package pl.edu.agh.cyberwej.business.services.api;

import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.GroupMembership;
import org.agh.iosr.cyberwej.data.objects.User;

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
