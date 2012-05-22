package pl.edu.agh.cyberwej.web.beans.payback;

import java.util.List;

import pl.edu.agh.cyberwej.business.services.api.PaybackService;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

public class PaybackRegistrationBean extends BaseBean {
    private PaybackService paybackService;
    private SessionContextBean sessionContextBean;
    private List<Group> userGroups;
    private List<User> groupMembers;
    private User userWhom;
    private Group group;
    private float amount;
    
    public String registerPayback() {
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
    
    
}
