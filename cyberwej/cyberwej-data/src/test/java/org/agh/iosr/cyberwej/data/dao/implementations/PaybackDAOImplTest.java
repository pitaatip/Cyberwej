package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.*;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.PaybackDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payback;
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
public class PaybackDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private PaybackDAO paybackDAO;

    private User debtor;
    private String debtorName = "Jan";
    private String debtorSurname = "Nowak";
    private String debtorMail = "jan1@jan.pl";
    private String debtorLogin = "Jan1";

    private User investor;
    private String investorName = "Janina";
    private String investorSurname = "Nowak";
    private String investorMail = "janina1@jan.pl";
    private String investorLogin = "Janina1";

    private Group group;
    private String groupName = "Pierwsza grupa 2";

    private float amount = 3.50f;

    @BeforeTransaction
    public void setUp() {
        this.debtor = new User();
        this.debtor.setName(debtorName);
        this.debtor.setSurname(debtorSurname);
        this.debtor.setMail(debtorMail);
        this.debtor.setLogin(debtorLogin);

        this.investor = new User();
        this.investor.setName(investorName);
        this.investor.setSurname(investorSurname);
        this.investor.setMail(investorMail);
        this.investor.setLogin(investorLogin);

        this.group = new Group();
        group.setName(groupName);

        this.groupDAO.saveGroup(group);
        this.userDAO.saveUser(debtor);
        this.userDAO.saveUser(investor);
        this.paybackDAO.addPayback(debtor, investor, group, amount);
    }

    @Transactional
    @Rollback(false)
    @Test
    public void testAddPayback() {
        User retrievedUser = this.userDAO.findUserByMail(this.investorMail);
        assertNotNull(retrievedUser);
        assertFalse(retrievedUser.getPaybacksForUser().isEmpty());
        for (Payback payback : retrievedUser.getPaybacksForUser()) {
            assertEquals(payback.getDebtor().getLogin(), this.debtorLogin);
            assertEquals(payback.getGroup().getName(), this.groupName);
            assertEquals(payback.getAmount(), this.amount, 0.0);
            assertEquals(payback.isAccepted(), false);
        }
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRemovePayback() {
        User retrievedUser = this.userDAO.findUserByMail(this.investorMail);
        assertNotNull(retrievedUser);
        assertFalse(retrievedUser.getPaybacksForUser().isEmpty());
        for (Payback payback : retrievedUser.getPaybacksForUser())
            this.paybackDAO.removePayback(payback);
        User retrievedDebtor = this.userDAO.findUserByMail(this.debtorMail);
        assertNotNull(retrievedDebtor);
        assertTrue(retrievedDebtor.getPaybacksForOthers().isEmpty());
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testUpdatePayback() {
        User retrievedUser = this.userDAO.findUserByMail(this.investorMail);
        assertNotNull(retrievedUser);
        assertFalse(retrievedUser.getPaybacksForUser().isEmpty());
        for (Payback payback : retrievedUser.getPaybacksForUser()) {
            payback.setAccepted(true);
            this.paybackDAO.updatePayback(payback);
        }
        User retrievedDebtor = this.userDAO.findUserByMail(this.debtorMail);
        assertNotNull(retrievedDebtor);
        assertFalse(retrievedDebtor.getPaybacksForOthers().isEmpty());
        for (Payback payback : retrievedDebtor.getPaybacksForOthers())
            assertTrue(payback.isAccepted());
    }

    @AfterTransaction
    public void clean() {
        this.userDAO.removeUser(investor);
        this.userDAO.removeUser(debtor);
        this.groupDAO.removeGroup(group);
    }
}
