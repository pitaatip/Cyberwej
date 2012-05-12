package pl.edu.agh.cyberwej.business.services.api;

import java.util.List;

import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */
public interface GroupService {

    public void saveGroup(Group group, User founder, List<User> invitees);
}
