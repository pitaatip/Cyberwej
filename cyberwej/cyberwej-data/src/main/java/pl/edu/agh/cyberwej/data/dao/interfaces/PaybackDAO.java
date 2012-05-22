package pl.edu.agh.cyberwej.data.dao.interfaces;

import java.util.List;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payback;
import pl.edu.agh.cyberwej.data.objects.User;

public interface PaybackDAO extends IDAO<Payback> {

    public boolean addPayback(User debtor, User investor, Group group,
            float amount);

    public void removePayback(Payback payback);

    public boolean updatePayback(Payback payback);
    
    public List<Payback> getPaybacksForUser(User user, boolean onlyUnaccepted);

}
