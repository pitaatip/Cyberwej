package org.agh.iosr.cyberwej.data.objects;

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

	private int id;

	private String name;

	private String surname;

	private String mail;

	private String login;

	private String location;

	private Set<GroupMembership> groupMemberships;

	private Set<Invitation> userInvitations;

	private Set<Payback> paybacksForUser;

	private Set<Payback> paybacksForOthers;

	@Id
	@Column(name = "USERID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SURNAME", nullable = false)
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Column(name = "MAIL", nullable = false, unique = true)
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name = "LOGIN", nullable = false, unique = true)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "LOCATION")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "USERID")
	public Set<GroupMembership> getGroupMemberships() {
		return groupMemberships;
	}

	public void setGroupMemberships(Set<GroupMembership> groupMemberships) {
		this.groupMemberships = groupMemberships;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "INVITEEID")
	public Set<Invitation> getUserInvitations() {
		return userInvitations;
	}

	public void setUserInvitations(Set<Invitation> userInvitations) {
		this.userInvitations = userInvitations;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "INVESTORID")
	public Set<Payback> getPaybacksForUser() {
		return paybacksForUser;
	}

	public void setPaybacksForUser(Set<Payback> paybacksForUser) {
		this.paybacksForUser = paybacksForUser;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "DEBTORID")
	public Set<Payback> getPaybacksForOthers() {
		return paybacksForOthers;
	}

	public void setPaybacksForOthers(Set<Payback> paybacksForOthers) {
		this.paybacksForOthers = paybacksForOthers;
	}
}
