package pl.edu.agh.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.data.dao.interfaces.GroupDAO;
import pl.edu.agh.cyberwej.data.dao.interfaces.PaymentDAO;
import pl.edu.agh.cyberwej.data.dao.interfaces.PaymentItemDAO;
import pl.edu.agh.cyberwej.data.dao.interfaces.UserDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.PaymentItem;
import pl.edu.agh.cyberwej.data.objects.User;

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
    private final int count = 3;
    private final float price = 2.45f;

    private User user;

    private Payment payment;
    private Date date;
    private final String description = "Wyjscie na pizze";

    private Group group;
    private final String groupName = "Grupa test";

    private final int newCount = 10;
    private final float newPrice = 3.20f;

    @Before
    public void setUp() {
        this.paymentItem = new PaymentItem();
        this.paymentItem.setPrice(this.price);
        this.paymentItem.setCount(this.count);
        this.user = new User();
        this.user.setName("Wojtek");
        this.user.setSurname("Wojtkowski");
        this.user.setLogin("Wojtecyek");
        this.user.setMail("wojtek2@wojtkowie.pl");
        this.userDAO.saveUser(this.user);
        Set<User> users = new HashSet<User>();
        users.add(this.user);
        this.paymentItem.setConsumers(users);

        this.payment = new Payment();
        this.payment.setDate(this.date);
        this.payment.setDescription(this.description);

        this.group = new Group();
        this.group.setName(this.groupName);

        this.groupDAO.saveGroup(this.group);
        this.groupDAO.addGroupPayment(this.group, this.payment);
        this.paymentDAO.savePaymentItem(this.payment, this.paymentItem);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testSave() {
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertNotNull(retrievedGroup);
        assertNotNull(retrievedGroup.getPayments());
        assertFalse(retrievedGroup.getPayments().isEmpty());
        for (Payment payment : retrievedGroup.getPayments()) {
            assertNotNull(payment);
            assertNotNull(payment.getPaymentItems());
            assertFalse(payment.getPaymentItems().isEmpty());
            for (PaymentItem paymentItem : payment.getPaymentItems()) {
                assertEquals(paymentItem.getCount(), this.count);
                assertEquals(paymentItem.getPrice(), this.price, 0.0f);
            }
        }
        this.paymentItem.setPrice(this.newPrice);
        this.paymentItem.setCount(this.newCount);
        this.paymentItemDAO.save(this.paymentItem);
        retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertNotNull(retrievedGroup);
        assertNotNull(retrievedGroup.getPayments());
        assertFalse(retrievedGroup.getPayments().isEmpty());
        for (Payment payment : retrievedGroup.getPayments()) {
            assertNotNull(payment);
            assertNotNull(payment.getPaymentItems());
            assertFalse(payment.getPaymentItems().isEmpty());
            for (PaymentItem paymentItem : payment.getPaymentItems()) {
                assertEquals(paymentItem.getCount(), this.newCount);
                assertEquals(paymentItem.getPrice(), this.newPrice, 0.0f);
            }
        }
    }
}
