package org.agh.iosr.cyberwej.data.dao.implementations;

import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentParticipationDAO;
import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.PaymentParticipation;
import org.agh.iosr.cyberwej.data.objects.User;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentParticipationDAOImpl extends DAOBase implements
		PaymentParticipationDAO {

	@Override
	public boolean addPaymentParticipation(Payment payment, User user,
			float amount) {
		PaymentParticipation paymentParticipation = new PaymentParticipation();
		paymentParticipation.setAmount(amount);
		paymentParticipation.setUser(user);
		paymentParticipation.setPayment(payment);
		payment.getParticipations().add(paymentParticipation);
		return super.save(paymentParticipation);
	}

	@Override
	public void removePaymentParticipation(
			PaymentParticipation paymentParticipation) {
		super.hibernateTemplate.delete(paymentParticipation);
	}

}
