package pl.edu.agh.cyberwej.data.dao.interfaces;

import java.util.List;

import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.PaymentItem;
import pl.edu.agh.cyberwej.data.objects.PaymentParticipation;
import pl.edu.agh.cyberwej.data.objects.User;

public interface PaymentDAO extends IDAO<Payment> {

    boolean savePaymentItem(Payment payment, PaymentItem paymentItem);

    void removePaymentItem(Payment payment, PaymentItem paymentItem);

    boolean addPaymentParticipation(Payment payment, User user, float amount);

    void removePaymentParticipation(PaymentParticipation paymentParticipation);

    List<Payment> getLastParticipatedPayments(int count, User user);

    List<Payment> getLastConsumedPayments(int count, User user);
}
