package pl.edu.agh.cyberwej.web.beans.views.user;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.business.services.api.PaymentService;
import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

/**
 * 
 * @author Krzysztof
 * 
 */
@ManagedBean
@RequestScoped
public class LoggedUserBean {

    private static final String SELECTEDGROUP = "selectedGroup";

    @ManagedProperty(value = "#{service}")
    private UserService userService;

    @ManagedProperty("#{sessionContextBean}")
    private SessionContextBean sessionContextBean;

    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;

    private User user;

    private Map<Payment, Float> latestPaymentsMap;

    private final int count = 3;

    public SessionContextBean getSessionContextBean() {
        return this.sessionContextBean;
    }

    public void setSessionContextBean(SessionContextBean sessionContextBean) {
        this.sessionContextBean = sessionContextBean;
    }

    @PostConstruct
    public void init() {
        this.user = this.sessionContextBean.getLoggedUser();
        this.latestPaymentsMap = this.paymentService.getLastPayments(
                this.count, this.user);
    }

    /**
     * @return the userService
     */
    public UserService getUserService() {
        return this.userService;
    }

    /**
     * @param userService
     *            the userService to set
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return this.user;
    }

    public List<GroupMembership> getGroupMemberships() {
        return this.userService.getGroupMemberships(this.user);
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

    public List<Payment> getPayments() {
        return new LinkedList<Payment>(this.latestPaymentsMap.keySet());
    }

    /**
     * @return the latestPaymentsMap
     */
    public Map<Payment, Float> getLatestPaymentsMap() {
        return this.latestPaymentsMap;
    }
}
