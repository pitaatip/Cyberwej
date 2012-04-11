package org.agh.iosr.cyberwej.data.dao;

import org.agh.iosr.cyberwej.data.objects.User;

public interface UserDAO {
	public void saveUser(User user);
	
	public User findUserByMail(String Mail);
}
