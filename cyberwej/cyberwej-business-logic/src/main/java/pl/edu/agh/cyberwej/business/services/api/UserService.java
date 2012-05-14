/**
 * 
 */
package pl.edu.agh.cyberwej.business.services.api;

import java.util.List;

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

}
