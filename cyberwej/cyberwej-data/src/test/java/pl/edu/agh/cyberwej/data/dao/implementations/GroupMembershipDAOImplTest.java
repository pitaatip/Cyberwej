package pl.edu.agh.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import pl.edu.agh.cyberwej.data.dao.interfaces.GroupMembershipDAO;
import pl.edu.agh.cyberwej.data.dao.interfaces.UserDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupMembershipDAOImplTest {

    @Autowired
    private GroupMembershipDAO groupMembershipDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private UserDAO userDAO;

    private Group group;
    private final String groupName = "Pierwsza grupa";

    private User user;
    private final String name = "Jan";
    private final String surname = "Kowalski";
    private final String mail = "jan@janowie.pl";
    private final String login = "Janek";

    @BeforeTransaction
    public void setUp() {
        this.user = new User();
        this.user.setSurname(this.surname);
        this.user.setName(this.name);
        this.user.setLogin(this.login);
        this.user.setMail(this.mail);
        this.userDAO.saveUser(this.user);

        this.group = new Group();
        this.group.setName(this.groupName);
        this.groupDAO.saveGroup(this.group);

        this.groupMembershipDAO.addGroupMembership(this.group, this.user);

    }

    @Transactional
    @Rollback(true)
    @Test
    public void testAddGroupMembership() {
        User retrievedUser = this.userDAO.findUserByMail(this.mail);
        assertFalse(retrievedUser.getGroupMemberships().isEmpty());

        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertFalse(retrievedGroup.getGroupMembers().isEmpty());
        assertEquals(retrievedGroup.getGroupMembers(),
                retrievedUser.getGroupMemberships());
        for (GroupMembership groupMembership : retrievedGroup.getGroupMembers()) {
            assertEquals(groupMembership.getOverdraw(), 0.0f, 0.0);
            assertTrue(groupMembership.getJoinDate().before(new Date()));
        }
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRemoveGroupMembership() {
        User retrievedUser = this.userDAO.findUserByMail(this.mail);
        for (GroupMembership groupMembership : retrievedUser
                .getGroupMemberships())
            if (groupMembership.getGroup().getName().equals(this.groupName))
                this.groupMembershipDAO.removeGroupMembership(groupMembership);
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertTrue(retrievedGroup.getGroupMembers().isEmpty());
    }

    @AfterTransaction
    public void clean() {
        this.userDAO.removeUser(this.user);
        this.groupDAO.removeGroup(this.group);
    }
}
