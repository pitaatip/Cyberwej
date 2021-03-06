package pl.edu.agh.cyberwej.web.beans.payback;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;

import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.PaybackService;
import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

public class PaybackRegistrationBean extends BaseBean {
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
        paybackService.createPayback(loggedUser, userWhom, group, amount);
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

    }

    private void refreshUsers(String groupId) {
        Group group = groupService.getGroupWithMembersAndPayments(Integer.parseInt(groupId));
        List<User> members = new ArrayList<User>();
        for (GroupMembership groupMembership : group.getGroupMembers()) {
            User us = groupMembership.getUser();
            members.add(us);
        }
        setGroupMembers(members);
    }

    public String getGroupId() {
        if (group != null) {
            return group.getId().toString();
        }
        return null;
    }

    public void setGroupId(String groupId) {
        setGroup(groupService.getGroupWithMembersAndPayments(Integer.parseInt(groupId)));
    }

    public void setUserId(String userId) {
        setUserWhom(userService.getUserById(Integer.parseInt(userId)));
    }

    public String getUserId() {
        if (userWhom != null) {
            return userWhom.getId().toString();
        }
        return null;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @PostConstruct
    public void init() {
        this.loggedUser = sessionContextBean.getLoggedUser();
        List<GroupMembership> groupMemberships = userService.getGroupMemberships(loggedUser);
        for (GroupMembership membership : groupMemberships) {
            userGroups.add(membership.getGroup());
        }
        if (getUserGroups().size() == 1) {
            setGroupId(getUserGroups().get(0).getId().toString());
            refreshUsers(getUserGroups().get(0).getId().toString());
        }

    }

    public void valueChanged(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String groupId = (String) event.getNewValue();
            refreshUsers(groupId);
        }
    }
}
