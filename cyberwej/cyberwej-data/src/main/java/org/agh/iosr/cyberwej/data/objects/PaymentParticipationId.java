package org.agh.iosr.cyberwej.data.objects;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PaymentParticipationId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;
	
	private Payment payment;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
}
