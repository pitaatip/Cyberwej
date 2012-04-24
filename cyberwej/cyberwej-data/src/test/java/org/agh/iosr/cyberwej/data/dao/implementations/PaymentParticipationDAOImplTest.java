package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentParticipationDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payment;
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
    private String groupName = "Pierwsza grupa";

    private User user;
    private String name = "Marek";
    private String surname = "Markowski";
    private String mail = "marek@markowie.pl";
    private String login = "Marek";

    private Payment payment;
    private Date date;
    private String description = "Wyjscie na 2 pizze";

    private float amount = 3.45f;

    @BeforeTransaction
    public void setUp() {
        this.user = new User();
        user.setSurname(this.surname);
        user.setName(this.name);
        user.setLogin(this.login);
        user.setMail(this.mail);

        this.payment = new Payment();
        this.payment.setDate(date);
        this.payment.setDescription(description);

        this.group = new Group();
        group.setName(groupName);

        this.userDAO.saveUser(user);
        this.groupDAO.saveGroup(group);
        this.paymentDAO.addGroupPayment(group, payment);
        this.paymentParticipationDAO.addPaymentParticipation(payment, user,
                amount);
    }

    @Transactional
    @Rollback(false)
    @Test
    public void testAddPaymentParticipation() {
        Group retrievedGroup = groupDAO.getGroupByName(groupName);
        assertNotNull(retrievedGroup);
        assertFalse(retrievedGroup.getPayments().isEmpty());
        for (Payment payment : retrievedGroup.getPayments()) {
            assertFalse(payment.getParticipations().isEmpty());
            for (PaymentParticipation participation : payment
                    .getParticipations()) {
                assertEquals(participation.getAmount(), this.amount, 0.0);
                assertEquals(participation.getUser().getName(), this.name);
            }
        }
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRemovePaymentParticipation() {
        for (Payment payment : group.getPayments()) {
            for (PaymentParticipation participation : payment
                    .getParticipations()) {
                this.paymentParticipationDAO
                        .removePaymentParticipation(participation);
            }
        }
        Group retrievedGroup = groupDAO.getGroupByName(groupName);
        assertNotNull(retrievedGroup);
        assertFalse(retrievedGroup.getPayments().isEmpty());
        for (Payment payment : retrievedGroup.getPayments()) {
            assertTrue(payment.getParticipations().isEmpty());
        }
    }

    @AfterTransaction
    public void clean() {
        Group group = this.groupDAO.getGroupByName(groupName);
        this.groupDAO.removeGroup(group);
        this.userDAO.removeUser(user);
    }
}
