/**
 * 
 */
package org.agh.iosr.cyberwej.web.beans.logging;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.agh.iosr.cyberwej.web.beans.common.SessionContextBean;

/**
 * @author Pita
 *
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
