package pl.edu.agh.cyberwej.data.dao.interfaces;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;

public interface GroupMembershipDAO extends IDAO<GroupMembership> {

    public boolean addGroupMembership(Group group, User user);

    public void removeGroupMembership(GroupMembership groupMembership);

}
