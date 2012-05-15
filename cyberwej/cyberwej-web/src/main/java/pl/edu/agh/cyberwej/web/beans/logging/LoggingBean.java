package pl.edu.agh.cyberwej.web.beans.logging;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

/**
 * @author Pita This is request bean used on loggingPage.jspx
 */
@ManagedBean
@RequestScoped
public class LoggingBean {
    @ManagedProperty("#{sessionContextBean}")
    private SessionContextBean sessionContextBean;

    public SessionContextBean getSessionContextBean() {
        return sessionContextBean;
    }

    public void setSessionContextBean(SessionContextBean sessionContextBean) {
        this.sessionContextBean = sessionContextBean;
    }

}