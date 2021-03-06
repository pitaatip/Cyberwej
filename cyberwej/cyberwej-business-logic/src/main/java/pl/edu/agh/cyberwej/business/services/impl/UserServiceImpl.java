/**
 * 
 */
package pl.edu.agh.cyberwej.business.services.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.common.objects.service.GroupInformation;
import pl.edu.agh.cyberwej.data.dao.interfaces.UserDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author pita
 */
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

    @Override
    public User getUserByLogin(String login) {
        return this.dao.findUserByLogin(login);
    }

    @Override
    public List<User> findUserByCriteria(String login, String name, String surname, String location) {
        return this.dao.findUserByCriteria(login, name, surname, location);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupInformation> getUserGroupsInformation(User user) {
        user = this.dao.getById(user.getId());
        List<GroupInformation> groupInformations = new LinkedList<GroupInformation>();
        for (GroupMembership groupMembership : user.getGroupMemberships()) {
            GroupInformation groupInformation = new GroupInformation();
            Group group = groupMembership.getGroup();
            groupInformation.setGroup(group);
            groupInformation.setMembersCount(group.getGroupMembers().size());
            groupInformation.setPaymentsCount(group.getPayments().size());
            groupInformations.add(groupInformation);
        }

        return groupInformations;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupMembership> getGroupMemberships(User user) {
        User retrievedUser = this.dao.getById(user.getId());
        return new LinkedList<GroupMembership>(retrievedUser.getGroupMemberships());
    }
}
