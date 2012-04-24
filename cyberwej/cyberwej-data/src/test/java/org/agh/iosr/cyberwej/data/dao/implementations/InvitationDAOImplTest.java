package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.InvitationDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Invitation;
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
public class InvitationDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private InvitationDAO invitationDAO;

    private User invitee;
    private String inviteeName = "Jan";
    private String inviteeSurname = "Kowalski";
    private String inviteeMail = "jan@janowie.pl";
    private String inviteeLogin = "Janek";

    private User inviter;
    private String inviterName = "Janina";
    private String inviterSurname = "Kowalski";
    private String inviterMail = "janina@janowie.pl";
    private String inviterLogin = "Janka";

    private Group group;
    private String groupName = "Pierwsza grupa";

    @BeforeTransaction
    public void setUp() {
        this.invitee = new User();
        this.invitee.setName(inviteeName);
        this.invitee.setSurname(inviteeSurname);
        this.invitee.setMail(inviteeMail);
        this.invitee.setLogin(inviteeLogin);

        this.inviter = new User();
        this.inviter.setName(inviterName);
        this.inviter.setSurname(inviterSurname);
        this.inviter.setMail(inviterMail);
        this.inviter.setLogin(inviterLogin);

        this.group = new Group();
        group.setName(groupName);
        this.groupDAO.saveGroup(group);
        this.userDAO.saveUser(invitee);
        this.userDAO.saveUser(inviter);
        this.invitationDAO.addInvitation(inviter, invitee, group);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testAddInvitation() {
        User retrievedInvitee = userDAO.findUserByMail(inviteeMail);
        Group retrievedGroup = groupDAO.getGroupByName(this.groupName);
        assertNotNull(retrievedGroup);
        assertNotNull(retrievedInvitee);
        assertNotNull(retrievedInvitee.getUserInvitations());
        assertNotNull(retrievedGroup.getInvitations());
        assertFalse(retrievedGroup.getInvitations().isEmpty());
        assertFalse(retrievedInvitee.getUserInvitations().isEmpty());
        for (Invitation invitation : retrievedInvitee.getUserInvitations())
            assertTrue(invitation.getInviter().getMail()
                    .equals(this.inviterMail));
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRemoveInvitation() {
        User retrievedInvitee = userDAO.findUserByMail(inviteeMail);
        for (Invitation invitation : retrievedInvitee.getUserInvitations())
            this.invitationDAO.removeInvitation(invitation);
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertNotNull(retrievedGroup);
        assertTrue(retrievedGroup.getInvitations().isEmpty());
    }

    @AfterTransaction
    public void clean() {
        try {
            this.userDAO.removeUser(this.invitee);
            this.userDAO.removeUser(this.inviter);
            this.groupDAO.removeGroup(this.group);
        } catch (Exception e) {

        }
    }
}
