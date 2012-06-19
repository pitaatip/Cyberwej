package pl.edu.agh.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import pl.edu.agh.cyberwej.data.dao.interfaces.PaybackDAO;
import pl.edu.agh.cyberwej.data.dao.interfaces.UserDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Payback;
import pl.edu.agh.cyberwej.data.objects.User;

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
    private final String debtorName = "Jan";
    private final String debtorSurname = "Nowak";
    private final String debtorMail = "jan1@jan.pl";
    private final String debtorLogin = "Jan1";

    private User investor;
    private final String investorName = "Janina";
    private final String investorSurname = "Nowak";
    private final String investorMail = "janina1@jan.pl";
    private final String investorLogin = "Janina1";

    private Group group;
    private final String groupName = "Pierwsza grupa 2";

    private final float amount = 3.50f;

    @BeforeTransaction
    public void setUp() {
        this.debtor = new User();
        this.debtor.setName(this.debtorName);
        this.debtor.setSurname(this.debtorSurname);
        this.debtor.setMail(this.debtorMail);
        this.debtor.setLogin(this.debtorLogin);

        this.investor = new User();
        this.investor.setName(this.investorName);
        this.investor.setSurname(this.investorSurname);
        this.investor.setMail(this.investorMail);
        this.investor.setLogin(this.investorLogin);

        this.group = new Group();
        this.group.setName(this.groupName);

        this.groupDAO.saveGroup(this.group);
        this.userDAO.saveUser(this.debtor);
        this.userDAO.saveUser(this.investor);
        this.paybackDAO.addPayback(this.debtor, this.investor, this.group, this.amount);
    }

    @Transactional
    @Rollback(false)
    @Test
    public void testAddPayback() {
        User retrievedUser = this.userDAO.findUserByMail(this.investorMail);
        assertNotNull(retrievedUser);
        assertFalse(retrievedUser.getPaybacksForUser().isEmpty());
        for (Payback payback : retrievedUser.getPaybacksForUser()) {
            assertEquals(payback.getSender().getLogin(), this.debtorLogin);
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
        this.userDAO.removeUser(this.investor);
        this.debtor = this.userDAO.findUserByMail(this.debtorMail);
        this.userDAO.removeUser(this.debtor);
        this.group = this.groupDAO.getGroupByName(this.groupName);
        this.groupDAO.removeGroup(this.group);
    }
}
