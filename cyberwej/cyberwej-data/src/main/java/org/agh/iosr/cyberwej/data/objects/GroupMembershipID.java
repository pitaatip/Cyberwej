package org.agh.iosr.cyberwej.data.objects;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class GroupMembershipID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1129047153997804572L;

	private User user;

	private Group group;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
