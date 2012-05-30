package pl.edu.agh.cyberwej.web.beans.group;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import pl.edu.agh.cyberwej.business.services.api.InvitationService;
import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.User;
import pl.edu.agh.cyberwej.web.beans.common.BaseBean;
import pl.edu.agh.cyberwej.web.beans.common.SessionContextBean;

public class SearchForUserBean extends BaseBean {
    private UserService service;

    private String login;
    private String name;
    private String surname;
    private String location;

    private int newUserId;
    private int groupId;

    @ManagedProperty(value = "#{invitationService}")
    private InvitationService invitationService;

    @ManagedProperty("#{sessionContextBean}")
    private SessionContextBean sessionContextBean;

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<User> getUsersList() {
        if (this.login == null && this.name == null && this.surname == null
                && this.location == null)
            return new LinkedList<User>();
        return this.service.findUserByCriteria(this.login, this.name,
                this.surname, this.location);
    }

    public UserService getService() {
        return this.service;
    }

    public void setService(UserService service) {
        this.service = service;
    }

    /**
     * @return the newUserId
     */
    public int getNewUserId() {
        return this.newUserId;
    }

    /**
     * @param newUserId
     *            the newUserId to set
     */
    public void setNewUserId(int newUserId) {
        this.newUserId = newUserId;
    }

    public void addInvitation() {
        int inviterId = this.sessionContextBean.getLoggedUser().getId();
        this.invitationService.inviteUser(inviterId, this.newUserId,
                this.groupId);
    }

    /**
     * @return the invitationService
     */
    public InvitationService getInvitationService() {
        return this.invitationService;
    }

    /**
     * @param invitationService
     *            the invitationService to set
     */
    public void setInvitationService(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    /**
     * @return the sessionContextBean
     */
    public SessionContextBean getSessionContextBean() {
        return this.sessionContextBean;
    }

    /**
     * @param sessionContextBean
     *            the sessionContextBean to set
     */
    public void setSessionContextBean(SessionContextBean sessionContextBean) {
        this.sessionContextBean = sessionContextBean;
    }

    /**
     * @return the groupId
     */
    public int getGroupId() {
        return this.groupId;
    }

    /**
     * @param groupId
     *            the groupId to set
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
