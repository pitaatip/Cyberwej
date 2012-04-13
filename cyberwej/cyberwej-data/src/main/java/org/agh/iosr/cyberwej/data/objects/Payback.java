package org.agh.iosr.cyberwej.data.objects;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "PAYBACKS")
public class Payback implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7646180639825723045L;

	private User debtor;

	private User investor;

	private Group group;

	private float amount;

	private Boolean isAccepted;

	@Id
	@ManyToOne
	@JoinColumn(name = "DEBTORID", nullable = false)
	public User getDebtor() {
		return debtor;
	}

	public void setDebtor(User debtor) {
		this.debtor = debtor;
	}

	@Id
	@ManyToOne
	@JoinColumn(name = "INVESTORID", nullable = false)
	public User getInvestor() {
		return investor;
	}

	public void setInvestor(User investor) {
		this.investor = investor;
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

	@Column(name = "AMOUNT", nullable = false)
	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Column(name = "ISACCEPTED", nullable = false)
	public Boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	@PreUpdate
	@PrePersist
	public void beforePersist() {
		if (this.isAccepted == null)
			this.isAccepted = false;
	}
}
