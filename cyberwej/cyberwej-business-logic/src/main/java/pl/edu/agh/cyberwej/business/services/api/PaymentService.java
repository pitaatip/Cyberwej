package pl.edu.agh.cyberwej.business.services.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.edu.agh.cyberwej.common.objects.service.ParticipantInformation;
import pl.edu.agh.cyberwej.common.objects.service.PaymentInformation;
import pl.edu.agh.cyberwej.data.dao.interfaces.PaymentDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.PaymentItem;
import pl.edu.agh.cyberwej.data.objects.PaymentParticipation;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author Krzysztof
 */
public interface PaymentService {

    float getPaymentCost(Payment payment);

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
    Map<Payment, Float> getLastPayments(int count, User user);

    float getUserStatusInPayment(Payment payment, User user);

    boolean registerPayment(Group group, Set<PaymentItem> items,
            Set<PaymentParticipation> participators, String description);

    void setPaymentDAO(PaymentDAO paymentDAO);

    List<PaymentInformation> getGroupPayments(Group group);

    Payment getPaymentWithDependencies(int id);

    int getInvolvedUsersCount(Payment payment);

    Set<User> getInvolvedUsers(Payment payment);

    List<ParticipantInformation> getParticipants(Payment payment);

    void addPaymentItems(Payment payment, List<PaymentItem> paymentItems);

    void addPayers(Payment payment, List<PaymentParticipation> paymentParticipations);
}
