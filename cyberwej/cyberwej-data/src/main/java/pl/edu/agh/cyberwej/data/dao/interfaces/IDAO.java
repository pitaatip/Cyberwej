package pl.edu.agh.cyberwej.data.dao.interfaces;

public interface IDAO<ENTITY> {

    public boolean save(ENTITY entity);

    public ENTITY getById(int id);
}
