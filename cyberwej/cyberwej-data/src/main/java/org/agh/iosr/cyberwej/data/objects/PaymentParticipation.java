package org.agh.iosr.cyberwej.data.objects;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.agh.iosr.cyberwej.data.objects.id.PaymentParticipationId;

@Entity
@IdClass(PaymentParticipationId.class)
@Table(name = "PAYMENTPARTICIPATIONS")
public class PaymentParticipation implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2251267927230859363L;

    private Payment payment;

    private User user;

    private float amount;

    @Id
    @ManyToOne
    @JoinColumn(name = "PAYMENTID", nullable = false)
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
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

    @Column(name = "AMOUNT", nullable = false)
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
