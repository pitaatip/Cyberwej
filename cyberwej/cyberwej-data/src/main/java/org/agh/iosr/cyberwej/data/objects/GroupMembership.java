package org.agh.iosr.cyberwej.data.objects;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GROUPMEMBERSHIPS")
public class GroupMembership implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 470204746629470622L;

	private float overdraw;

	private Group group;

	private User user;

	@Column(name = "OVERDRAW", nullable = false)
	public float getOverdraw() {
		return overdraw;
	}

	public void setOverdraw(float overdraw) {
		this.overdraw = overdraw;
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

	@Id
	@ManyToOne
	@JoinColumn(name = "USERID", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
