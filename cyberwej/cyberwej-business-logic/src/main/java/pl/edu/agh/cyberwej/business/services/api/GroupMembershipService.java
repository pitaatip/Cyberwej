package pl.edu.agh.cyberwej.business.services.api;

import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */
public interface GroupMembershipService {

    public boolean addGroupMember(Group group, User user);

    public void updateGroupMembershipStatus(GroupMembership groupMembership,
            float amount);

}
