package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Iterator;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.PaymentItem;
import org.agh.iosr.cyberwej.data.objects.PaymentParticipation;
import org.agh.iosr.cyberwej.data.objects.User;
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

    @Autowired
    private UserDAO userDAO;

    private Payment payment;
    private Date date;
    private final String description = "Wyjscie na pizze";

    private PaymentItem paymentItem;
    private final int count = 3;
    private final float price = 2.45f;

    private User user;
    private final String userMail = "w@wojtkowie.pl";

    private Group group;
    private final String groupName = "Grupa testowa";

    private final float amount = 12.5f;

    @BeforeTransaction
    public void setUp() {
        this.group = new Group();
        this.group.setName(this.groupName);

        this.date = new Date();

        this.payment = new Payment();
        this.payment.setDate(this.date);
        this.payment.setDescription(this.description);

        this.groupDAO.saveGroup(this.group);
        this.groupDAO.addGroupPayment(this.group, this.payment);

        this.user = new User();
        this.user.setName("Wojtek");
        this.user.setSurname("Wojtkowski");
        this.user.setLogin("W");
        this.user.setMail(this.userMail);
        this.userDAO.saveUser(this.user);

        this.paymentItem = new PaymentItem();
        this.paymentItem.setPrice(this.price);
        this.paymentItem.setCount(this.count);

        this.paymentItem.getConsumers().add(this.user);

        this.paymentDAO.savePaymentItem(this.payment, this.paymentItem);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testSavePaymentItem() {
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertFalse(retrievedGroup.getPayments().isEmpty());
        for (Payment payment : retrievedGroup.getPayments()) {
            assertFalse(payment.getPaymentItems().isEmpty());
            for (PaymentItem paymentItem : payment.getPaymentItems()) {
                assertNotNull(paymentItem);
                assertEquals(paymentItem.getCount(), this.count);
                assertEquals(paymentItem.getPrice(), this.price, 0.0);
                assertFalse(paymentItem.getConsumers().isEmpty());
                assertTrue(paymentItem.getConsumers().iterator().next()
                        .getMail().equals(this.userMail));
            }
        }
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRemovePaymentItem() {
        this.paymentDAO.removePaymentItem(this.payment, this.paymentItem);
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertFalse(retrievedGroup.getPayments().isEmpty());
        for (Payment payment : retrievedGroup.getPayments()) {
            assertTrue(payment.getPaymentItems().isEmpty());
        }
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testAddPaymentParticipation() {
        this.paymentDAO.addPaymentParticipation(this.payment, this.user,
                this.amount);
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertNotNull(retrievedGroup);
        assertFalse(retrievedGroup.getPayments().isEmpty());
        for (Payment payment : retrievedGroup.getPayments()) {
            assertFalse(payment.getParticipations().isEmpty());
            for (PaymentParticipation participation : payment
                    .getParticipations()) {
                assertEquals(participation.getAmount(), this.amount, 0.0);
                assertEquals(participation.getUser().getName(),
                        this.user.getName());
            }
        }
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRemovePaymentParticipation() {
        this.paymentDAO.addPaymentParticipation(this.payment, this.user,
                this.amount);
        for (Payment payment : this.group.getPayments()) {
            for (Iterator<PaymentParticipation> iterator = payment
                    .getParticipations().iterator(); iterator.hasNext();) {
                this.paymentDAO.removePaymentParticipation(iterator.next());
            }
        }
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertNotNull(retrievedGroup);
        assertFalse(retrievedGroup.getPayments().isEmpty());
        for (Payment payment : retrievedGroup.getPayments()) {
            assertTrue(payment.getParticipations().isEmpty());
        }
    }

    @AfterTransaction
    public void clean() {
        Group group = this.groupDAO.getGroupByName(this.groupName);
        this.groupDAO.removeGroup(group);
        User user = this.userDAO.findUserByMail(this.userMail);
        this.userDAO.removeUser(user);
    }

}
