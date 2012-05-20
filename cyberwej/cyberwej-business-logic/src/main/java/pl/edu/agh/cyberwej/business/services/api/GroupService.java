package pl.edu.agh.cyberwej.business.services.api;

import java.util.List;

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
    
    public boolean saveGroupWithItsMembers(Group group, List<User> members);

    public Group getGroupById(int id);

    public Group getGroupWithMembersAndPayments(int id);

    public void setGroupDAO(GroupDAO groupDAO);
    
    public void setGroupMembershipService(GroupMembershipService service);
}
