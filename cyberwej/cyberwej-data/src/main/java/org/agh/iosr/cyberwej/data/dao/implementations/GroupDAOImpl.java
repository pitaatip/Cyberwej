package org.agh.iosr.cyberwej.data.dao.implementations;

import java.util.List;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDAOImpl implements GroupDAO {

	private HibernateTemplate hibernateTemplate;

	private static Logger logger = Logger.getLogger(GroupDAO.class);

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void saveGroup(Group group) {
		try {
			this.hibernateTemplate.saveOrUpdate(group);
		} catch (Exception e) {
			logger.info("Object not saved", e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Group getGroupByName(String name) {
		List<Group> groups = this.hibernateTemplate.find(
				"from Group group where group.name=?", name);
		if (groups.size() > 0)
			return groups.get(0);
		return null;
	}

}
