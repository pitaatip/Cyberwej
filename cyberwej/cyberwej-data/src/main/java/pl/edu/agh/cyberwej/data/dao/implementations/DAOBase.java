package pl.edu.agh.cyberwej.data.dao.implementations;

import java.lang.reflect.ParameterizedType;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import pl.edu.agh.cyberwej.data.dao.interfaces.IDAO;

public abstract class DAOBase<ENTITY> implements IDAO<ENTITY> {

    protected HibernateTemplate hibernateTemplate;

    private static Logger logger = Logger.getLogger(DAOBase.class);

    private final Class<ENTITY> entityType;

    @SuppressWarnings("unchecked")
    public DAOBase() {
        this.entityType = ((Class<ENTITY>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);

    }

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

    /*
     * (non-Javadoc)
     * 
     * @see pl.edu.agh.cyberwej.data.dao.interfaces.IDAO#getById(int)
     */
    @Override
    public ENTITY getById(int id) {
        return this.hibernateTemplate.get(this.entityType, id);
    }
}
