package org.agh.iosr.cyberwej.data.dao.interfaces;

import org.agh.iosr.cyberwej.data.objects.Group;

public interface GroupDAO {
	
	public void saveGroup(Group group);
	
	public Group getGroupByName(String name);
}
