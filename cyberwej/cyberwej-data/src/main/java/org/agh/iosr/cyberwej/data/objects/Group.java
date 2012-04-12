package org.agh.iosr.cyberwej.data.objects;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="GROUPS")
public class Group implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5297224439165603332L;

	private int id;
	
	private String name;
	
	private Set<GroupMember> groupMembers;

	@Id
	@Column(name="GROUPID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="NAME", unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name="GROUPID")
	public Set<GroupMember> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(Set<GroupMember> groupMembers) {
		this.groupMembers = groupMembers;
	}
	
}
