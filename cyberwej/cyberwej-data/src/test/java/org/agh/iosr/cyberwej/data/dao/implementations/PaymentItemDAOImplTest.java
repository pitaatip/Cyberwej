package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentItemDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.PaymentItem;
import org.agh.iosr.cyberwej.data.objects.User;
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
public class PaymentItemDAOImplTest {

	@Autowired
	private PaymentItemDAO paymentItemDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PaymentDAO paymentDAO;

	@Autowired
	private GroupDAO groupDAO;

	private PaymentItem paymentItem;
	private int count = 3;
	private float price = 2.45f;

	private User user;

	private Payment payment;
	private Date date;
	private String description = "Wyjscie na pizze";

	private Group group;
	private String groupName = "Grupa test";

	@Before
	public void setUp() {
		paymentItem = new PaymentItem();
		paymentItem.setPrice(price);
		paymentItem.setCount(count);
		user = new User();
		user.setName("Wojtek");
		user.setSurname("Wojtkowski");
		user.setLogin("Woj");
		user.setMail("wojtek@wojtkowie.pl");
		this.userDAO.saveUser(user);
		Set<User> users = new HashSet<User>();
		users.add(user);
		paymentItem.setConsumers(users);

		this.payment = new Payment();
		this.payment.setDate(date);
		this.payment.setDescription(description);

		this.group = new Group();
		this.group.setName(groupName);

		this.groupDAO.saveGroup(group);
		this.paymentDAO.addGroupPayment(group, payment);
		this.paymentItemDAO.savePaymentItem(payment, paymentItem);
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testSavePaymentItem() {
		Group retrievedGroup = this.groupDAO.getGroupByName(groupName);
		assertFalse(retrievedGroup.getPayments().isEmpty());
		for (Payment payment : retrievedGroup.getPayments()) {
			assertFalse(payment.getPaymentItems().isEmpty());
			for (PaymentItem paymentItem : payment.getPaymentItems()) {
				assertNotNull(paymentItem);
				assertEquals(paymentItem.getCount(), count);
				assertEquals(paymentItem.getPrice(), price, 0.0);
				assertFalse(paymentItem.getConsumers().isEmpty());
				assertTrue(paymentItem.getConsumers().contains(this.user));
			}
		}
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testRemovePaymentItem() {
		this.paymentItemDAO.removePaymentItem(payment, paymentItem);
		Group retrievedGroup = this.groupDAO.getGroupByName(groupName);
		assertFalse(retrievedGroup.getPayments().isEmpty());
		for (Payment payment : retrievedGroup.getPayments()) {
			assertTrue(payment.getPaymentItems().isEmpty());
		}
	}
}
