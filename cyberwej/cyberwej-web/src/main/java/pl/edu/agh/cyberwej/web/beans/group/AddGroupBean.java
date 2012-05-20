/**
 * 
 */
package pl.edu.agh.cyberwej.web.beans.group;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;

/**
 * @author Pita
 * 
 */

public class AddGroupBean extends BaseBean {
    private static final String GROUP2ADD = "group2add";
    private Group group = new Group();
    private List<User> users = new ArrayList<User>();
    private String groupName;
    private int newUserId;
    //private GroupService groupService;
    private UserService userService;
    
    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String next() {
        getMap4Stuff().put(GROUP2ADD, getGroup());
        return "addGroupSummary";
    }
    
    public Group getGroup() {
        if (group == null) {
            group = (Group) getMap4Stuff().get(GROUP2ADD);
        }
        return group;
    }
    
    public void setGroup(Group group) {
        this.group = group;
    }
    
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public String addUser(User user) {
        users.add(user);
        return "";
    }
    
    public void setNewUserId(int userId) {
        this.newUserId = userId;
        users.add(userService.getUserById(userId));
    }
    
    public int getNewUser() {
        return newUserId;
    }

    @PostConstruct
    public void addLoggedUser() {
        //TODO
    }


    public List<User> getUsers() {
        return users;
    }
}
