package pl.edu.agh.cyberwej.business.services.api;

import pl.edu.agh.cyberwej.data.dao.interfaces.GroupDAO;
import pl.edu.agh.cyberwej.data.objects.Group;

/**
 * 
 * @author Krzysztof
 * 
 */
public interface GroupService {

    public boolean saveGroup(Group group);

    public Group getGroupById(int id);

    public Group getGroupWithMembers(int id);

    public void setGroupDAO(GroupDAO groupDAO);
}
