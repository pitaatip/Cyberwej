package org.agh.iosr.cyberwej.web.beans.logging;


import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
import org.agh.iosr.cyberwej.data.objects.User;

public class UserLogin {
	
	private UserDAO dao;
	private String name;
	private String surname;
	private String login;
	private String mail;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void saveNewUser(){
		User user = new User();
		user.setName(getName());
		user.setSurname(getSurname());
		user.setLogin(getLogin());
		user.setMail(getMail());
		getDao().saveUser(user);
	}
	
	public UserDAO getDao() {
		return dao;
	}

	public void setDao(UserDAO dao) {
		this.dao = dao;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	

}
