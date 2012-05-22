package pl.edu.agh.cyberwej.business.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.agh.cyberwej.business.services.api.GroupMembershipService;
import pl.edu.agh.cyberwej.data.dao.interfaces.GroupMembershipDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */
@Service
public class GroupMembershipServiceImpl implements GroupMembershipService {

    @Autowired
    private GroupMembershipDAO groupMembershipDAO;

    @Override
    public boolean addGroupMember(Group group, User user) {
        return this.groupMembershipDAO.addGroupMembership(group, user);
    }

    @Override
    public void updateGroupMembershipStatus(GroupMembership groupMembership,
            float amount) {
        groupMembership.setOverdraw(groupMembership.getOverdraw() + amount);
        this.groupMembershipDAO.save(groupMembership);
    }
    
    /**
     * @param groupMembershipDAO
     *            the groupMembershipDAO to set
     */
    public void setGroupMembershipDAO(GroupMembershipDAO groupMembershipDAO) {
        this.groupMembershipDAO = groupMembershipDAO;
    }

}
