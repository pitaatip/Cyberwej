package pl.edu.agh.cyberwej.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean inviteUser(User inviter, User invitee, Group group) {
        return this.invitationDAO.addInvitation(inviter, invitee, group);
    }

    @Override
    public void acceptInvitation(Invitation invitation, boolean isAccepted) {
        // TODO add groupMembership
    }
    
    public List<Invitation> getInviationsForUser(User invite) {
        return invitationDAO.getInviationsForUser(invite);
    }

}
