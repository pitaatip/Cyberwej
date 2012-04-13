package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentItemDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.PaymentItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentDAOImplTest {

	@Autowired
	private PaymentDAO paymentDAO;

	@Autowired
	private PaymentItemDAO paymentItemDAO;

	@Autowired
	private GroupDAO groupDAO;

	private Payment payment;
	private Date date;
	private String description = "Wyjscie na pizze";

	private PaymentItem paymentItem;
	private int count = 3;
	private float price = 2.45f;

	private Group group;
	private String groupName = "Grupa testowa";

	@Before
	public void setUp() {
		paymentItem = new PaymentItem();
		paymentItem.setPrice(price);
		paymentItem.setCount(count);
		this.paymentItemDAO.savePaymentItem(paymentItem);

		Set<PaymentItem> paymentItems = new HashSet<PaymentItem>();
		paymentItems.add(paymentItem);

		this.date = new Date();

		this.payment = new Payment();
		this.payment.setDate(date);
		this.payment.setDescription(description);
		this.payment.setPaymentItems(paymentItems);

		this.paymentDAO.savePayment(payment);

		Set<Payment> payments = new HashSet<Payment>();
		payments.add(payment);
		this.group = new Group();
		this.group.setPayments(payments);
		this.group.setName(groupName);
		groupDAO.saveGroup(this.group);
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testSavePayment() {
		List<Payment> payments = this.paymentDAO.findPaymentsAfterDate(date,
				this.group);
		assertFalse(payments.isEmpty());
		assertTrue(payments.contains(payment));
		assertTrue(payments.get(0).getPaymentItems().equals(paymentItem));
		date = new Date();
		payments = this.paymentDAO.findPaymentsAfterDate(date, group);
		assertTrue(payments.isEmpty());
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testRemovePayment() {
		fail("Not yet implemented");
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testFindPaymentsAfterDate() {
		fail("Not yet implemented");
	}

}
