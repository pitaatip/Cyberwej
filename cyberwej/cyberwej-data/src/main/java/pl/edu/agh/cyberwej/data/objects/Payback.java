package pl.edu.agh.cyberwej.data.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "PAYBACKS")
public class Payback extends Message {

    private float amount;

    private Boolean isAccepted;

    @Column(name = "AMOUNT", nullable = false)
    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Column(name = "ISACCEPTED", nullable = false)
    public Boolean isAccepted() {
        return this.isAccepted;
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
