/**
 * 
 */
package pl.edu.agh.cyberwej.business.services.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.dao.interfaces.UserDAO;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;

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
        return this.dao.getAllUsers();
    }

    @Override
    public void saveUser(User user) {
        this.dao.saveUser(user);
    }

    @Override
    public void removeUser(String userToDeleteId) {
        Integer id = Integer.parseInt(userToDeleteId);
        User delUser = this.dao.findUserById(id);
        this.dao.removeUser(delUser);

    }

    public UserDAO getDao() {
        return this.dao;
    }

    @Override
    public void setDao(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public User getUserById(int id) {
        return this.dao.getById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupMembership> getUserGroupMemberships(User user) {
        user = this.dao.getById(user.getId());
        return new LinkedList<GroupMembership>(user.getGroupMemberships());
    }
    
    @Transactional(readOnly = true)
    @Override
    public User getUserByLogin(String login) {
        User user = this.dao.findUserByLogin(login);
        return user;
    }
    
    

}
