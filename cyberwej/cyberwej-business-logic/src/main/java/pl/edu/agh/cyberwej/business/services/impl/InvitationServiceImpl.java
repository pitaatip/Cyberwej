package pl.edu.agh.cyberwej.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.business.services.api.GroupMembershipService;
import pl.edu.agh.cyberwej.business.services.api.InvitationService;
import pl.edu.agh.cyberwej.data.dao.interfaces.GroupDAO;
import pl.edu.agh.cyberwej.data.dao.interfaces.InvitationDAO;
import pl.edu.agh.cyberwej.data.dao.interfaces.UserDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.Invitation;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author Krzysztof
 */
@Service(value = "invitationService")
public class InvitationServiceImpl implements InvitationService {

    @Autowired
    private InvitationDAO invitationDAO;

    @Autowired
    private GroupMembershipService groupMembershipService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Override
    public boolean inviteUser(User inviter, User invitee, Group group) {
        return this.invitationDAO.addInvitation(inviter, invitee, group);
    }

    @Override
    @Transactional
    public void acceptInvitation(Invitation invitation, boolean isAccepted) {
        if (isAccepted) {
            this.groupMembershipService.addGroupMember(invitation.getGroup(),
                    invitation.getReceiver());
            this.invitationDAO.acceptInvitation(invitation);
        } // else - delete invitation or what?
    }

    @Override
    public List<Invitation> getInviationsForUser(User invite, boolean onlyUnaccepted) {
        return this.invitationDAO.getInviationsForUser(invite, onlyUnaccepted);
    }

    @Transactional
    @Override
    public void acceptInvitationById(int invitationId, boolean isAccepted) {
        acceptInvitation(this.invitationDAO.getById(invitationId), isAccepted);
    }

    @Transactional
    @Override
    public boolean inviteUser(Integer inviterId, Integer inviteeId, Integer groupId) {
        User inviter = this.userDAO.getById(inviterId);
        User invitee = this.userDAO.getById(inviteeId);
        Group group = this.groupDAO.getById(groupId);
        for (GroupMembership groupMembership : group.getGroupMembers()) {
            if (groupMembership.getUser().getId().equals(inviteeId)) {
                return false;
            }
        }
        return this.invitationDAO.addInvitation(inviter, invitee, group);
    }
}
