package pl.edu.agh.cyberwej.business.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentDAO;
import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.PaymentItem;
import org.agh.iosr.cyberwej.data.objects.PaymentParticipation;
import org.agh.iosr.cyberwej.data.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.agh.cyberwej.business.services.api.PaymentService;

/**
 * 
 * @author Krzysztof
 * 
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDAO paymentDAO;

    @Override
    public float getPaymentCost(Payment payment) {
        float result = 0.0f;
        for (PaymentItem paymentItem : payment.getPaymentItems())
            result += paymentItem.getPrice() * paymentItem.getCount();
        return result;
    }

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

    public void setPaymentDAO(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }
}
