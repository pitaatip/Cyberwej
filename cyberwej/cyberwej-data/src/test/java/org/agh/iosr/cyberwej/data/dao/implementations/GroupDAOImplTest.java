package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupDAOImplTest {

	@Autowired
	private GroupDAO groupDAO;

	private Group group;

	private String groupName = "Pierwsza grupa";

	private String nonExistingGroupName = "Nie ma takiej grupy";

	@Before
	public void setUp() {
		this.group = new Group();
		group.setName(groupName);
		this.groupDAO.saveGroup(group);
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testSaveGroup() {
		Group retrievedGroup = this.groupDAO.getGroupByName(groupName);
		assertNotNull(retrievedGroup);
		assertEquals(retrievedGroup.getName(), this.groupName);
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testRemoveGroup() {
		this.groupDAO.removeGroup(group);
		Group retrievedGroup = this.groupDAO.getGroupByName(groupName);
		assertNull(retrievedGroup);
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testGetGroupByName() {
		Group retrievedGroup = this.groupDAO.getGroupByName(groupName);
		assertNotNull(retrievedGroup);
		retrievedGroup = this.groupDAO.getGroupByName(nonExistingGroupName);
		assertNull(retrievedGroup);
	}
}
