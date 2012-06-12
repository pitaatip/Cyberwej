/**
 * 
 */
package pl.edu.agh.cyberwej.business.integrationTests;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.edu.agh.cyberwej.business.services.api.GroupMembershipService;
import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.InvitationService;
import pl.edu.agh.cyberwej.business.services.api.PaybackService;
import pl.edu.agh.cyberwej.business.services.api.PaymentService;
import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Invitation;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author Pita
 * 
 */
public abstract class BaseIntegrationTest {
    
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupMembershipService groupMembershipService;
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaybackService paybackService;

    private int userNr = 3;
    protected final List<Integer> usersIds = new LinkedList<Integer>();
    protected final List<String> usersLogins = new LinkedList<String>();
    protected int groupId;
    protected String groupName;

    protected void createGroup(LinkedList<User> users) {
        Group group = TestObjectsFactory.getMockGroup();
        getGroupService().saveGroupWithItsMembers(group, users, users.get(0));
        groupId = group.getId();
        groupName = group.getName();
    }

    protected void createUsers() {
        User user = null;
        for (int i = 0; i < getUserNr(); i++) {
            user = TestObjectsFactory.getMockUser();
            getUserService().saveUser(user);
            usersIds.add(user.getId());
            usersLogins.add(user.getLogin());
        }
    }

    protected LinkedList<User> retrieveUsers() {
        LinkedList<User> users = new LinkedList<User>();
        for (Integer userId : usersIds) {
            users.add(getUserService().getUserById(userId));
        }
        return users;
    }
    
    protected void acceptAllInvitations() {
        LinkedList<User> users = retrieveUsers();

        // iterate over all users except owner of the group
        for (int i = 1; i < users.size(); i++) {
            User user = users.get(i);
            List<Invitation> inviationsForUser = getInvitationService().getInviationsForUser(user,
                    true);
            Invitation invitation = inviationsForUser.get(0);
            getInvitationService().acceptInvitation(invitation, true);
        }
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public GroupMembershipService getGroupMembershipService() {
        return groupMembershipService;
    }

    public void setGroupMembershipService(GroupMembershipService groupMembershipService) {
        this.groupMembershipService = groupMembershipService;
    }

    public InvitationService getInvitationService() {
        return invitationService;
    }

    public void setInvitationService(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    public int getUserNr() {
        return userNr;
    }

    public void setUserNr(int userNr) {
        this.userNr = userNr;
    }

    public PaybackService getPaybackService() {
        return paybackService;
    }

    public void setPaybackService(PaybackService paybackService) {
        this.paybackService = paybackService;
    }
}
