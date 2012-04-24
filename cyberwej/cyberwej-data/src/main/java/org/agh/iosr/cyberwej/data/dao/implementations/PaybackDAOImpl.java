package org.agh.iosr.cyberwej.data.dao.implementations;

import org.agh.iosr.cyberwej.data.dao.interfaces.PaybackDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payback;
import org.agh.iosr.cyberwej.data.objects.User;
import org.springframework.stereotype.Repository;

@Repository
public class PaybackDAOImpl extends DAOBase implements PaybackDAO {

	@Override
	public boolean addPayback(User debtor, User investor, Group group,
			float amount) {
		Payback payback = new Payback();
		payback.setAccepted(false);
		payback.setAmount(amount);
		payback.setDebtor(debtor);
		payback.setGroup(group);
		payback.setInvestor(investor);
		investor.getPaybacksForUser().add(payback);
		debtor.getPaybacksForOthers().add(payback);
		group.getPaybacks().add(payback);
		return super.save(payback);
	}

	@Override
	public void removePayback(Payback payback) {
		payback.getDebtor().getPaybacksForOthers().remove(payback);
		payback.getInvestor().getPaybacksForUser().remove(payback);
		payback.getGroup().getPaybacks().remove(payback);
		super.hibernateTemplate.delete(payback);
	}

	@Override
	public boolean updatePayback(Payback payback) {
		return super.save(payback);
	}

}
