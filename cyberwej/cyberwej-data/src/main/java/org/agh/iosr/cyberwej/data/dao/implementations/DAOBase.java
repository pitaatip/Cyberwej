package org.agh.iosr.cyberwej.data.dao.implementations;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

public abstract class DAOBase {

    protected HibernateTemplate hibernateTemplate;

    private static Logger logger = Logger.getLogger(DAOBase.class);

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    protected boolean save(Object object) {
        try {
            this.hibernateTemplate.saveOrUpdate(object);
            return true;
        } catch (Exception e) {
            logger.warn("Object not saved " + object.getClass().getName());
            return false;
        }
    }
}
