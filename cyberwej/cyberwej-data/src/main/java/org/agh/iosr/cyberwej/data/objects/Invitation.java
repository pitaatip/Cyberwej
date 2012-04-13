package org.agh.iosr.cyberwej.data.objects;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "INVITATIONS")
public class Invitation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5602015934199476337L;

	private User inviter;

	private User invitee;

	private Group group;

	@Id
	@ManyToOne
	@JoinColumn(name = "INVITERID", nullable = false)
	public User getInviter() {
		return inviter;
	}

	public void setInviter(User inviter) {
		this.inviter = inviter;
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "INVITEEID", nullable = false)
	public User getInvitee() {
		return invitee;
	}

	public void setInvitee(User invitee) {
		this.invitee = invitee;
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "GROUPID", nullable = false)
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}
