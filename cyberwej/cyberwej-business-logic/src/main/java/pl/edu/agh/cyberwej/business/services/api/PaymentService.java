package pl.edu.agh.cyberwej.business.services.api;

import java.util.Map;
import java.util.Set;

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
    
    public boolean registerPayment(Group group, Set<PaymentItem> items, Set<PaymentParticipation> participators, String description);

    public void setPaymentDAO(PaymentDAO paymentDAO);

}
