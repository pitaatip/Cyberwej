package org.agh.iosr.cyberwej.data.dao;

import java.util.List;

import org.agh.iosr.cyberwej.data.objects.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}


	@Override
	public void saveUser(User user) {
		this.hibernateTemplate.saveOrUpdate(user);
	}

	@Override
	@SuppressWarnings("unchecked")
	public User findUserByMail(String mail) {
		List<User> queryResult = this.hibernateTemplate.find("from User user where user.mail=?", mail);
		if(queryResult.size() > 0)
			return queryResult.get(0);
		return null;
	}
}
