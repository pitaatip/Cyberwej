package org.agh.iosr.cyberwej.data.objects.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.User;

@Embeddable
public class InvitationId implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3101457842390285087L;

    private User invitee;

    private Group group;

    public User getInvitee() {
        return invitee;
    }

    public void setInvitee(User invitee) {
        this.invitee = invitee;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
