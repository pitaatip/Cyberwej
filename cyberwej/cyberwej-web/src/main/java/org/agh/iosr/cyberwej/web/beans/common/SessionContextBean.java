package org.agh.iosr.cyberwej.web.beans.common;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Pita
 * This is session scoped bean, which is used to store data specific for each session.
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
