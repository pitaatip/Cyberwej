package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.PaymentItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentDAOImplTest {

	@Autowired
	private PaymentDAO paymentDAO;

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

	@BeforeTransaction
	public void setUp() {
		this.group = new Group();
		this.group.setName(groupName);

		paymentItem = new PaymentItem();
		paymentItem.setPrice(price);
		paymentItem.setCount(count);

		Set<PaymentItem> paymentItems = new HashSet<PaymentItem>();
		paymentItems.add(paymentItem);

		this.date = new Date();

		this.payment = new Payment();
		this.payment.setDate(date);
		this.payment.setDescription(description);
		this.payment.setPaymentItems(paymentItems);

		this.paymentDAO.addGroupPayment(group, payment);
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testSavePayment() {
		Set<Payment> groupPayments = this.groupDAO.getGroupByName(
				this.groupName).getPayments();
		assertFalse(groupPayments.isEmpty());
		assertTrue(groupPayments.iterator().next().getId() == this.payment
				.getId());
		assertTrue(groupPayments.iterator().next().getPaymentItems().iterator()
				.next().getId() == this.paymentItem.getId());
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testRemovePayment() {
		this.paymentDAO.removePayment(payment);
		Set<Payment> groupPayments = this.groupDAO.getGroupByName(
				this.groupName).getPayments();
		assertTrue(groupPayments.isEmpty());
	}

	@AfterTransaction
	public void clean() {
		this.groupDAO.removeGroup(group);
	}
}
