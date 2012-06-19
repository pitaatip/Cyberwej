package pl.edu.agh.cyberwej.data.objects;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "USERID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @Column(name = "MAIL", nullable = false, unique = true)
    private String mail;

    @Column(name = "LOGIN", nullable = false, unique = true)
    private String login;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "user", fetch = FetchType.LAZY)
    private Set<GroupMembership> groupMemberships = new HashSet<GroupMembership>();

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "receiver")
    private Set<Invitation> userInvitations = new HashSet<Invitation>();

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "receiver")
    private Set<Payback> paybacksForUser = new HashSet<Payback>();

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "sender")
    private Set<Payback> paybacksForOthers = new HashSet<Payback>();

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<GroupMembership> getGroupMemberships() {
        return this.groupMemberships;
    }

    public void setGroupMemberships(Set<GroupMembership> groupMemberships) {
        this.groupMemberships = groupMemberships;
    }

    public Set<Invitation> getUserInvitations() {
        return this.userInvitations;
    }

    public void setUserInvitations(Set<Invitation> userInvitations) {
        this.userInvitations = userInvitations;
    }

    public Set<Payback> getPaybacksForUser() {
        return this.paybacksForUser;
    }

    public void setPaybacksForUser(Set<Payback> paybacksForUser) {
        this.paybacksForUser = paybacksForUser;
    }

    public Set<Payback> getPaybacksForOthers() {
        return this.paybacksForOthers;
    }

    public void setPaybacksForOthers(Set<Payback> paybacksForOthers) {
        this.paybacksForOthers = paybacksForOthers;
    }

    public void addGroupMembership(GroupMembership groupMembership) {
        this.groupMemberships.add(groupMembership);
    }

    public void removeGroupMembership(GroupMembership groupMembership) {
        if (this.groupMemberships != null)
            this.groupMemberships.remove(groupMembership);
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return this.birthday;
    }

    /**
     * @param birthday
     *            the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
