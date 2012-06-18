package pl.edu.agh.cyberwej.data.dao.implementations;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import pl.edu.agh.cyberwej.data.dao.interfaces.GroupMembershipDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;

@Repository
public class GroupMembershipDAOImpl extends DAOBase<GroupMembership> implements GroupMembershipDAO {

    @Override
    public boolean addGroupMembership(Group group, User user) {
        GroupMembership groupMembership = new GroupMembership();
        groupMembership.setGroup(group);
        groupMembership.setUser(user);
        groupMembership.setOverdraw(0);
        user.addGroupMembership(groupMembership);
        group.getGroupMembers().add(groupMembership);
        groupMembership.setJoinDate(new Date());
        return super.save(groupMembership);
    }

    @Override
    public void removeGroupMembership(GroupMembership groupMembership) {
        if(Hibernate.isInitialized(groupMembership.getUser().getGroupMemberships())) {
            groupMembership.getUser().removeGroupMembership(groupMembership);
        }
        if(Hibernate.isInitialized(groupMembership.getGroup().getGroupMembers())) {
            groupMembership.getGroup().getGroupMembers().remove(groupMembership);
        }
        super.hibernateTemplate.delete(groupMembership);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void removeGroupMembership(Group group, User user) {
        List<GroupMembership> groupMemberships = super.hibernateTemplate.find(
                "select gm from GroupMembership gm where gm.group.id = ? and gm.user.id = ?",
                group.getId(), user.getId());
        if (groupMemberships != null && groupMemberships.size() == 1) {
            removeGroupMembership(groupMemberships.get(0));
        }
    }

}
