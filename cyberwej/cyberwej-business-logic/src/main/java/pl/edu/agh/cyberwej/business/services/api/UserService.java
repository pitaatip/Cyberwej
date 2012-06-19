package pl.edu.agh.cyberwej.business.services.api;

import java.util.List;

import pl.edu.agh.cyberwej.common.objects.service.GroupInformation;
import pl.edu.agh.cyberwej.data.dao.interfaces.UserDAO;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author pita
 */
public interface UserService {

    /**
     * Get all users from database.
     * @return list of users
     */
    List<User> getAllUsers();

    /**
     * Saves user to database.
     * @param user
     */
    void saveUser(User user);

    /**
     * Remove user from database.
     * @param id
     */
    void removeUser(String id);

    User getUserById(int id);

    List<GroupInformation> getUserGroupsInformation(User user);

    List<GroupMembership> getGroupMemberships(User user);

    void setDao(UserDAO dao);

    User getUserByLogin(String login);

    List<User> findUserByCriteria(String login, String name, String surname, String location);
}
