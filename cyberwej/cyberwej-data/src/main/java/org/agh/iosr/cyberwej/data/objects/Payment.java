package org.agh.iosr.cyberwej.data.objects;

import java.util.Date;
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
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "PAYMENTS")
public class Payment {

    private Integer id;

    private Set<PaymentItem> paymentItems = new HashSet<PaymentItem>();

    private Date date;

    private String description;

    private Set<PaymentParticipation> participations = new HashSet<PaymentParticipation>();

    @Id
    @Column(name = "PAYMENTID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Column(name = "DATE")
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

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "payment")
    public Set<PaymentParticipation> getParticipations() {
        return participations;
    }

    public void setParticipations(Set<PaymentParticipation> participations) {
        this.participations = participations;
    }

    @PrePersist
    public void beforePersist() {
        if (this.date == null)
            this.date = new Date();
    }
}
