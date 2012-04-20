package org.agh.iosr.cyberwej.data.dao.implementations;

import org.agh.iosr.cyberwej.data.dao.interfaces.InvitationDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Invitation;
import org.agh.iosr.cyberwej.data.objects.User;
import org.springframework.stereotype.Repository;

@Repository
public class InvitationDAOImpl extends DAOBase implements InvitationDAO {

	@Override
	public boolean addInvitation(User inviter, User invitee, Group group) {
		Invitation invitation = new Invitation();
		invitation.setGroup(group);
		invitation.setInvitee(invitee);
		invitation.setInviter(inviter);
		return super.save(invitation);
	}

	@Override
	public void removeInvitation(Invitation invitation) {
		super.hibernateTemplate.delete(invitation);
	}

}
