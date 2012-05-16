package pl.edu.agh.cyberwej.web.beans.common;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

/**
 * @author Pita
 */
@ManagedBean
@RequestScoped
public class TemplateBean {
    private static final Logger log = Logger.getLogger(TemplateBean.class);

    @ManagedProperty("#{sessionContextBean}")
    private SessionContextBean sessionContextBean;
    
    public SessionContextBean getSessionContextBean() {
        return sessionContextBean;
    }

    public void setSessionContextBean(SessionContextBean sessionContextBean) {
        this.sessionContextBean = sessionContextBean;
    }

    public String getGreetings() {
        log.debug("Getting user login name.");
        String retVal = sessionContextBean.getLoggedUser() != null ? "Hello, "
                + sessionContextBean.getLoggedUser().getLogin() + " !" : "";
        return retVal;
    }

    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getSessionMap().clear();
        return "logging";
    }
}
