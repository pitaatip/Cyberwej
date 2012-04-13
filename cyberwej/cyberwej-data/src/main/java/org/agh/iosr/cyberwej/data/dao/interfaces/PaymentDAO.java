package org.agh.iosr.cyberwej.data.dao.interfaces;

import java.util.Date;
import java.util.List;

import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payment;

public interface PaymentDAO {

	public boolean savePayment(Payment payment);

	public void removePayment(Payment payment);

	public List<Payment> findPaymentsAfterDate(Date date, Group group);
}
