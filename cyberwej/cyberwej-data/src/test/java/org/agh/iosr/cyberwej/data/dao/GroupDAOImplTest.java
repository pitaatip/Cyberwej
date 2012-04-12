package org.agh.iosr.cyberwej.data.dao;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupDAOImplTest {

	@Autowired
	private GroupDAO groupDAO;

	@Test
	public void testSaveGroup() {
		Group group = new Group();
		group.setName("Pierwsza grupa");
		this.groupDAO.saveGroup(group);
	}

}
