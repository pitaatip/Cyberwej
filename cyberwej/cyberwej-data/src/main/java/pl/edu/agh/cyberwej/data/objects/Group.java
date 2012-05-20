package pl.edu.agh.cyberwej.data.objects;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GROUPS")
public class Group {

    private Integer id;

    private String name;

    private Date creationDate;

    private Set<GroupMembership> groupMembers = new HashSet<GroupMembership>();

    private Set<Invitation> invitations = new HashSet<Invitation>();

    private Set<Payment> payments = new HashSet<Payment>();

    private Set<Payback> paybacks = new HashSet<Payback>();

    @Id
    @Column(name = "GROUPID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "NAME", unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "group")
    public Set<GroupMembership> getGroupMembers() {
        return this.groupMembers;
    }

    public void setGroupMembers(Set<GroupMembership> groupMembers) {
        this.groupMembers = groupMembers;
    }

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "group")
    public Set<Invitation> getInvitations() {
        return this.invitations;
    }

    public void setInvitations(Set<Invitation> invitations) {
        this.invitations = invitations;
    }

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "group")
    public Set<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "group")
    public Set<Payback> getPaybacks() {
        return this.paybacks;
    }

    public void setPaybacks(Set<Payback> paybacks) {
        this.paybacks = paybacks;
    }

    /**
     * @return the creationDate
     */
    @Column(name = "CREATIONDATE", nullable = false)
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * @param creationDate
     *            the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
