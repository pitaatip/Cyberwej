package pl.edu.agh.cyberwej.web.beans.group;

import java.util.List;

import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.objects.User;

public class SearchForUserBean {
    private UserService service;

    private String login;
    private String name;
    private String surname;
    private String location;

    private int newUserId;

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

    }
}
