package org.agh.iosr.cyberwej.data.dao.interfaces;

import org.agh.iosr.cyberwej.data.objects.Group;

public interface GroupDAO {

	public boolean saveGroup(Group group);

	public Group getGroupByName(String name);

	public void removeGroup(Group group);

}
