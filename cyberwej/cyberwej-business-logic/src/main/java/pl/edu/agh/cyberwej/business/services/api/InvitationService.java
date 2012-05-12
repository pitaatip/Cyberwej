package pl.edu.agh.cyberwej.business.services.api;

import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Invitation;
import org.agh.iosr.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */
public interface InvitationService {

    public boolean inviteUser(User inviter, User invitee, Group group);

    public void acceptInvitation(Invitation invitation, boolean isAccepted);
}
