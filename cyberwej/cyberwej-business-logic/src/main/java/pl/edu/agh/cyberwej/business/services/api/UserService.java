/**
 * 
 */
package pl.edu.agh.cyberwej.business.services.api;

import java.util.List;

import org.agh.iosr.cyberwej.data.objects.User;

/**
 * @author pita
 * 
 */
public interface UserService {

	public List<User> getAllUsers();

	public void saveUser(User user);

	public void removeUser(String id);

}
