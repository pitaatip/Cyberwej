package pl.edu.agh.cyberwej.data.dao.implementations;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import pl.edu.agh.cyberwej.data.dao.interfaces.GroupDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payment;

@Repository
public class GroupDAOImpl extends DAOBase<Group> implements GroupDAO {

    @Override
    public boolean saveGroup(Group group) {
        group.setCreationDate(new Date());
        return super.save(group);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Group getGroupByName(String name) {
        List<Group> groups = this.hibernateTemplate.find(
                "from Group group where group.name=?", name);
        if (groups.size() > 0)
            return groups.get(0);
        return null;
    }

    @Override
    public void removeGroup(Group group) {
        super.hibernateTemplate.delete(group);
    }

    @Override
    public boolean addGroupPayment(Group group, Payment payment) {
        group.getPayments().add(payment);
        payment.setGroup(group);
        return super.save(group);
    }

    @Override
    public void removeGroupPayment(Group group, Payment payment) {
        group.getPayments().remove(payment);
        super.save(group);
    }

}
