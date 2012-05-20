package pl.edu.agh.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
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
import pl.edu.agh.cyberwej.data.dao.interfaces.GroupMembershipDAO;
import pl.edu.agh.cyberwej.data.dao.interfaces.UserDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.User;

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupDAOImplTest {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired 
    private UserDAO userDAO;
    
    @Autowired
    private GroupMembershipDAO grMemDao;
    
    private Group group;

    private final String groupName = "Pierwsza grupa";

    private final String nonExistingGroupName = "Nie ma takiej grupy";

    private Payment payment;
    private Date date;
    private final String description = "Wyjscie na pizze";

    
    @Before
    public void setUp() {
        this.date = new Date();

        this.payment = new Payment();
        this.payment.setDate(this.date);
        this.payment.setDescription(this.description);

        this.group = new Group();
        this.group.setName(this.groupName);
        this.groupDAO.saveGroup(this.group);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testSaveGroup() {
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertNotNull(retrievedGroup);
        assertEquals(retrievedGroup.getName(), this.groupName);
        assertNotNull(retrievedGroup.getId());
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRemoveGroup() {
        this.groupDAO.removeGroup(this.group);
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertNull(retrievedGroup);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testGetGroupByName() {
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertNotNull(retrievedGroup);
        retrievedGroup = this.groupDAO
                .getGroupByName(this.nonExistingGroupName);
        assertNull(retrievedGroup);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testAddGroupPayment() {
        this.groupDAO.addGroupPayment(this.group, this.payment);
        Set<Payment> groupPayments = this.groupDAO.getGroupByName(
                this.groupName).getPayments();
        assertFalse(groupPayments.isEmpty());
        Payment retrievedPayment = groupPayments.iterator().next();
        assertEquals(retrievedPayment.getId(), this.payment.getId());
        /*
         * assertEquals(retrievedPayment.getPaymentItems().iterator().next()
         * .getId(), this.paymentItem.getId());
         */assertEquals(retrievedPayment.getDate().getTime() / 1000,
                this.payment.getDate().getTime() / 1000);
        assertEquals(retrievedPayment.getDescription(),
                this.payment.getDescription());
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRemoveGroupPayment() {
        this.groupDAO.addGroupPayment(this.group, this.payment);
        Set<Payment> groupPayments = this.groupDAO.getGroupByName(
                this.groupName).getPayments();
        assertFalse(groupPayments.isEmpty());

        this.groupDAO.removeGroupPayment(this.group, this.payment);
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertTrue(retrievedGroup.getPayments().isEmpty());
    }


    public UserDAO getUserDAO() {
        return userDAO;
    }


    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public GroupMembershipDAO getGrMemDao() {
        return grMemDao;
    }


    public void setGrMemDao(GroupMembershipDAO grMemDao) {
        this.grMemDao = grMemDao;
    }

}
