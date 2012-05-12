package org.agh.iosr.cyberwej.data.dao.interfaces;

import java.util.List;

import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.PaymentItem;
import org.agh.iosr.cyberwej.data.objects.PaymentParticipation;
import org.agh.iosr.cyberwej.data.objects.User;

public interface PaymentDAO extends IDAO<Payment> {

    public boolean savePaymentItem(Payment payment, PaymentItem paymentItem);

    public void removePaymentItem(Payment payment, PaymentItem paymentItem);

    public boolean addPaymentParticipation(Payment payment, User user,
            float amount);

    public void removePaymentParticipation(
            PaymentParticipation paymentParticipation);

    public List<Payment> getLastParticipatedPayments(int count, User user);

    public List<Payment> getLastConsumedPayments(int count, User user);
}
