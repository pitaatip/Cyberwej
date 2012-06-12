package pl.edu.agh.cyberwej.business.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.Invitation;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Pita
 * 
 */
@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CreateGroupTest extends BaseIntegrationTest {

    private static final int userNr = 3;

    @Before
    public void registerUsersAndGroup() {

        // create and save users
        createUsers();
        LinkedList<User> users = retrieveUsers();

        // create and save group
        createGroup(users);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRetrieveGroup() {
        Group groupById = getGroupService().getGroupById(groupId);
        assertNotNull(groupById);
        assertEquals(groupName, groupById.getName());
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRetrieveGroupWithOwnerOnly() {
        Group groupById = getGroupService().getGroupWithMembersAndPayments(groupId);
        assertNotNull(groupById);
        assertEquals(groupName, groupById.getName());
        // now the group contains only the owner
        assertEquals(groupById.getGroupMembers().size(), 1);
        for (GroupMembership member : groupById.getGroupMembers()) {
            assertEquals(usersIds.get(0), member.getUser().getId());
            assertEquals(usersLogins.get(0), member.getUser().getLogin());
        }
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testAllUsersWereInvited() {
        LinkedList<User> users = retrieveUsers();

        // iterate over all users except owner of the group
        for (int i = 1; i < users.size(); i++) {
            User user = users.get(i);
            List<Invitation> inviationsForUser = getInvitationService().getInviationsForUser(user,
                    true);
            assertNotNull(inviationsForUser);
            assertEquals(inviationsForUser.size(), 1);
            Invitation invitation = inviationsForUser.get(0);
            assertEquals(invitation.getSender().getId(), usersIds.get(0));
            assertEquals(invitation.getGroup().getId(), new Integer(groupId));
            assertEquals(invitation.getReceiver().getId(), user.getId());
        }

        // make sure owner hasn't been invited
        User user = users.get(0);
        List<Invitation> inviationsForUser = getInvitationService()
                .getInviationsForUser(user, true);
        assertNotNull(inviationsForUser);
        assertEquals(inviationsForUser.size(), 0);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRetrieveAllGroupMembers() {
        // Accept all invitations
        acceptAllInvitations();

        Group groupById = getGroupService().getGroupWithMembersAndPayments(groupId);
        assertNotNull(groupById);
        assertEquals(groupName, groupById.getName());
        assertEquals(groupById.getGroupMembers().size(), userNr);
        for (GroupMembership member : groupById.getGroupMembers()) {
            assertTrue(usersIds.contains(member.getUser().getId()));
            assertTrue(usersLogins.contains(member.getUser().getLogin()));
        }
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testAddMemberToGroup() {
        acceptAllInvitations();

        // Add next user to group
        Group groupById = getGroupService().getGroupWithMembersAndPayments(groupId);
        User user = TestObjectsFactory.getMockUser();
        getUserService().saveUser(user);
        user = getUserService().getUserById(user.getId());
        getInvitationService().inviteUser(usersIds.get(0), user.getId(), groupId);

        // Accept invitation
        List<Invitation> inviationsForUser = getInvitationService()
                .getInviationsForUser(user, true);
        assertNotNull(inviationsForUser);
        assertEquals(inviationsForUser.size(), 1);
        Invitation invitation = inviationsForUser.get(0);
        getInvitationService().acceptInvitation(invitation, true);

        // Retrieve group and check if user was added
        groupById = getGroupService().getGroupWithMembersAndPayments(groupId);
        assertNotNull(groupById);
        assertEquals(groupName, groupById.getName());
        assertEquals(groupById.getGroupMembers().size(), userNr + 1);
        for (GroupMembership member : groupById.getGroupMembers()) {
            // Test previously added users
            if (!member.getUser().getId().equals(user.getId())) {
                assertTrue(usersIds.contains(member.getUser().getId()));
                assertTrue(usersLogins.contains(member.getUser().getLogin()));
            }
            // Test new group member
            else {
                assertEquals(user.getLogin(), member.getUser().getLogin());
                assertEquals(user.getId(), member.getUser().getId());
            }
        }
    }

}
