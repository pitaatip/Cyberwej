package org.agh.iosr.cyberwej.data.dao.implementations;

import java.util.HashSet;

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
		if (payment.getParticipations() == null)
			payment.setParticipations(new HashSet<PaymentParticipation>());
		payment.getParticipations().add(paymentParticipation);
		return super.save(payment);
	}

	@Override
	public void removePaymentParticipation(
			PaymentParticipation paymentParticipation) {
		paymentParticipation.getPayment().getParticipations()
				.remove(paymentParticipation);
		super.save(paymentParticipation.getPayment());
	}

}
