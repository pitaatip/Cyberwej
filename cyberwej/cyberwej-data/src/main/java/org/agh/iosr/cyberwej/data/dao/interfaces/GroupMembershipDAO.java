package org.agh.iosr.cyberwej.data.dao.interfaces;

import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.GroupMembership;
import org.agh.iosr.cyberwej.data.objects.User;

public interface GroupMembershipDAO extends IDAO<GroupMembership> {

    public boolean addGroupMembership(Group group, User user);

    public void removeGroupMembership(GroupMembership groupMembership);

}
