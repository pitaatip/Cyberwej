package org.agh.iosr.cyberwej.data.dao.implementations;

import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentItemDAO;
import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.PaymentItem;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentItemDAOImpl extends DAOBase implements PaymentItemDAO {

	@Override
	public boolean savePaymentItem(Payment payment, PaymentItem paymentItem) {
		payment.getPaymentItems().add(paymentItem);
		return super.save(payment);
	}

	@Override
	public void removePaymentItem(Payment payment, PaymentItem paymentItem) {
		payment.getPaymentItems().remove(paymentItem);
		super.save(paymentItem);
	}
}
