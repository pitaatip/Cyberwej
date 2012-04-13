package org.agh.iosr.cyberwej.data.dao.implementations;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupMembershipDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.GroupMembership;
import org.agh.iosr.cyberwej.data.objects.User;
import org.springframework.stereotype.Repository;

@Repository
public class GroupMembershipDAOImpl extends DAOBase implements
		GroupMembershipDAO {

	@Override
	public boolean addGroupMembership(Group group, User user) {
		GroupMembership groupMembership = new GroupMembership();
		groupMembership.setGroup(group);
		groupMembership.setUser(user);
		groupMembership.setOverdraw(0);
		return super.save(groupMembership);
	}

	@Override
	public void removeGroupMembership(GroupMembership groupMembership) {
		super.hibernateTemplate.delete(groupMembership);
	}

}
