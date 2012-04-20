package org.agh.iosr.cyberwej.data.dao.interfaces;

import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.PaymentParticipation;
import org.agh.iosr.cyberwej.data.objects.User;

public interface PaymentParticipationDAO {

	public boolean addPaymentParticipation(Payment payment, User user,
			float amount);

	public void removePaymentParticipation(
			PaymentParticipation paymentParticipation);
}
