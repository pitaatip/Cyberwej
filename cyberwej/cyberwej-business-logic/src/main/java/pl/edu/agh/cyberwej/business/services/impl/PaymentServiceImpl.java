package pl.edu.agh.cyberwej.business.services.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.business.services.api.PaymentService;
import pl.edu.agh.cyberwej.common.objects.service.ParticipantInformation;
import pl.edu.agh.cyberwej.data.dao.interfaces.PaymentDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.PaymentItem;
import pl.edu.agh.cyberwej.data.objects.PaymentParticipation;
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

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public float getPaymentCost(Payment payment) {
        float result = 0.0f;
        for (PaymentItem paymentItem : payment.getPaymentItems())
            result += paymentItem.getPrice() * paymentItem.getCount();
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Map<Payment, Float> getLastPayments(int count, User user) {
        List<Payment> consumedPayments = this.paymentDAO
                .getLastConsumedPayments(count, user);
        List<Payment> participatedPayments = this.paymentDAO
                .getLastParticipatedPayments(count, user);
        // consumedPayments and participatedPayments are sorted by payment.date
        // descending
        int index = 0; // index in consumedPayments list
        for (Payment participatedPayment : participatedPayments)
            if (!consumedPayments.contains(participatedPayment)) {
                int i = index;
                while (i < consumedPayments.size()
                        && participatedPayment.getDate().before(
                                consumedPayments.get(i).getDate()))
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

    @Override
    @Transactional(readOnly = true)
    public Map<Payment, Float> getGroupPayments(Group group) {
        Map<Payment, Float> result = new HashMap<Payment, Float>();
        for (Payment payment : group.getPayments())
            result.put(payment,
                    getPaymentCost(this.paymentDAO.getById(payment.getId())));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Payment getPaymentWithDependencies(int id) {
        Payment payment = this.paymentDAO.getById(id);
        if (payment != null) {
            payment.setPaymentItems(new HashSet<PaymentItem>(payment
                    .getPaymentItems()));
            payment.setParticipations(new HashSet<PaymentParticipation>(payment
                    .getParticipations()));
            for (PaymentItem paymentItem : payment.getPaymentItems())
                paymentItem.setConsumers(new HashSet<User>(paymentItem
                        .getConsumers()));
        }
        return payment;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public int getInvolvedUsersCount(Payment payment) {
        return getInvolvedUsers(payment).size();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<User> getInvolvedUsers(Payment payment) {
        Set<User> involvedUsers = new HashSet<User>();
        for (PaymentItem paymentItem : payment.getPaymentItems())
            for (User user : paymentItem.getConsumers())
                involvedUsers.add(user);
        for (PaymentParticipation paymentParticipation : payment
                .getParticipations())
            involvedUsers.add(paymentParticipation.getUser());
        return involvedUsers;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ParticipantInformation> getParticipants(Payment payment) {
        Map<User, ParticipantInformation> participationsMap = new HashMap<User, ParticipantInformation>();
        for (PaymentParticipation paymentParticipation : payment
                .getParticipations()) {
            ParticipantInformation participantInformation = new ParticipantInformation();
            participantInformation.setUser(paymentParticipation.getUser());
            participantInformation.setAmount(paymentParticipation.getAmount());
            participantInformation.setStatus(paymentParticipation.getAmount());
            participationsMap.put(paymentParticipation.getUser(),
                    participantInformation);
        }
        ParticipantInformation participantInformation;
        for (PaymentItem paymentItem : payment.getPaymentItems())
            for (User consumer : paymentItem.getConsumers()) {
                if (participationsMap.containsKey(consumer))
                    participantInformation = participationsMap.get(consumer);
                else {
                    participantInformation = new ParticipantInformation();
                    participationsMap.put(consumer, participantInformation);
                    participantInformation.setUser(consumer);
                }
                participantInformation.incrementConsumedItemsCount();
                participantInformation.setStatus(participantInformation
                        .getStatus()
                        - paymentItem.getCount()
                        * paymentItem.getPrice()
                        / paymentItem.getConsumers().size());
            }
        return new LinkedList<ParticipantInformation>(
                participationsMap.values());
    }
}
