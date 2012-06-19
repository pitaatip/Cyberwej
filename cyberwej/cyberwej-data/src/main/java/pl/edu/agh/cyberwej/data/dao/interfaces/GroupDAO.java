package pl.edu.agh.cyberwej.data.dao.interfaces;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payment;

public interface GroupDAO extends IDAO<Group> {

    boolean saveGroup(Group group);

    Group getGroupByName(String name);

    void removeGroup(Group group);

    boolean addGroupPayment(Group group, Payment payment);

    void removeGroupPayment(Group group, Payment payment);

}
