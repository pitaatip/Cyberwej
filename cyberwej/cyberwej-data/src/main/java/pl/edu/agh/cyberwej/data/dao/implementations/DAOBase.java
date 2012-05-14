package pl.edu.agh.cyberwej.data.dao.implementations;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import pl.edu.agh.cyberwej.data.dao.interfaces.IDAO;

public abstract class DAOBase<ENTITY> implements IDAO<ENTITY> {

    protected HibernateTemplate hibernateTemplate;

    private static Logger logger = Logger.getLogger(DAOBase.class);

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @Override
    public boolean save(ENTITY entity) {
        try {
            this.hibernateTemplate.saveOrUpdate(entity);
            return true;
        } catch (Exception e) {
            logger.warn("Object not saved " + entity.getClass().getName());
            return false;
        }
    }
}
