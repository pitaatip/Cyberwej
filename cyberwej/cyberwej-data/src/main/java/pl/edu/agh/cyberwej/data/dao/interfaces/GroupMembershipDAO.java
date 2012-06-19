package pl.edu.agh.cyberwej.data.dao.interfaces;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;

public interface GroupMembershipDAO extends IDAO<GroupMembership> {

    boolean addGroupMembership(Group group, User user);

    void removeGroupMembership(GroupMembership groupMembership);

    void removeGroupMembership(Group group, User user);

}
