/**
 * 
 */
package pl.edu.agh.cyberwej.web.beans.payment;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.PaymentParticipation;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

/**
 * @author Pita
 * 
 */
@ManagedBean
@RequestScoped
public class RegisterPaymentStep3Bean extends BaseBean {

    private String amount;
    private String selectedPayer;

    @ManagedProperty(value = "#{service}")
    private UserService userService;

    @ManagedProperty("#{sessionContextBean}")
    private SessionContextBean sessionContextBean;

    @ManagedProperty(value = "#{groupService}")
    private GroupService groupService;

    public List<GroupMembership> getGroupMemberships() {
        Group groupWithMembers = getGroupService().getGroupWithMembers(getGroup().getId());
        return new LinkedList<GroupMembership>(groupWithMembers.getGroupMembers());
    }

    public String next() {
        return "registerPaymentSummary";
    }

    private Group getGroup() {
        return (Group) getMap4Stuff().get("SelectedGroup");
    }

    public User getUser() {
        return getSessionContextBean().getLoggedUser();
    }

    public String addPayer() {
        PaymentParticipation paymentParticipation = new PaymentParticipation();
        paymentParticipation.setAmount(Float.parseFloat(amount));
        User userById = userService.getUserById(Integer.parseInt(selectedPayer));
        paymentParticipation.setUser(userById);
        getPaymentParticipators().add(paymentParticipation);
        return null;
    }

    public String removePayer() {
        int itemId = Integer.parseInt(getParameter("itemToDelete"));
        getPaymentParticipators().remove(itemId);
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<PaymentParticipation> getPaymentParticipators() {
        if (getMap4Stuff().get("paymentParticipators") == null) {
            getMap4Stuff().put("paymentParticipators", new LinkedList<PaymentParticipation>());
        }
        return (List<PaymentParticipation>) getMap4Stuff().get("paymentParticipators");
    }

    //GET AND SETS
    
    public String getSelectedPayer() {
        return selectedPayer;
    }

    public void setSelectedPayer(String selectedPayer) {
        this.selectedPayer = selectedPayer;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public SessionContextBean getSessionContextBean() {
        return sessionContextBean;
    }

    public void setSessionContextBean(SessionContextBean sessionContextBean) {
        this.sessionContextBean = sessionContextBean;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

}
