/**
 * 
 */
package pl.edu.agh.cyberwej.web.beans.payment;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.PaymentService;
import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.Payment;
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
public class AddPayersBean extends BaseBean {

    private static Logger logger = Logger.getLogger(AddPayersBean.class);

    private String amount;
    private String selectedPayer;

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    @ManagedProperty("#{sessionContextBean}")
    private SessionContextBean sessionContextBean;

    @ManagedProperty(value = "#{groupService}")
    private GroupService groupService;

    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;

    public List<GroupMembership> getGroupMemberships() {
        Group groupWithMembers = getGroupService()
                .getGroupWithMembersAndPayments(getGroup().getId());
        return new LinkedList<GroupMembership>(
                groupWithMembers.getGroupMembers());
    }

    public String next() {
        if (getMap4Stuff().get("ActionType").equals(ActionType.STEP3))
            return "registerPaymentSummary";
        else {
            Payment payment = (Payment) getMap4Stuff().get("Payment");
            // TODO
            try {
                FacesContext
                        .getCurrentInstance()
                        .getExternalContext()
                        .redirect(
                                "paymentPage.jsf?selectedPayment="
                                        + payment.getId());
                return null;
            } catch (IOException e) {
                logger.warn("Add payers oepration error", e);
                e.printStackTrace();
            }
            return "payment_page_redirect";
        }
    }

    private Group getGroup() {
        return (Group) getMap4Stuff().get("SelectedGroup");
    }

    public User getUser() {
        return getSessionContextBean().getLoggedUser();
    }

    public String addPayer() {
        PaymentParticipation paymentParticipation = new PaymentParticipation();
        paymentParticipation.setAmount(Float.parseFloat(this.amount));
        User userById = this.userService.getUserById(Integer
                .parseInt(this.selectedPayer));
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
            getMap4Stuff().put("paymentParticipators",
                    new LinkedList<PaymentParticipation>());
        }
        return (List<PaymentParticipation>) getMap4Stuff().get(
                "paymentParticipators");
    }

    // GET AND SETS

    public String getSelectedPayer() {
        return this.selectedPayer;
    }

    public void setSelectedPayer(String selectedPayer) {
        this.selectedPayer = selectedPayer;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public UserService getUserService() {
        return this.userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public SessionContextBean getSessionContextBean() {
        return this.sessionContextBean;
    }

    public void setSessionContextBean(SessionContextBean sessionContextBean) {
        this.sessionContextBean = sessionContextBean;
    }

    public GroupService getGroupService() {
        return this.groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public String getDescription() {
        return getMap4Stuff().get("ActionType").toString();
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
}
