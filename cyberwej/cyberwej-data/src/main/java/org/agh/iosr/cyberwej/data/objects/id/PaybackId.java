package org.agh.iosr.cyberwej.data.objects.id;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.User;

@Embeddable
public class PaybackId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1738543827890081132L;

	private Group group;

	private User debtor;

	private User investor;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getDebtor() {
		return debtor;
	}

	public void setDebtor(User debtor) {
		this.debtor = debtor;
	}

	public User getInvestor() {
		return investor;
	}

	public void setInvestor(User investor) {
		this.investor = investor;
	}

}
