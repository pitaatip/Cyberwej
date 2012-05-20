package pl.edu.agh.cyberwej.business.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.PaymentService;
import pl.edu.agh.cyberwej.data.dao.interfaces.GroupDAO;
import pl.edu.agh.cyberwej.data.dao.interfaces.PaymentDAO;
import pl.edu.agh.cyberwej.data.dao.interfaces.ProductDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.PaymentItem;
import pl.edu.agh.cyberwej.data.objects.PaymentParticipation;
import pl.edu.agh.cyberwej.data.objects.Product;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */
@Service(value = "paymentService")
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDAO paymentDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private GroupService groupService;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public float getPaymentCost(Payment payment) {
        float result = 0.0f;
        for (PaymentItem paymentItem : payment.getPaymentItems())
            result += paymentItem.getPrice() * paymentItem.getCount();
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Map<Payment, Float> getLastPayments(int count, User user) {
        List<Payment> consumedPayments = this.paymentDAO.getLastConsumedPayments(count, user);
        List<Payment> participatedPayments = this.paymentDAO.getLastParticipatedPayments(count,
                user);
        // consumedPayments and participatedPayments are sorted by payment.date
        // descending
        int index = 0; // index in consumedPayments list
        for (Payment participatedPayment : participatedPayments)
            if (!consumedPayments.contains(participatedPayment)) {
                int i = index;
                while (i < consumedPayments.size()
                        && participatedPayment.getDate().before(consumedPayments.get(i).getDate()))
                    i++;
                index = i;
                consumedPayments.add(i, participatedPayment);
            }
        if (consumedPayments.size() > count)
            consumedPayments = consumedPayments.subList(0, count);
        Map<Payment, Float> result = new HashMap<Payment, Float>();
        for (Payment payment : consumedPayments)
            result.put(payment, this.getUserStatusInPayment(payment, user));
        return result;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public float getUserStatusInPayment(Payment payment, User user) {
        float result = 0.0f;
        Integer userId = user.getId();

        for (PaymentItem paymentItem : payment.getPaymentItems())
            for (User consumer : paymentItem.getConsumers())
                if (consumer.getId().equals(userId))
                    result -= paymentItem.getCount() * paymentItem.getPrice()
                            / paymentItem.getConsumers().size();

        for (PaymentParticipation participation : payment.getParticipations())
            if (participation.getUser().getId().equals(userId))
                result += participation.getAmount();

        return result;
    }

    @Override
    public void setPaymentDAO(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @Transactional
    @Override
    public boolean registerPayment(Group group, Set<PaymentItem> items,
            Set<PaymentParticipation> participators, String description) {
        Payment payment = new Payment();
        payment.setDescription(description);
        payment.setDate(new Date());
        Group initializedGroup = groupService.getGroupWithMembers(group.getId());
        groupDAO.addGroupPayment(initializedGroup, payment);

        addParticipatorsWichPayedNothing(items, participators);
        for (PaymentParticipation paymentParticipation : participators) {
            paymentParticipation.setPayment(payment);
        }

        // save products first
        for (PaymentItem item : items) {
            Product findProductByName = productDAO.findProductByName(item.getProduct().getName());
            if (findProductByName != null) {
                item.setProduct(findProductByName);
            } else {
                productDAO.save(item.getProduct());
            }
        }
        payment.setParticipations(participators);
        payment.setPaymentItems(items);
        paymentDAO.save(payment);
        
        //actualize group members overdraw
        Map<User, Float> overdrawMap = getOverdrawMap(items, participators);
        for (PaymentParticipation paymentParticipation : participators) {
            for (GroupMembership member : initializedGroup.getGroupMembers()) {
                if(member.getUser().getId().equals(paymentParticipation.getUser().getId())){
                    //count difference between eaten and payed
                    member.setOverdraw(member.getOverdraw() - overdrawMap.get(paymentParticipation.getUser()));
                }
            }
        }
        groupDAO.saveGroup(initializedGroup);
        
        return true;
    }

    private Map<User, Float> getOverdrawMap(Set<PaymentItem> items,
            Set<PaymentParticipation> participators) {
        Map<User,Float> overdrawMap = new HashMap<User, Float>();
        for (PaymentParticipation paymentParticipation : participators) {
            overdrawMap.put(paymentParticipation.getUser(), -1 *paymentParticipation.getAmount());
            for (PaymentItem item : items) {
                final Set<User> consumers = item.getConsumers();
                
                boolean contains = UserIsConsumer(paymentParticipation, consumers);
                
                if(contains){
                    final float itemCost = (item.getCount()*item.getPrice())/consumers.size();
                    overdrawMap.put(paymentParticipation.getUser(), overdrawMap.get(paymentParticipation.getUser()) + itemCost);
                }
            }
        }
        return overdrawMap;
    }

    private boolean UserIsConsumer(PaymentParticipation paymentParticipation,
            final Set<User> consumers) {
        for (User user : consumers) {
            if(user.getId().equals(paymentParticipation.getUser().getId()))
                return true;
        }
        return false;
    }

    private void addParticipatorsWichPayedNothing(Set<PaymentItem> items,
            Set<PaymentParticipation> participators) {
        Set<User> consumersInPayment = new HashSet<User>();
        for (PaymentItem item : items) {
            for (User consumer : item.getConsumers()) {
                consumersInPayment.add(consumer);
            }
        }

        for (User user : consumersInPayment) {
            boolean found = false;
            for (PaymentParticipation participation : participators) {
                if (participation.getUser().getId().equals(user.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                PaymentParticipation newPart = new PaymentParticipation();
                newPart.setAmount(0);
                newPart.setUser(user);
                participators.add(newPart);
            }

        }
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
}
