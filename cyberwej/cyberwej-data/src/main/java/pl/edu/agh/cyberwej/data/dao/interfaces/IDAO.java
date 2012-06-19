package pl.edu.agh.cyberwej.data.dao.interfaces;

public interface IDAO<ENTITY> {

    boolean save(ENTITY entity);

    ENTITY getById(int id);
}
