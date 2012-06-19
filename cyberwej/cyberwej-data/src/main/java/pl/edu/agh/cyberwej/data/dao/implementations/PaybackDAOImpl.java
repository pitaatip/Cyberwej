package pl.edu.agh.cyberwej.data.dao.implementations;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import pl.edu.agh.cyberwej.data.dao.interfaces.PaybackDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payback;
import pl.edu.agh.cyberwej.data.objects.User;

@Repository
public class PaybackDAOImpl extends DAOBase<Payback> implements PaybackDAO {

    @Override
    public boolean addPayback(User debtor, User investor, Group group, float amount) {
        Payback payback = new Payback();
        payback.setAccepted(false);
        payback.setAmount(amount);
        payback.setSender(debtor);
        payback.setGroup(group);
        payback.setReceiver(investor);
        payback.setSentTime(new Date());
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

    @Override
    @SuppressWarnings("unchecked")
    public List<Payback> getPaybacksForUser(User user, boolean onlyUnaccepted) {
        String query = "from Payback payback where payback.receiver=?";
        if (onlyUnaccepted) {
            query = query + " and payback.accepted = false";
        }
        List<Payback> paybacks = (List<Payback>) this.hibernateTemplate.find(query, user);
        return paybacks;
    }

}
