package pl.edu.agh.cyberwej.business.services.api;

import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payback;
import org.agh.iosr.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */
public interface PaybackService {

    public boolean createPayback(User debtor, User investor, Group group,
            float amount);

    public boolean acceptPayback(Payback payback, boolean isAccepted);
}
