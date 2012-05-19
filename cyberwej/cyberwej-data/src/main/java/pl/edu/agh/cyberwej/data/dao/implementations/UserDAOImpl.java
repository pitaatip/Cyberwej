package pl.edu.agh.cyberwej.data.dao.implementations;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import org.springframework.stereotype.Repository;

import pl.edu.agh.cyberwej.data.dao.interfaces.UserDAO;
import pl.edu.agh.cyberwej.data.objects.User;

@Repository
public class UserDAOImpl extends DAOBase<User> implements UserDAO {

    @Override
    public boolean saveUser(User user) {
        return super.save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findUserByMail(String mail) {
        List<User> queryResult = this.hibernateTemplate.find(
                "from User user where user.mail=?", mail);
        if (queryResult.size() > 0) {
            return queryResult.get(0);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        List<User> users = this.hibernateTemplate.find("from User user");
        return users;
    }

    @Override
    public void removeUser(User user) {
        super.hibernateTemplate.delete(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findUserById(int id) {
        List<User> queryResult = this.hibernateTemplate.find(
                "from User user where user.id=?", id);
        if (queryResult.size() > 0) {
            return queryResult.get(0);
        }
        return null;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public User findUserByLogin(String login) {
        List<User> queryResult = this.hibernateTemplate.find(
                "from User user where user.login=?", login);
        if (queryResult.size() > 0) {
            return queryResult.get(0);
        }
        return null;
    }
    
    private String toLikeParameter(String parameter) {
        if(hasText(parameter)) {
            StringBuilder sb = new StringBuilder("%");
            sb.append(parameter);
            sb.append("%");
            return sb.toString();
        }
        return "%";
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<User> findUserByCriteria(String login, String name, String surname, String location) {
        String loginLike = toLikeParameter(login);
        String nameLike = toLikeParameter(name);
        String surnameLike = toLikeParameter(surname);
        String locationLike = toLikeParameter(location);
        
        List<User> queryResult = this.hibernateTemplate.find(
        "from User user where user.login like ? and user.name like ? and user.surname like ? and user.location like ?",
        loginLike, nameLike, surnameLike, locationLike);
        return queryResult;
    }
}
