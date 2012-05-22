package pl.edu.agh.cyberwej.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.business.services.api.GroupMembershipService;
import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.PaybackService;
import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.dao.interfaces.PaybackDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.Payback;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */
@Service(value = "paybackService")
public class PaybackServiceImpl implements PaybackService {

    @Autowired
    private PaybackDAO paybackDAO;

    @Autowired
    private GroupMembershipService groupMembershipService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private GroupService groupService;

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public GroupMembershipService getGroupMembershipService() {
        return groupMembershipService;
    }

    public void setGroupMembershipService(GroupMembershipService groupMembershipService) {
        this.groupMembershipService = groupMembershipService;
    }

    @Override
    @Transactional
    public boolean createPayback(User debtor, User investor, Group group, float amount) {
        return this.paybackDAO.addPayback(userService.getUserById(debtor.getId()),
                userService.getUserById(investor.getId()), groupService.getGroupById(group.getId()), amount);
    }

    private GroupMembership getGroupMembershipFromObjects(Group group, User user) {
        for (GroupMembership gm : user.getGroupMemberships()) {
            if (gm.getGroup().getId() == group.getId()) {
                return gm;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public boolean acceptPayback(Payback payback, boolean isAccepted) {
        GroupMembership sender = getGroupMembershipFromObjects(payback.getGroup(),
                payback.getSender());
        GroupMembership receiver = getGroupMembershipFromObjects(payback.getGroup(),
                payback.getReceiver());
        groupMembershipService.updateGroupMembershipStatus(sender, payback.getAmount());
        groupMembershipService.updateGroupMembershipStatus(receiver, payback.getAmount());
        payback.setAccepted(isAccepted);
        return this.paybackDAO.updatePayback(payback);
    }
    
    @Override
    @Transactional
    public boolean acceptPaybackById(int paybackId, boolean isAccepted) {
        return acceptPayback(paybackDAO.getById(paybackId), isAccepted);
    }

    /**
     * @param paybackDAO
     *            the paybackDAO to set
     */
    public void setPaybackDAO(PaybackDAO paybackDAO) {
        this.paybackDAO = paybackDAO;
    }
    
    public List<Payback> getPaybacksForUser(User user, boolean onlyUnaccepted) {
        return paybackDAO.getPaybacksForUser(user, onlyUnaccepted);
    }

}
