/**
 * 
 */
package org.agh.iosr.cyberwej.web.beans.common;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Pita
 *
 */

@ManagedBean
@SessionScoped
public class SessionContextBean {
private String userLogin;

public String getUserLogin() {
    return userLogin;
}

public void setUserLogin(String userLogin) {
    this.userLogin = userLogin;
}
}
