package org.agh.iosr.cyberwej.data.objects;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GROUPMEMBERS")
public class GroupMember implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7580732888585727870L;

	private float overdraw;

	private Group group;
	
	private User user;
	
	@Column(name = "OVERDRAW")
	public float getOverdraw() {
		return overdraw;
	}

	public void setOverdraw(float overdraw) {
		this.overdraw = overdraw;
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "GROUPID")
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	@Id
	@ManyToOne
	@JoinColumn(name = "USERID")
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	
	
}
