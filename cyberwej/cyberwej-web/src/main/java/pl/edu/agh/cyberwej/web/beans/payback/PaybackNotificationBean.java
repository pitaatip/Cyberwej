package pl.edu.agh.cyberwej.web.beans.payback;

import java.util.List;

import javax.annotation.PostConstruct;

import pl.edu.agh.cyberwej.business.services.api.InvitationService;
import pl.edu.agh.cyberwej.business.services.api.PaybackService;
import pl.edu.agh.cyberwej.data.objects.Invitation;
import pl.edu.agh.cyberwej.data.objects.Payback;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

public class PaybackNotificationBean extends BaseBean {
    private SessionContextBean sessionContextBean;
    private User loggedUser;
    private List<Payback> paybacks;
    private PaybackService paybackService;

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

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public List<Payback> getPaybacks() {
        return paybacks;
    }

    public void setPaybacks(List<Payback> paybacks) {
        this.paybacks = paybacks;
    }
    
    @PostConstruct
    public void init() {
        this.loggedUser = sessionContextBean.getLoggedUser();
        this.paybacks = paybackService.getPaybacksForUser(loggedUser, true);
    }
    
    public String acceptPayback() {
        String parameter = getParameter("paybackId");
        this.paybackService.acceptPaybackById(Integer.parseInt(parameter), true);
        return "main";
    }
}
