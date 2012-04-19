package org.agh.iosr.cyberwej.data.dao.interfaces;

import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payment;

public interface PaymentDAO {

	public boolean addGroupPayment(Group group, Payment payment);

	public void removePayment(Payment payment);

}
