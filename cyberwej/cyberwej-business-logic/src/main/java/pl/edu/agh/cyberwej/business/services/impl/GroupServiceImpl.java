package pl.edu.agh.cyberwej.business.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.business.services.api.GroupMembershipService;
import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.InvitationService;
import pl.edu.agh.cyberwej.business.services.api.UserService;
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
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDAO groupDAO;
    
    @Autowired
    private InvitationService invitationService;
    
    private GroupMembershipService groupMembershipService;
    
    private UserService userService;

    @Override
    public boolean saveGroup(Group group) {
        return this.groupDAO.saveGroup(group);
    }

    @Override
    @Transactional
    public boolean saveGroupWithItsMembers(Group group, Collection<User> members, User owner) {
        if(groupDAO.getGroupByName(group.getName()) != null) {
            return false;
        }
        if(!groupDAO.saveGroup(group)) {
            return false;
        }
        groupMembershipService.addGroupMember(group, owner);
        for(User user : members) {
            if(user.getId() != owner.getId()) {
                invitationService.inviteUser(owner, user, group);
            }
        }
        return true;
    }
    
    @Override
    @Transactional
    public boolean saveGroupWithItsMembersIds(Group group, Collection<Integer> membersIds, User owner) {
        List<User> members = new ArrayList<User>();
        for(int memberId : membersIds) {
            members.add(userService.getUserById(memberId));
            //What if null?
        }
        return saveGroupWithItsMembers(group, members, owner);
    }
    
    @Override
    public void setGroupMembershipService(GroupMembershipService service) {
        this.groupMembershipService = service;
    }
    
    @Override
    public void setUserService(UserService service) {
        this.userService = service;
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
