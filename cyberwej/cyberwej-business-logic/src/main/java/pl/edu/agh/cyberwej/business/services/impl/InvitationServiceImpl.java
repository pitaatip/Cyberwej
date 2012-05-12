package pl.edu.agh.cyberwej.business.services.impl;

import org.agh.iosr.cyberwej.data.dao.interfaces.InvitationDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Invitation;
import org.agh.iosr.cyberwej.data.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.agh.cyberwej.business.services.api.InvitationService;

/**
 * 
 * @author Krzysztof
 * 
 */
@Service
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

}
