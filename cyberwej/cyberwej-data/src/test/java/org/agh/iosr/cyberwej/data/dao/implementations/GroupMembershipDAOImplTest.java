package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.GroupMembershipDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.GroupMembership;
import org.agh.iosr.cyberwej.data.objects.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupMembershipDAOImplTest {

	@Autowired
	private GroupMembershipDAO groupMembershipDAO;

	@Autowired
	private GroupDAO groupDAO;

	@Autowired
	private UserDAO userDAO;

	private Group group;
	private final String groupName = "Pierwsza grupa";

	private User user;
	private final String name = "Jan";
	private final String surname = "Kowalski";
	private final String mail = "jan@janowie.pl";
	private final String login = "Janek";

	@BeforeTransaction
	public void setUp() {
		this.user = new User();
		user.setSurname(this.surname);
		user.setName(this.name);
		user.setLogin(this.login);
		user.setMail(this.mail);
		this.userDAO.saveUser(user);

		this.group = new Group();
		this.group.setName(groupName);
		this.groupDAO.saveGroup(this.group);

		this.groupMembershipDAO.addGroupMembership(group, user);

	}

	@Transactional
	@Rollback(true)
	@Test
	public void testAddGroupMembership() {
		User retrievedUser = this.userDAO.findUserByMail(this.mail);
		assertFalse(retrievedUser.getGroupMemberships().isEmpty());

		Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
		assertFalse(retrievedGroup.getGroupMembers().isEmpty());
		assertEquals(retrievedGroup.getGroupMembers(),
				retrievedUser.getGroupMemberships());
		for (GroupMembership groupMembership : retrievedGroup.getGroupMembers())
			assertEquals(groupMembership.getOverdraw(), 0.0f, 0.0);
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testRemoveGroupMembership() {
		User retrievedUser = this.userDAO.findUserByMail(this.mail);
		for (GroupMembership groupMembership : retrievedUser
				.getGroupMemberships())
			if (groupMembership.getGroup().getName().equals(this.groupName))
				groupMembershipDAO.removeGroupMembership(groupMembership);
		Group retrievedGroup = this.groupDAO.getGroupByName(this.groupName);
		assertTrue(retrievedGroup.getGroupMembers().isEmpty());
	}

	@AfterTransaction
	public void clean() {
		this.userDAO.removeUser(user);
		this.groupDAO.removeGroup(group);
	}
}
