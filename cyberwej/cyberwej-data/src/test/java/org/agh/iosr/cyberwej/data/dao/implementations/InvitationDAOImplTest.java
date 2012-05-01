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
    private final String inviteeName = "Jan";
    private final String inviteeSurname = "Kowalski";
    private final String inviteeMail = "jan@janowie.pl";
    private final String inviteeLogin = "Janek";

    private User inviter;
    private final String inviterName = "Janina";
    private final String inviterSurname = "Kowalski";
    private final String inviterMail = "janina@janowie.pl";
    private final String inviterLogin = "Janka";

    private Group group;
    private final String groupName = "Pierwsza grupa";

    @BeforeTransaction
    public void setUp() {
        this.invitee = new User();
        this.invitee.setName(this.inviteeName);
        this.invitee.setSurname(this.inviteeSurname);
        this.invitee.setMail(this.inviteeMail);
        this.invitee.setLogin(this.inviteeLogin);

        this.inviter = new User();
        this.inviter.setName(this.inviterName);
        this.inviter.setSurname(this.inviterSurname);
        this.inviter.setMail(this.inviterMail);
        this.inviter.setLogin(this.inviterLogin);

        this.group = new Group();
        this.group.setName(this.groupName);
        this.groupDAO.saveGroup(this.group);
        this.userDAO.saveUser(this.invitee);
        this.userDAO.saveUser(this.inviter);
        this.invitationDAO
                .addInvitation(this.inviter, this.invitee, this.group);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testAddInvitation() {
        User retrievedInvitee = this.userDAO.findUserByMail(this.inviteeMail);
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertNotNull(retrievedGroup);
        assertNotNull(retrievedInvitee);
        assertNotNull(retrievedInvitee.getUserInvitations());
        assertNotNull(retrievedGroup.getInvitations());
        assertFalse(retrievedGroup.getInvitations().isEmpty());
        assertFalse(retrievedInvitee.getUserInvitations().isEmpty());
        for (Invitation invitation : retrievedInvitee.getUserInvitations())
            assertTrue(invitation.getSender().getMail()
                    .equals(this.inviterMail));
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRemoveInvitation() {
        User retrievedInvitee = this.userDAO.findUserByMail(this.inviteeMail);
        for (Invitation invitation : retrievedInvitee.getUserInvitations())
            this.invitationDAO.removeInvitation(invitation);
        Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
        assertNotNull(retrievedGroup);
        assertTrue(retrievedGroup.getInvitations().isEmpty());
    }

    @AfterTransaction
    public void clean() {
        this.invitee = this.userDAO.findUserByMail(this.inviteeMail);
        this.userDAO.removeUser(this.invitee);
        this.inviter = this.userDAO.findUserByMail(this.inviterMail);
        this.userDAO.removeUser(this.inviter);
        this.group = this.groupDAO.getGroupByName(this.groupName);
        this.groupDAO.removeGroup(this.group);
    }
}
