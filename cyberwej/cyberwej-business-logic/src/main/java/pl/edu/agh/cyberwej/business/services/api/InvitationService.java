package pl.edu.agh.cyberwej.business.services.api;

import java.util.List;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Invitation;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author Krzysztof
 */
public interface InvitationService {

    boolean inviteUser(User inviter, User invitee, Group group);

    boolean inviteUser(Integer inviterId, Integer inviteeId, Integer groupId);

    void acceptInvitation(Invitation invitation, boolean isAccepted);

    List<Invitation> getInviationsForUser(User invite, boolean onlyUnaccepted);

    void acceptInvitationById(int invitationId, boolean isAccepted);
}
