/**
 * 
 */
package pl.edu.agh.cyberwej.web.beans.payment;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

/**
 * @author Pita
 */
@ManagedBean
@RequestScoped
public class RegisterPaymentBean extends BaseBean {
    private String paymentName;
    private String selectedGroup;

    @PostConstruct
    public void cleanMap() {
        getMap4Stuff().put("SelectedGroup", null);
        getMap4Stuff().put("PaymentName", null);
        getMap4Stuff().put("ItemsList", null);
        getMap4Stuff().put("paymentParticipators", null);
    }

    @ManagedProperty("#{sessionContextBean}")
    private SessionContextBean sessionContextBean;

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    @ManagedProperty(value = "#{groupService}")
    private GroupService groupService;

    public List<GroupMembership> getGroupMemberships() {
        return getUserService().getGroupMemberships(getUser());
    }

    public String next() {
        Integer groupId = Integer.parseInt(getSelectedGroup());
        Group group = this.groupService.getGroupById(groupId);
        getMap4Stuff().put("SelectedGroup", group);
        getMap4Stuff().put("PaymentName", getPaymentName());
        getMap4Stuff().put("ActionType", ActionType.STEP);
        return "registerPayment_secondStep";

    }

    public SessionContextBean getSessionContextBean() {
        return this.sessionContextBean;
    }

    public void setSessionContextBean(SessionContextBean sessionContextBean) {
        this.sessionContextBean = sessionContextBean;
    }

    public User getUser() {
        return getSessionContextBean().getLoggedUser();
    }

    public String getSelectedGroup() {
        return this.selectedGroup;
    }

    public void setSelectedGroup(String selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    // List of user group must be kept in session map. Otherwise during submit
    // it changes and select doesnt work?!?
    public List<Group> getUserGroup() {
        List<GroupMembership> groupMemberships = getGroupMemberships();
        List<Group> groupList = new LinkedList<Group>();
        for (GroupMembership groupMembership : groupMemberships) {
            groupList.add(groupMembership.getGroup());
        }
        return groupList;
    }

    public UserService getUserService() {
        return this.userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getPaymentName() {
        return this.paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public GroupService getGroupService() {
        return this.groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

}
