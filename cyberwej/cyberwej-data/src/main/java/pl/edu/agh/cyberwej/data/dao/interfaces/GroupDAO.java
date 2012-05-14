package pl.edu.agh.cyberwej.data.dao.interfaces;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payment;

public interface GroupDAO extends IDAO<Group> {

    public boolean saveGroup(Group group);

    public Group getGroupByName(String name);

    public void removeGroup(Group group);

    public boolean addGroupPayment(Group group, Payment payment);

    public void removeGroupPayment(Group group, Payment payment);
}
