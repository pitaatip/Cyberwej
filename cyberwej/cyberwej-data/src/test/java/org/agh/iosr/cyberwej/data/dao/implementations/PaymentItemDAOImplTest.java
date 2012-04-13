package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentItemDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
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

	private PaymentItem paymentItem;
	private int count = 3;
	private float price = 2.45f;

	private User user;

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
		this.paymentItemDAO.savePaymentItem(paymentItem);
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testSavePaymentItem() {
		PaymentItem retrievedPaymentItem = this.paymentItemDAO
				.loadPaymentItem(this.user.getId());
		assertNotNull(retrievedPaymentItem);
		assertEquals(retrievedPaymentItem.getCount(), count);
		assertEquals(retrievedPaymentItem.getPrice(), price, 0.0);
		assertFalse(retrievedPaymentItem.getConsumers().isEmpty());
		assertTrue(retrievedPaymentItem.getConsumers().contains(this.user));
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testRemovePaymentItem() {
		this.paymentItemDAO.removePaymentItem(paymentItem);
		PaymentItem retrievedPaymentItem = this.paymentItemDAO
				.loadPaymentItem(this.user.getId());
		assertNull(retrievedPaymentItem);
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testLoadPaymentItem() {
		PaymentItem retrievedPaymentItem = this.paymentItemDAO
				.loadPaymentItem(this.user.getId());
		assertNotNull(retrievedPaymentItem);
	}
}
