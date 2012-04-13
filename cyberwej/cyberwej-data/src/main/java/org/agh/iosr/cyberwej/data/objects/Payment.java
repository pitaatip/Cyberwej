package org.agh.iosr.cyberwej.data.objects;

import java.util.Date;
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
@Table(name = "PAYMENTS")
public class Payment {

	private int id;

	private Set<PaymentItem> paymentItems;

	private Date date;

	private String description;

	private Set<PaymentParticipation> participations;

	@Id
	@Column(name = "PAYMENTID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "PAYMENTID")
	public Set<PaymentItem> getPaymentItems() {
		return paymentItems;
	}

	public void setPaymentItems(Set<PaymentItem> paymentItems) {
		this.paymentItems = paymentItems;
	}

	@Column(name = "DATE", columnDefinition = "TIMESTAMP DEFAULT NOW()")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "PAYMENTID")
	public Set<PaymentParticipation> getParticipations() {
		return participations;
	}

	public void setParticipations(Set<PaymentParticipation> participations) {
		this.participations = participations;
	}

}
