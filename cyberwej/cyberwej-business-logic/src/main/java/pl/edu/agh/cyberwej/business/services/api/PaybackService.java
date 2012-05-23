package pl.edu.agh.cyberwej.business.services.api;

import java.util.List;

import pl.edu.agh.cyberwej.data.dao.interfaces.PaybackDAO;
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
    
    public boolean acceptPaybackById(int paybackId, boolean isAccepted);
    
    public List<Payback> getPaybacksForUser(User user, boolean onlyUnaccepted);
    
    public void setPaybackDAO(PaybackDAO paybackDAO);
}
