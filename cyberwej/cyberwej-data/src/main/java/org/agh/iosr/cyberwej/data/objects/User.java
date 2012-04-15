package org.agh.iosr.cyberwej.data.objects;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	@Column(name = "USERID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "SURNAME", nullable = false)
	private String surname;

	@Column(name = "MAIL", nullable = false, unique = true)
	private String mail;

	@Column(name = "LOGIN", nullable = false, unique = true)
	private String login;

	@Column(name = "LOCATION")
	private String location;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "user", fetch = FetchType.LAZY)
	private Set<GroupMembership> groupMemberships = new HashSet<GroupMembership>();

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "INVITEEID")
	private Set<Invitation> userInvitations;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "INVESTORID")
	private Set<Payback> paybacksForUser;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "DEBTORID")
	private Set<Payback> paybacksForOthers;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Set<GroupMembership> getGroupMemberships() {
		return groupMemberships;
	}

	public void setGroupMemberships(Set<GroupMembership> groupMemberships) {
		this.groupMemberships = groupMemberships;
	}

	public Set<Invitation> getUserInvitations() {
		return userInvitations;
	}

	public void setUserInvitations(Set<Invitation> userInvitations) {
		this.userInvitations = userInvitations;
	}

	public Set<Payback> getPaybacksForUser() {
		return paybacksForUser;
	}

	public void setPaybacksForUser(Set<Payback> paybacksForUser) {
		this.paybacksForUser = paybacksForUser;
	}

	public Set<Payback> getPaybacksForOthers() {
		return paybacksForOthers;
	}

	public void setPaybacksForOthers(Set<Payback> paybacksForOthers) {
		this.paybacksForOthers = paybacksForOthers;
	}

	public void addGroupMembership(GroupMembership groupMembership) {
		this.groupMemberships.add(groupMembership);
	}
}
