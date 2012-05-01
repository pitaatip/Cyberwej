package org.agh.iosr.cyberwej.data.dao.interfaces;

import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Invitation;
import org.agh.iosr.cyberwej.data.objects.User;

public interface InvitationDAO extends IDAO<Invitation> {

    public boolean addInvitation(User inviter, User invitee, Group group);

    public void removeInvitation(Invitation invitation);

}
