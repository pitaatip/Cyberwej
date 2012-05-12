package pl.edu.agh.cyberwej.business.services.api;

import java.util.Map;

import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.User;

public interface PaymentService {

    public float getPaymentCost(Payment payment);

    /**
     * Returns last payments of the user(payments which user has participated
     * in) with value of spent money decreased by consumed goods cost
     * 
     * @param count
     *            max number of returned payments
     * @param user
     * 
     * @return map representing payment and user status in this payment
     */
    public Map<Payment, Float> getLastPayments(int count, User user);

    public float getUserStatusInPayment(Payment payment, User user);
}
