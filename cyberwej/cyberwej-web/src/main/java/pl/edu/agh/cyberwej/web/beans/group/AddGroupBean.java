/**
 * 
 */
package pl.edu.agh.cyberwej.web.beans.group;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import pl.edu.agh.cyberwej.business.services.api.GroupService;
import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

/**
 * @author Pita
 * 
 */

public class AddGroupBean extends BaseBean {
    private static final String GROUP2ADD = "group2add";
    private Group group = new Group();
    private List<User> users = new ArrayList<User>();
    private Set<Integer> usersIdsSet = new HashSet<Integer>();
    private String groupName;
    private User loggedUser;
    private int newUserId;
    private int userToBeRemovedId;

    private UserService userService;
    private GroupService groupService;
    private SessionContextBean sessionContextBean;
    
    public SessionContextBean getSessionContextBean() {
        return sessionContextBean;
    }

    public void setSessionContextBean(SessionContextBean sessionContextBean) {
        this.sessionContextBean = sessionContextBean;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String next() {
        group = new Group();
        group.setName(groupName);
        groupService.saveGroupWithItsMembersIds(group, usersIdsSet);
        //sessionContextBean.getMap4Stuff().put(GROUP2ADD, getGroup());
        //return "addGroupSummary";
        return "main";
    }
    
    public Group getGroup() {
        if(group == null) {
            group = (Group) sessionContextBean.getMap4Stuff().get(GROUP2ADD);
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
    
    public void setNewUserId(int userId) {
        this.newUserId = userId;
        addUserById(userId);
    }
    
    public int getNewUser() {
        return newUserId;
    }
    
    public int getUserToBeRemovedId() {
        return userToBeRemovedId;
    }

    public void setUserToBeRemovedId(int userToBeRemovedId) {
        this.userToBeRemovedId = userToBeRemovedId;
        removeUserById(userToBeRemovedId);
    }

    @PostConstruct
    public void addLoggedUser() {
        loggedUser = sessionContextBean.getLoggedUser();
        users.add(loggedUser);
        usersIdsSet.add(loggedUser.getId());
    }


    public List<User> getUsers() {
        return users;
    }
    
    private void addUserById(int userId) {
        if(usersIdsSet.add(userId)) {
            users.add(userService.getUserById(userId));
        }
    }
    
    private void removeUserById(int userId) {
        if(loggedUser.getId() == userId) {
            return;
        }
        usersIdsSet.remove(userId);
        for(Iterator<User> it = users.iterator(); it.hasNext(); ) {
            User user = it.next();
            if(user.getId() == userId) {
                it.remove();
                break;
            }
        }
    }
}
