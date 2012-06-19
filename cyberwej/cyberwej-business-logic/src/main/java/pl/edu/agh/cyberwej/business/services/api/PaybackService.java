package pl.edu.agh.cyberwej.business.services.api;

import java.util.List;

import pl.edu.agh.cyberwej.data.dao.interfaces.PaybackDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payback;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author Krzysztof
 */
public interface PaybackService {

    boolean createPayback(User debtor, User investor, Group group, float amount);

    boolean acceptPayback(Payback payback, boolean isAccepted);

    boolean acceptPaybackById(int paybackId, boolean isAccepted);

    List<Payback> getPaybacksForUser(User user, boolean onlyUnaccepted);

    void setPaybackDAO(PaybackDAO paybackDAO);
}
