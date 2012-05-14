package pl.edu.agh.cyberwej.data.dao.interfaces;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Invitation;
import pl.edu.agh.cyberwej.data.objects.User;

public interface InvitationDAO extends IDAO<Invitation> {

    public boolean addInvitation(User inviter, User invitee, Group group);

    public void removeInvitation(Invitation invitation);

}
