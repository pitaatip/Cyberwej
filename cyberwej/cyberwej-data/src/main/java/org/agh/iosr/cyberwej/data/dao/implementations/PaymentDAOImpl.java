package org.agh.iosr.cyberwej.data.dao.implementations;

import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentDAO;
import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.PaymentItem;
import org.agh.iosr.cyberwej.data.objects.PaymentParticipation;
import org.agh.iosr.cyberwej.data.objects.User;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDAOImpl extends DAOBase<Payment> implements PaymentDAO {

    @Override
    public boolean savePaymentItem(Payment payment, PaymentItem paymentItem) {
        payment.getPaymentItems().add(paymentItem);
        return super.save(payment);
    }

    @Override
    public void removePaymentItem(Payment payment, PaymentItem paymentItem) {
        payment.getPaymentItems().remove(paymentItem);
        super.save(payment);
    }

    @Override
    public boolean addPaymentParticipation(Payment payment, User user,
            float amount) {
        PaymentParticipation paymentParticipation = new PaymentParticipation();
        paymentParticipation.setAmount(amount);
        paymentParticipation.setUser(user);
        paymentParticipation.setPayment(payment);
        payment.getParticipations().add(paymentParticipation);
        return super.save(payment);
    }

    @Override
    public void removePaymentParticipation(
            PaymentParticipation paymentParticipation) {
        paymentParticipation.getPayment().getParticipations()
                .remove(paymentParticipation);
        super.save(paymentParticipation.getPayment());
    }

}
