package org.agh.iosr.cyberwej.data.dao.implementations;

import java.util.List;

import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
import org.agh.iosr.cyberwej.data.objects.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl extends DAOBase implements UserDAO {

	private static Logger logger = Logger.getLogger(UserDAO.class);

	@Override
	public boolean saveUser(User user) {
		return super.save(user);
	}

	@Override
	@SuppressWarnings("unchecked")
	public User findUserByMail(String mail) {
		List<User> queryResult = this.hibernateTemplate.find(
				"from User user where user.mail=?", mail);
		if (queryResult.size() > 0)
			return queryResult.get(0);
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers()
	{
		List<User> users = this.hibernateTemplate.find("from User user");
		return users;
	}
	
	@Override
	public void removeUser(User user) {
		super.hibernateTemplate.delete(user);
	}
}
