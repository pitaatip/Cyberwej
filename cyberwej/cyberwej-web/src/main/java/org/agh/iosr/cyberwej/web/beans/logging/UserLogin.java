package org.agh.iosr.cyberwej.web.beans.logging;


import java.util.List;

import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
import org.agh.iosr.cyberwej.data.objects.User;

public class UserLogin {
	
	private UserDAO dao;
	private User user;
	
	
	
	public List<User> getUsersList(){
		return dao.getAllUsers();
	}
	
	public UserLogin(){
		//Initialize user
		setUser(new User());
	}
	
	public void saveNewUser(){
		getDao().saveUser(getUser());
	}
	
	public UserDAO getDao() {
		return dao;
	}

	public void setDao(UserDAO dao) {
		this.dao = dao;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
