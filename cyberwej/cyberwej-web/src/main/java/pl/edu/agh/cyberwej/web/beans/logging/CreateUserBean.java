package pl.edu.agh.cyberwej.web.beans.logging;

import org.apache.log4j.Logger;

import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;
import pl.edu.agh.cyberwej.web.beans.common.CryptoUtil;

/**
 * @author pita This is managed bean used on addUserComponent.xhtml.
 */
public class CreateUserBean extends BaseBean {
    private static final Logger log = Logger.getLogger(CreateUserBean.class);

    private UserService service;
    private User user;
    private String password;

    public CreateUserBean() {
        // Initialize user
        setUser(new User());
        log.info("Initialized");
    }

    /**
     * Save user in database.
     * 
     * @return
     */
    public String saveNewUser() {
        try {
            user.setPassword(CryptoUtil.encrypt(getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.saveUser(user);
        return "logging";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

}
