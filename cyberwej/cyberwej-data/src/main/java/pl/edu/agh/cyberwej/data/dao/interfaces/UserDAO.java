package pl.edu.agh.cyberwej.data.dao.interfaces;

import java.util.List;

import pl.edu.agh.cyberwej.data.objects.User;

public interface UserDAO extends IDAO<User> {
    public boolean saveUser(User user);

    public User findUserByMail(String mail);
    
    public User findUserByLogin(String login);

    public User findUserById(int id);

    public User findUserByLogin(String login);

    public void removeUser(User user);

    public List<User> getAllUsers();
}
