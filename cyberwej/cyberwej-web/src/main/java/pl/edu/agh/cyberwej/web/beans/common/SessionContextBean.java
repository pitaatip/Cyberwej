package pl.edu.agh.cyberwej.web.beans.common;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author Pita This is session scoped bean, which is used to store data
 *         specific for each session. It also contains map4Stuff which is used
 *         to pass data object between pages.
 */

@ManagedBean(name = "sessionContextBean")
@SessionScoped
public class SessionContextBean {
    private User loggedUser;
    private Map<String, Object> map4Stuff = new HashMap<String, Object>();

    public void isLogged(ComponentSystemEvent event) {
        if (getLoggedUser() == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication()
                    .getNavigationHandler();

            nav.performNavigation("logging");
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Map<String, Object> getMap4Stuff() {
        return map4Stuff;
    }
}
