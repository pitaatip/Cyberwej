package org.agh.iosr.cyberwej.data.dao.implementations;

import java.util.List;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDAOImpl extends DAOBase implements GroupDAO {

    @Override
    public boolean saveGroup(Group group) {
        return super.save(group);
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

    @Override
    public void removeGroup(Group group) {
        super.hibernateTemplate.delete(group);
    }
}
