package pl.edu.agh.cyberwej.data.dao.interfaces;

import java.util.List;

import pl.edu.agh.cyberwej.data.objects.User;

public interface UserDAO extends IDAO<User> {
    boolean saveUser(User user);

    User findUserByMail(String mail);

    User findUserById(int id);

    User findUserByLogin(String login);

    void removeUser(User user);

    List<User> getAllUsers();

    List<User> findUserByCriteria(String login, String name, String surname, String location);
}
