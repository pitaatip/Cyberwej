package pl.edu.agh.cyberwej.web.beans.payback;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.PaybackService;
import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

public class PaybackRegistrationBean extends BaseBean {
    private static final Logger LOGGER = Logger.getLogger(PaybackRegistrationBean.class); 
    private PaybackService paybackService;
    private SessionContextBean sessionContextBean;
    private UserService userService;
    private GroupService groupService;

    private List<Group> userGroups = new ArrayList<Group>();
    private List<User> groupMembers = new ArrayList<User>();
    private User loggedUser;
    private User userWhom;
    private Group group;
    private float amount;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public String registerPayback() {
        LOGGER.warn("registerPayback");
        //paybackService.createPayback(loggedUser, userWhom, group, amount);
        return "main";
    }

    public PaybackService getPaybackService() {
        return paybackService;
    }

    public void setPaybackService(PaybackService paybackService) {
        this.paybackService = paybackService;
    }

    public SessionContextBean getSessionContextBean() {
        return sessionContextBean;
    }

    public void setSessionContextBean(SessionContextBean sessionContextBean) {
        this.sessionContextBean = sessionContextBean;
    }

    public List<Group> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<Group> userGroups) {
        this.userGroups = userGroups;
    }

    public List<User> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<User> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public User getUserWhom() {
        return userWhom;
    }

    public void setUserWhom(User userWhom) {
        this.userWhom = userWhom;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
        List<User> members = new ArrayList<User>();
        for (GroupMembership groupMembership : group.getGroupMembers()) {
            User us = groupMembership.getUser();
            members.add(us);
            LOGGER.warn("adding group member " + us.getLogin());
        }
        setGroupMembers(members);
    }

    public String getGroupId() {
        if (group != null) {
            return group.getId().toString();
        } else {
            return "";
        }
    }

    public void setGroupId(String groupId) {
        LOGGER.warn("set groupId" + groupId);
        setGroup(groupService.getGroupWithMembersAndPayments(Integer.parseInt(groupId)));
    }

    public void setUserId(String userId) {
        LOGGER.warn("set userId" + userId);
        setUserWhom(userService.getUserById(Integer.parseInt(userId)));
    }
    
    public String getUserId() {
        return userWhom.getId().toString();
    }
    
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        LOGGER.warn("set amount" + amount);
        this.amount = amount;
    }

    @PostConstruct
    public void init() {
        LOGGER.warn("init");
        this.loggedUser = sessionContextBean.getLoggedUser();
        List<GroupMembership> groupMemberships = userService.getGroupMemberships(loggedUser);
        for (GroupMembership membership : groupMemberships) {
            userGroups.add(membership.getGroup());
        }

    }

}
