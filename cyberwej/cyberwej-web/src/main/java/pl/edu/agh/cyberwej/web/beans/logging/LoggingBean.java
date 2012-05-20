package pl.edu.agh.cyberwej.web.beans.logging;

import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.CryptoUtil;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

/**
 * @author Pita This is request bean used on loggingPage.jspx
 */
public class LoggingBean {
    private boolean nonExistError;
    private boolean wrongPasswordError;
    private String userLogin;
    private UserService service;
    private String password;

    private SessionContextBean sessionContextBean;

    public String logIn() {

        User user = getService().getUserByLogin(userLogin);
        if (user != null) {
            String password2 = user.getPassword();
            try {
                if (password2 == null || password2.equals(CryptoUtil.encrypt(getPassword()))) {
                    sessionContextBean.setLoggedUser(user);
                    return "main";
                }
                wrongPasswordError = true;
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setNonExistError(true);
        return null;
    }

    public SessionContextBean getSessionContextBean() {
        return sessionContextBean;
    }

    public void setSessionContextBean(SessionContextBean sessionContextBean) {
        this.sessionContextBean = sessionContextBean;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isWrongPasswordError() {
        return wrongPasswordError;
    }

    public void setWrongPasswordError(boolean wrongPasswordError) {
        this.wrongPasswordError = wrongPasswordError;
    }

    public boolean isNonExistError() {
        return nonExistError;
    }

    public void setNonExistError(boolean nonExistError) {
        this.nonExistError = nonExistError;
    }

}
