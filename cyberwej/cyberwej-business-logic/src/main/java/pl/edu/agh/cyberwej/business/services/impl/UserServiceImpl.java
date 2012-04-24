/**
 * 
 */
package pl.edu.agh.cyberwej.business.services.impl;

import java.util.List;

import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
import org.agh.iosr.cyberwej.data.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.agh.cyberwej.business.services.api.UserService;

/**
 * @author pita
 * 
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO dao;

    @Override
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    @Override
    public void saveUser(User user) {
        dao.saveUser(user);
    }

    @Override
    public void removeUser(String userToDeleteId) {
        Integer id = Integer.parseInt(userToDeleteId);
        User delUser = dao.findUserById(id);
        dao.removeUser(delUser);

    }

    public UserDAO getDao() {
        return dao;
    }

    public void setDao(UserDAO dao) {
        this.dao = dao;
    }

}
