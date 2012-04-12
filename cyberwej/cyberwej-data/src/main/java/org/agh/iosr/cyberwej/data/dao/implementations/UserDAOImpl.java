package org.agh.iosr.cyberwej.data.dao.implementations;

import java.util.List;

import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.GroupMember;
import org.agh.iosr.cyberwej.data.objects.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

	private HibernateTemplate hibernateTemplate;
	private static Logger logger = Logger.getLogger(UserDAO.class);

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void saveUser(User user) {
		try {
			this.hibernateTemplate.saveOrUpdate(user);
		} catch (Exception e) {
			logger.info("Object not saved", e);
		}
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
	public void addGroupMember(User user, Group group) {
		GroupMember groupMember = new GroupMember();
		groupMember.setGroup(group);
		groupMember.setUser(user);
		this.hibernateTemplate.save(groupMember);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<GroupMember> getUserGroups(String login) {
		return this.hibernateTemplate.find(
				"from GroupMember groupMember where groupMember.user.login=?",
				login);
	}
}
