package org.agh.iosr.cyberwej.web.beans.logging;

import java.util.List;

import org.agh.iosr.cyberwej.data.objects.User;
import org.agh.iosr.cyberwej.web.beans.common.BaseBean;
import org.apache.log4j.Logger;

import pl.edu.agh.cyberwej.business.services.api.UserService;

/**
 * @author pita This is managed bean used on example hello view.
 * 
 */
public class AddUserBean extends BaseBean {
	private static final Logger log = Logger.getLogger(AddUserBean.class);

	private UserService service;
	private User user;

	public AddUserBean() {
		// Initialize user
		setUser(new User());
	}

	public String remove() {
		String userToDeleteId = getParameter("userToDelete");
		service.removeUser(userToDeleteId);
		return null;

	}

	public List<User> getUsersList() {
		return service.getAllUsers();
	}

	public String saveNewUser() {
		service.saveUser(user);
		return null;
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

}
