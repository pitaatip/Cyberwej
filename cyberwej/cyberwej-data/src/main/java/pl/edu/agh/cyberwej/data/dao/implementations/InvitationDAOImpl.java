package pl.edu.agh.cyberwej.data.dao.implementations;

import org.springframework.stereotype.Repository;

import pl.edu.agh.cyberwej.data.dao.interfaces.InvitationDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.Invitation;
import pl.edu.agh.cyberwej.data.objects.User;

@Repository
public class InvitationDAOImpl extends DAOBase<Invitation> implements
        InvitationDAO {

    @Override
    public boolean addInvitation(User inviter, User invitee, Group group) {
        Invitation invitation = new Invitation();
        invitation.setGroup(group);
        invitation.setReceiver(invitee);
        invitation.setSender(inviter);
        invitee.getUserInvitations().add(invitation);
        group.getInvitations().add(invitation);
        return super.save(invitation);
    }

    @Override
    public void removeInvitation(Invitation invitation) {
        invitation.getReceiver().getUserInvitations().remove(invitation);
        invitation.getGroup().getInvitations().remove(invitation);
        super.hibernateTemplate.delete(invitation);
    }

}
