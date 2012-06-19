package pl.edu.agh.cyberwej.web.beans.views.user;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.common.objects.service.GroupInformation;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;

/**
 * @author Krzysztof
 */
@ManagedBean
@RequestScoped
public class UserInformationBean extends BaseBean {

    private User user = new User();

    private static String USERTOSHOW = "userToShow";

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    @PostConstruct
    public void construct() {
        String userId = getParameter(USERTOSHOW);
        this.user = this.userService.getUserById(Integer.parseInt(userId));
    }

    /**
     * @return the userService
     */
    public UserService getUserService() {
        return this.userService;
    }

    /**
     * @param userService
     *            the userService to set
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return this.user;
    }

    public List<GroupInformation> getGroupMemberships() {
        return this.userService.getUserGroupsInformation(this.user);
    }
}
