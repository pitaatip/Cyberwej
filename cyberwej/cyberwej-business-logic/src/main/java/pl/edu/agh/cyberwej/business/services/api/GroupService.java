package pl.edu.agh.cyberwej.business.services.api;

import java.util.Collection;

import pl.edu.agh.cyberwej.data.dao.interfaces.GroupDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */
public interface GroupService {

    public boolean saveGroup(Group group);
    
    public boolean saveGroupWithItsMembers(Group group, Collection<User> members, User owner);
    
    public boolean saveGroupWithItsMembersIds(Group group, Collection<Integer> membersIds, User owner);

    public Group getGroupById(int id);

    public Group getGroupWithMembersAndPayments(int id);
    
    public void setUserService(UserService service);

    public void setGroupDAO(GroupDAO groupDAO);
    
    public void setGroupMembershipService(GroupMembershipService service);
}
