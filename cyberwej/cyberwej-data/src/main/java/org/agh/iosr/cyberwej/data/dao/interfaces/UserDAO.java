package org.agh.iosr.cyberwej.data.dao.interfaces;

import java.util.List;

import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.GroupMember;
import org.agh.iosr.cyberwej.data.objects.User;

public interface UserDAO {
	public void saveUser(User user);
	
	public User findUserByMail(String Mail);
	
	public void addGroupMember(User user, Group group);

	public List<GroupMember> getUserGroups(String login);
}
