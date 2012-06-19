package pl.edu.agh.cyberwej.business.services.api;

import java.util.Collection;

import pl.edu.agh.cyberwej.data.dao.interfaces.GroupDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author Krzysztof
 */
public interface GroupService {

    boolean saveGroup(Group group);

    boolean saveGroupWithItsMembers(Group group, Collection<User> members, User owner);

    boolean saveGroupWithItsMembersIds(Group group, Collection<Integer> membersIds,
            int ownerId);

    Group getGroupById(int id);

    Group getGroupWithMembersAndPayments(int id);

    void setUserService(UserService service);

    void setGroupDAO(GroupDAO groupDAO);

    void setGroupMembershipService(GroupMembershipService service);
}
