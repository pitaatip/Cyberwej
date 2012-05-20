/**
 * 
 */
package pl.edu.agh.cyberwej.business.services.api;

import java.util.List;

import pl.edu.agh.cyberwej.common.objects.service.GroupInformation;
import pl.edu.agh.cyberwej.data.dao.interfaces.UserDAO;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author pita
 * 
 */
public interface UserService {

    /**
     * Get all users from database
     * 
     * @return list of users
     */
    public List<User> getAllUsers();

    /**
     * Saves user to database
     * 
     * @param user
     */
    public void saveUser(User user);

    /**
     * Remove user from database
     * 
     * @param id
     */
    public void removeUser(String id);

    public User getUserById(int id);

    public List<GroupInformation> getUserGroupsInformation(User user);

    public List<GroupMembership> getGroupMemberships(User user);

    public void setDao(UserDAO dao);

    public User getUserByLogin(String login);

    public List<User> findUserByCriteria(String login, String name,
            String surname, String location);
}
