package pl.edu.agh.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

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
import pl.edu.agh.cyberwej.data.dao.interfaces.PaymentParticipationDAO;
import pl.edu.agh.cyberwej.data.dao.interfaces.UserDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.PaymentParticipation;
import pl.edu.agh.cyberwej.data.objects.User;

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentParticipationDAOImplTest {

    @Autowired
    private PaymentParticipationDAO paymentParticipationDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PaymentDAO paymentDAO;

    @Autowired
    private GroupDAO groupDAO;

    private Group group;
    private final String groupName = "Pierwsza grupa";

    private User user;
    private final String name = "Marek";
    private final String surname = "Markowski";
    private final String mail = "marek@markowie.pl";
    private final String login = "Marek";

    private Payment payment;
    private Date date;
    private final String description = "Wyjscie na 2 pizze";

    private final float amount = 3.45f;

    private final float newAmount = 2.50f;

    @BeforeTransaction
    public void setUp() {
        this.user = new User();
        this.user.setSurname(this.surname);
        this.user.setName(this.name);
        this.user.setLogin(this.login);
        this.user.setMail(this.mail);

        this.payment = new Payment();
        this.payment.setDate(this.date);
        this.payment.setDescription(this.description);

        this.group = new Group();
        this.group.setName(this.groupName);

        this.userDAO.saveUser(this.user);
        this.groupDAO.saveGroup(this.group);
        this.groupDAO.addGroupPayment(this.group, this.payment);
        this.paymentDAO.addPaymentParticipation(this.payment, this.user, this.amount);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testSave() {
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertNotNull(retrievedGroup);
        assertNotNull(retrievedGroup.getPayments());
        assertFalse(retrievedGroup.getPayments().isEmpty());
        for (Payment payment : retrievedGroup.getPayments()) {
            assertFalse(payment.getParticipations().isEmpty());
            for (PaymentParticipation paymentParticipation : payment.getParticipations()) {
                assertEquals(paymentParticipation.getAmount(), this.amount, 0.0f);
                paymentParticipation.setAmount(this.newAmount);
                this.paymentParticipationDAO.save(paymentParticipation);
            }
        }
        retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertNotNull(retrievedGroup);
        assertNotNull(retrievedGroup.getPayments());
        assertFalse(retrievedGroup.getPayments().isEmpty());
        for (Payment payment : retrievedGroup.getPayments()) {
            assertFalse(payment.getParticipations().isEmpty());
            for (PaymentParticipation paymentParticipation : payment.getParticipations())
                assertEquals(paymentParticipation.getAmount(), this.newAmount, 0.0f);
        }

    }

    @AfterTransaction
    public void clean() {
        Group group = this.groupDAO.getGroupByName(this.groupName);
        this.groupDAO.removeGroup(group);
        this.userDAO.removeUser(this.user);
    }
}
