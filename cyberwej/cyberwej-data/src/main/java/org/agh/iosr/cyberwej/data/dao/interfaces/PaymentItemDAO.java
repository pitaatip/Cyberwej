package org.agh.iosr.cyberwej.data.dao.interfaces;

import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.PaymentItem;

public interface PaymentItemDAO {

    public boolean savePaymentItem(Payment payment, PaymentItem paymentItem);

    public void removePaymentItem(Payment payment, PaymentItem paymentItem);
}
