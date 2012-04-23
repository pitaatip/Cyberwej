package org.agh.iosr.cyberwej.data.objects;

import java.util.HashSet;
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
@Table(name = "GROUPS")
public class Group {

	private Integer id;

	private String name;

	private Set<GroupMembership> groupMembers = new HashSet<GroupMembership>();

	private Set<Invitation> invitations;

	private Set<Payment> payments;

	private Set<Payback> paybacks;

	@Id
	@Column(name = "GROUPID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "group")
	public Set<GroupMembership> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(Set<GroupMembership> groupMembers) {
		this.groupMembers = groupMembers;
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "group")
	public Set<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(Set<Invitation> invitations) {
		this.invitations = invitations;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "GROUPID", nullable = false)
	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	public void addGroupMembership(GroupMembership groupMembership) {
		this.groupMembers.add(groupMembership);
	}

	public void addPayment(Payment payment) {
		if (this.payments == null)
			this.payments = new HashSet<Payment>();
		this.payments.add(payment);
	}

	public void removeGroupMembership(GroupMembership groupMembership) {
		if (this.groupMembers != null)
			this.groupMembers.remove(groupMembership);
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "group")
	public Set<Payback> getPaybacks() {
		return paybacks;
	}

	public void setPaybacks(Set<Payback> paybacks) {
		this.paybacks = paybacks;
	}

}
