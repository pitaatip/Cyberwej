package pl.edu.agh.cyberwej.data.objects;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pl.edu.agh.cyberwej.data.objects.id.GroupMembershipID;

@Entity
@IdClass(GroupMembershipID.class)
@Table(name = "GROUPMEMBERSHIPS")
public class GroupMembership implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 470204746629470622L;

    @Column(name = "JOINDATE", nullable = false)
    private Date joinDate;

    @Column(name = "OVERDRAW", nullable = false)
    private float overdraw;

    @Id
    @ManyToOne
    @JoinColumn(name = "GROUPID", nullable = false)
    private Group group;

    @Id
    @JoinColumn(name = "USERID", nullable = false)
    @ManyToOne
    private User user;

    public float getOverdraw() {
        return this.overdraw;
    }

    public void setOverdraw(float overdraw) {
        this.overdraw = overdraw;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the joinDate
     */
    public Date getJoinDate() {
        return this.joinDate;
    }

    /**
     * @param joinDate
     *            the joinDate to set
     */
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
}
