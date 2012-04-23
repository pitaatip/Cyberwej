package org.agh.iosr.cyberwej.data.dao.interfaces;

import java.util.List;

import org.agh.iosr.cyberwej.data.objects.User;

public interface UserDAO {
	public boolean saveUser(User user);

	public User findUserByMail(String mail);
	
	public User findUserById(int id);

	public void removeUser(User user);

	public List<User> getAllUsers();
}
