package pl.edu.agh.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.data.dao.interfaces.GroupDAO;
import pl.edu.agh.cyberwej.data.dao.interfaces.PaymentDAO;
import pl.edu.agh.cyberwej.data.dao.interfaces.UserDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.PaymentItem;
import pl.edu.agh.cyberwej.data.objects.PaymentParticipation;
import pl.edu.agh.cyberwej.data.objects.User;

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

    private Payment payment2;
    private Date date2;
    private final String description2 = "Zakup kaloryfera";

    private PaymentItem paymentItem;
    private final int count = 3;
    private final float price = 2.45f;

    private PaymentItem paymentItem2;
    private final int count2 = 4;
    private final float price2 = 4.04f;

    private User user;
    private final String userMail = "w@wojtkowie.pl";

    private Group group;
    private final String groupName = "Grupa testowa";

    private final float amount = 12.5f;

    private final float amount2 = 15.60f;

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
                assertTrue(paymentItem.getConsumers().iterator().next().getMail()
                        .equals(this.userMail));
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
        this.paymentDAO.addPaymentParticipation(this.payment, this.user, this.amount);
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertNotNull(retrievedGroup);
        assertFalse(retrievedGroup.getPayments().isEmpty());
        for (Payment payment : retrievedGroup.getPayments()) {
            assertFalse(payment.getParticipations().isEmpty());
            for (PaymentParticipation participation : payment.getParticipations()) {
                assertEquals(participation.getAmount(), this.amount, 0.0);
                assertEquals(participation.getUser().getName(), this.user.getName());
            }
        }
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRemovePaymentParticipation() {
        this.paymentDAO.addPaymentParticipation(this.payment, this.user, this.amount);
        for (Payment payment : this.group.getPayments()) {
            for (Iterator<PaymentParticipation> iterator = payment.getParticipations().iterator(); iterator
                    .hasNext();) {
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

    @Transactional
    @Rollback(true)
    @Test
    public void testGetLastParticipatedPayments() {
        this.payment2 = new Payment();
        this.date2 = new Date();
        this.payment2.setDate(this.date2);
        this.payment2.setDescription(this.description2);
        this.groupDAO.addGroupPayment(this.group, this.payment2);

        this.paymentDAO.addPaymentParticipation(this.payment, this.user, this.amount);
        this.paymentDAO.addPaymentParticipation(this.payment2, this.user, this.amount2);
        List<Payment> payments = this.paymentDAO.getLastParticipatedPayments(1, this.user);
        assertNotNull(payments);
        assertEquals(payments.size(), 1);
        assertEquals(payments.get(0).getDate(), this.date2);
        payments = this.paymentDAO.getLastParticipatedPayments(2, this.user);
        assertNotNull(payments);
        assertEquals(payments.size(), 2);
        assertEquals(payments.get(0).getDate(), this.date2);
        assertEquals(payments.get(1).getDate(), this.date);
        assertNull(this.paymentDAO.getLastParticipatedPayments(500, null));
        assertTrue(this.paymentDAO.getLastParticipatedPayments(0, this.user).isEmpty());
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testGetLastConsumedPayments() {
        assertNull(this.paymentDAO.getLastParticipatedPayments(500, null));
        assertTrue(this.paymentDAO.getLastParticipatedPayments(0, this.user).isEmpty());

        this.payment2 = new Payment();
        this.date2 = new Date();
        this.payment2.setDate(this.date2);
        this.payment2.setDescription(this.description2);
        this.groupDAO.addGroupPayment(this.group, this.payment2);

        this.paymentItem2 = new PaymentItem();
        this.paymentItem2.setCount(this.count2);
        this.paymentItem2.setPrice(this.price2);
        this.paymentItem2.getConsumers().add(this.user);
        this.paymentDAO.savePaymentItem(this.payment2, this.paymentItem2);

        List<Payment> payments = this.paymentDAO.getLastConsumedPayments(1, this.user);
        assertNotNull(payments);
        assertEquals(payments.size(), 1);
        assertEquals(payments.get(0).getDate(), this.date2);

        payments = this.paymentDAO.getLastConsumedPayments(2, this.user);
        assertNotNull(payments);
        assertEquals(payments.size(), 2);
        assertEquals(payments.get(0).getDate(), this.date2);
        assertEquals(payments.get(1).getDate(), this.date);

    }

    @AfterTransaction
    public void clean() {
        Group group = this.groupDAO.getGroupByName(this.groupName);
        this.groupDAO.removeGroup(group);
        User user = this.userDAO.findUserByMail(this.userMail);
        this.userDAO.removeUser(user);
    }

}
