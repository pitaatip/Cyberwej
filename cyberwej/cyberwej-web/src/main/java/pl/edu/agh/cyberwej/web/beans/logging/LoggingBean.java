package pl.edu.agh.cyberwej.web.beans.logging;

import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

/**
 * @author Pita This is request bean used on loggingPage.jspx
 */
public class LoggingBean {
    private boolean loginErrors;
    private String userLogin;
    private UserService service;

    private SessionContextBean sessionContextBean;

    public String logIn() {

        User user = getService().getUserByLogin(userLogin);
        if (user != null) {
            sessionContextBean.setLoggedUser(user);
            return "main";
        } else {
            loginErrors = true;
            return null;
        }
    }

    public SessionContextBean getSessionContextBean() {
        return sessionContextBean;
    }

    public void setSessionContextBean(SessionContextBean sessionContextBean) {
        this.sessionContextBean = sessionContextBean;
    }

    public boolean isLoginErrors() {
        return loginErrors;
    }

    public void setLoginErrors(boolean loginErrors) {
        this.loginErrors = loginErrors;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public UserService getService() {
        return service;
    }

    public void setService(UserService service) {
        this.service = service;
    }

}
