/**
 * 
 */
package pl.edu.agh.cyberwej.business.services.api;

import java.util.List;
import java.util.Map;

import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.User;

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

    /**
     * Returns last payments of the user(payments which user has participated
     * in) with value of spent money decreased by consumed goods cost
     * 
     * @param count
     *            max number of returned payments
     * @param user
     * 
     * @return map representing payment and user status in this payment
     */
    public Map<Payment, Float> getLastPayments(int count, User user);
}
