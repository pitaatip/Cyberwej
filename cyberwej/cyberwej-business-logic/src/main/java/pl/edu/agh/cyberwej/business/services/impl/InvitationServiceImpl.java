package pl.edu.agh.cyberwej.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.business.services.api.GroupMembershipService;
import pl.edu.agh.cyberwej.business.services.api.InvitationService;
import pl.edu.agh.cyberwej.data.dao.interfaces.InvitationDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Invitation;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */
@Service(value="invitationService")
public class InvitationServiceImpl implements InvitationService {

    @Autowired
    private InvitationDAO invitationDAO;
    
    @Autowired
    private GroupMembershipService groupMembershipService;

    @Override
    public boolean inviteUser(User inviter, User invitee, Group group) {
        return this.invitationDAO.addInvitation(inviter, invitee, group);
    }

    @Override
    @Transactional
    public void acceptInvitation(Invitation invitation, boolean isAccepted) {
        if(isAccepted) {
            groupMembershipService.addGroupMember(invitation.getGroup(), invitation.getReceiver());
            invitationDAO.acceptInvitation(invitation);
        } //else - delete invitation or what?
    }
    
    @Override
    public List<Invitation> getInviationsForUser(User invite, boolean onlyUnaccepted) {
        return invitationDAO.getInviationsForUser(invite, onlyUnaccepted);
    }

    @Transactional
    @Override
    public void acceptInvitationById(int invitationId, boolean isAccepted) {
        acceptInvitation(invitationDAO.getById(invitationId), isAccepted);
    }
}
