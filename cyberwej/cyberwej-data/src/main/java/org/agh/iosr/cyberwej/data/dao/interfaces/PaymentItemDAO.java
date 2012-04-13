package org.agh.iosr.cyberwej.data.dao.interfaces;

import org.agh.iosr.cyberwej.data.objects.PaymentItem;

public interface PaymentItemDAO {

	public boolean savePaymentItem(PaymentItem paymentItem);

	public void removePaymentItem(PaymentItem paymentItem);
	
	public PaymentItem loadPaymentItem(int id);
}
