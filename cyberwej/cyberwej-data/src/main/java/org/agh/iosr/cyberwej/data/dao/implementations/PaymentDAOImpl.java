package org.agh.iosr.cyberwej.data.dao.implementations;

import java.util.Date;
import java.util.List;

import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payment;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDAOImpl extends DAOBase implements PaymentDAO {

	@Override
	public boolean savePayment(Payment payment) {
		return super.save(payment);
	}

	@Override
	public void removePayment(Payment payment) {
		super.hibernateTemplate.delete(payment);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Payment> findPaymentsAfterDate(Date date, Group group) {
		return super.hibernateTemplate.find(
				"from Payment payment");
	}
}
