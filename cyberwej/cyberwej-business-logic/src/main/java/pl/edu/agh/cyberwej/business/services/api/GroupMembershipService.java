package pl.edu.agh.cyberwej.business.services.api;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author Krzysztof
 */
public interface GroupMembershipService {

    boolean addGroupMember(Group group, User user);

    void updateGroupMembershipStatus(GroupMembership groupMembership, float amount);

    void removeGroupMember(Group group, User user);
    // public void updateGroupMembershipStatus(Group group, User member, float
    // amout);

}
