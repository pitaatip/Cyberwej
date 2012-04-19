package org.agh.iosr.cyberwej.data.dao.implementations;

import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payment;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDAOImpl extends DAOBase implements PaymentDAO {

	@Override
	public boolean addGroupPayment(Group group, Payment payment) {
		group.addPayment(payment);
		return super.save(group);
		//return super.save(payment);
	}

	@Override
	public void removePayment(Payment payment) {
		super.hibernateTemplate.delete(payment);
	}
}
