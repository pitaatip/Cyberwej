package pl.edu.agh.cyberwej.business.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.data.dao.interfaces.GroupDAO;
import pl.edu.agh.cyberwej.data.objects.Group;

/**
 * 
 * @author Krzysztof
 * 
 */
@Service(value = "groupService")
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDAO groupDAO;

    @Override
    public boolean saveGroup(Group group) {
        return this.groupDAO.saveGroup(group);
    }

    /**
     * @param groupDAO
     *            the groupDAO to set
     */
    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

}
