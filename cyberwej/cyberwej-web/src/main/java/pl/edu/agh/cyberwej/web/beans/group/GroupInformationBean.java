package pl.edu.agh.cyberwej.web.beans.group;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import pl.edu.agh.cyberwej.business.services.api.GroupMembershipService;
import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.PaymentService;
import pl.edu.agh.cyberwej.common.objects.service.PaymentInformation;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

/**
 * 
 * @author Krzysztof
 * 
 */
@ManagedBean
@ViewScoped
public class GroupInformationBean extends BaseBean {

    private static final String SELECTEDGROUP = "selectedGroup";

    @ManagedProperty(value = "#{groupService}")
    private GroupService groupService;

    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;

    @ManagedProperty(value = "#{groupMembershipService}")
    private GroupMembershipService groupMembershipService;
    
    @ManagedProperty(value = "#{sessionContextBean}")
    private SessionContextBean sessionContextBean;
    
    private Group group = new Group();

    private List<PaymentInformation> groupPayments = new LinkedList<PaymentInformation>();

    @PostConstruct
    public void init() {
        String idString = super.getParameter(SELECTEDGROUP);
        if (idString != null) {
            int id = Integer.parseInt(idString);
            this.group = this.groupService.getGroupWithMembersAndPayments(id);
            this.groupPayments = this.paymentService
                    .getGroupPayments(this.group);
        }
    }

    /**
     * @return the groupService
     */
    public GroupService getGroupService() {
        return this.groupService;
    }

    /**
     * @param groupService
     *            the groupService to set
     */
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * @return the group
     */
    public Group getGroup() {
        return this.group;
    }

    public List<GroupMembership> getGroupMembers() {
        return new LinkedList<GroupMembership>(this.group.getGroupMembers());
    }
    
    public boolean getGroupContainsLoggedUser() {
        User loggedUser = sessionContextBean.getLoggedUser();
        List<GroupMembership> groupMembers = getGroupMembers();
        for(GroupMembership groupMembership : groupMembers) {
            if(groupMembership.getUser().getId() == loggedUser.getId()) {
                return true;
            }
        }
        return false;
    }
    
    public float getOverdrawForLoggedUser() {
        User loggedUser = sessionContextBean.getLoggedUser();
        List<GroupMembership> groupMembers = getGroupMembers();
        for(GroupMembership groupMembership : groupMembers) {
            if(groupMembership.getUser().getId() == loggedUser.getId()) {
                return groupMembership.getOverdraw();
            }
        }
        return 0.0f;
    }
    
    public String leaveGroup() {
        User loggedUser = sessionContextBean.getLoggedUser();
        if(getOverdrawForLoggedUser() > -0.001) {
            groupMembershipService.removeGroupMember(group, loggedUser);
        }
        return "main";
    }

    /**
     * @return the paymentService
     */
    public PaymentService getPaymentService() {
        return this.paymentService;
    }

    /**
     * @param paymentService
     *            the paymentService to set
     */
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public String userPage() {
        return "user_page";
    }

    /**
     * @return the groupPayments
     */
    public List<PaymentInformation> getGroupPayments() {
        return this.groupPayments;
    }

    public GroupMembershipService getGroupMembershipService() {
        return groupMembershipService;
    }

    public void setGroupMembershipService(GroupMembershipService groupMembershipService) {
        this.groupMembershipService = groupMembershipService;
    }
    
    public void removeUserFromGroup() {
        
    }

    public SessionContextBean getSessionContextBean() {
        return sessionContextBean;
    }

    public void setSessionContextBean(SessionContextBean sessionContextBean) {
        this.sessionContextBean = sessionContextBean;
    }
}
