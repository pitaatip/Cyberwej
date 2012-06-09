package pl.edu.agh.cyberwej.business.services.api;

import java.util.List;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Invitation;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */
public interface InvitationService {

    public boolean inviteUser(User inviter, User invitee, Group group);

    public boolean inviteUser(Integer inviterId, Integer inviteeId,
            Integer groupId);

    public void acceptInvitation(Invitation invitation, boolean isAccepted);

    public List<Invitation> getInviationsForUser(User invite,
            boolean onlyUnaccepted);

    public void acceptInvitationById(int invitationId, boolean isAccepted);
}
