package org.agh.iosr.cyberwej.data.dao.implementations;

import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentItemDAO;
import org.agh.iosr.cyberwej.data.objects.PaymentItem;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentItemDAOImpl extends DAOBase implements PaymentItemDAO {

	@Override
	public boolean savePaymentItem(PaymentItem paymentItem) {
		return super.save(paymentItem);
	}

	@Override
	public void removePaymentItem(PaymentItem paymentItem) {
		super.hibernateTemplate.delete(paymentItem);
	}

	@Override
	public PaymentItem loadPaymentItem(int id) {
		return super.hibernateTemplate.get(PaymentItem.class, id);
	}

}
