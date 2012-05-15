package pl.edu.agh.cyberwej.data.dao.implementations;

import org.springframework.stereotype.Repository;

import pl.edu.agh.cyberwej.data.dao.interfaces.PaybackDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payback;
import pl.edu.agh.cyberwej.data.objects.User;

@Repository
public class PaybackDAOImpl extends DAOBase<Payback> implements PaybackDAO {

    @Override
    public boolean addPayback(User debtor, User investor, Group group,
            float amount) {
        Payback payback = new Payback();
        payback.setAccepted(false);
        payback.setAmount(amount);
        payback.setSender(debtor);
        payback.setGroup(group);
        payback.setReceiver(investor);
        investor.getPaybacksForUser().add(payback);
        debtor.getPaybacksForOthers().add(payback);
        group.getPaybacks().add(payback);
        return super.save(payback);
    }

    @Override
    public void removePayback(Payback payback) {
        payback.getSender().getPaybacksForOthers().remove(payback);
        payback.getReceiver().getPaybacksForUser().remove(payback);
        payback.getGroup().getPaybacks().remove(payback);
        super.hibernateTemplate.delete(payback);
    }

    @Override
    public boolean updatePayback(Payback payback) {
        return super.save(payback);
    }

}