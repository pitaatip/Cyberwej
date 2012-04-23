package org.agh.iosr.cyberwej.data.dao.implementations;

import java.util.HashSet;

import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payment;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDAOImpl extends DAOBase implements PaymentDAO {

	@Override
	public boolean addGroupPayment(Group group, Payment payment) {
		if (group.getPayments() == null)
			group.setPayments(new HashSet<Payment>());
		group.getPayments().add(payment);
		return super.save(group);
	}

	@Override
	public void removePayment(Group group, Payment payment) {
		group.getPayments().remove(payment);
		super.save(group);
	}
}
