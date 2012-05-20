package pl.edu.agh.cyberwej.business.services.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.business.services.api.GroupMembershipService;
import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.data.dao.interfaces.GroupDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */
@Service(value = "groupService")
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDAO groupDAO;
    
    @Autowired
    private GroupMembershipService groupMembershipService;

    @Override
    public boolean saveGroup(Group group) {
        return this.groupDAO.saveGroup(group);
    }

    @Override
    @Transactional
    public boolean saveGroupWithItsMembers(Group group, List<User> members) {
        if(groupDAO.getGroupByName(group.getName()) != null) {
            return false;
        }
        if(!groupDAO.saveGroup(group)) {
            return false;
        }
        for(User user : members) {
            groupMembershipService.addGroupMember(group, user);
        }
        return true;
    }
    
    @Override
    public void setGroupMembershipService(GroupMembershipService service) {
        this.groupMembershipService = service;
    }
    
    /**
     * @param groupDAO
     *            the groupDAO to set
     */
    @Override
    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Group getGroupById(int id) {
        return this.groupDAO.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Group getGroupWithMembersAndPayments(int id) {
        Group group = getGroupById(id);
        group.setGroupMembers(new HashSet<GroupMembership>(group
                .getGroupMembers()));
        group.setPayments(new HashSet<Payment>(group.getPayments()));
        return group;
    }
}
