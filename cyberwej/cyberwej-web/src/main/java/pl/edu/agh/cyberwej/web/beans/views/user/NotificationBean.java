package pl.edu.agh.cyberwej.web.beans.views.user;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import pl.edu.agh.cyberwej.business.services.api.InvitationService;
import pl.edu.agh.cyberwej.data.objects.Invitation;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

public class NotificationBean extends BaseBean {
    
    private SessionContextBean sessionContextBean;
    private User loggedUser;
    private List<Invitation> invitations;
    private InvitationService invitationService;

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

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }
    
    public InvitationService getInvitationService() {
        return invitationService;
    }

    public void setInvitationService(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostConstruct
    public void init() {
        this.loggedUser = sessionContextBean.getLoggedUser();
        this.invitations = invitationService.getInviationsForUser(loggedUser, true);
    }
    
    public String acceptInvitation() {
        String parameter = getParameter("invitationId");
        this.invitationService.acceptInvitationById(Integer.parseInt(parameter), true);
        return "main";
    }
}
