package pl.edu.agh.cyberwej.web.beans.group;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.PaymentService;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;

/**
 * 
 * @author Krzysztof
 * 
 */
@ManagedBean
@RequestScoped
public class GroupInformationBean extends BaseBean {

    private static final String SELECTEDGROUP = "selectedGroup";

    @ManagedProperty(value = "#{groupService}")
    private GroupService groupService;

    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;

    private Group group;

    private Map<Payment, Float> groupPayments;

    @PostConstruct
    public void init() {
        int id = Integer.parseInt(super.getParameter(SELECTEDGROUP));
        this.group = this.groupService.getGroupWithMembersAndPayments(id);
        this.groupPayments = this.paymentService.getGroupPayments(this.group);
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

    /**
     * @return the groupPayments
     */
    public Map<Payment, Float> getGroupPayments() {
        return this.groupPayments;
    }

    public List<Payment> getGroupPaymentsList() {
        return new LinkedList<Payment>(this.groupPayments.keySet());
    }
}
