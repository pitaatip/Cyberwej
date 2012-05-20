package pl.edu.agh.cyberwej.web.beans.views.user;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.data.objects.Invitation;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

@ManagedBean
@RequestScoped
public class NotificationBean extends BaseBean {
    
    @ManagedProperty("#{sessionContextBean}")
    private SessionContextBean sessionContextBean;
    
    private User loggedUser;
    
    private List<Invitation> invitations;
}
