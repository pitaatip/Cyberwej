package pl.edu.agh.cyberwej.data.objects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Message {

    protected Integer id;

    protected Group group;

    protected User sender;

    protected User receiver;

    private Date sentTime;

    private Boolean isAccepted;

    @Id
    @Column(name = "MESSAGEID")
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "GROUPID", nullable = false)
    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @ManyToOne
    @JoinColumn(name = "SENDERID", nullable = false)
    public User getSender() {
        return this.sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @ManyToOne
    @JoinColumn(name = "RECEIVERID", nullable = false)
    public User getReceiver() {
        return this.receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Date getSentTime() {
        return sentTime;
    }

    @Column(name = "SENTTIME", nullable = false)
    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
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
