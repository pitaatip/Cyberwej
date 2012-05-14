package pl.edu.agh.cyberwej.business.services.api;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payback;
import pl.edu.agh.cyberwej.data.objects.User;

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
