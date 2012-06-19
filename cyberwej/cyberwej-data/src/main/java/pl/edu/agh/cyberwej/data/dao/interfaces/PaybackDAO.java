package pl.edu.agh.cyberwej.data.dao.interfaces;

import java.util.List;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payback;
import pl.edu.agh.cyberwej.data.objects.User;

public interface PaybackDAO extends IDAO<Payback> {

    boolean addPayback(User debtor, User investor, Group group, float amount);

    void removePayback(Payback payback);

    boolean updatePayback(Payback payback);

    List<Payback> getPaybacksForUser(User user, boolean onlyUnaccepted);

}
