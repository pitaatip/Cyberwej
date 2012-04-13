package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.GroupMembershipDAO;
import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.User;
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
public class GroupMembershipDAOImplTest {

	@Autowired
	private GroupMembershipDAO groupMembershipDAO;

	@Autowired
	private GroupDAO groupDAO;

	@Autowired
	private UserDAO userDAO;

	private Group group;
	private String groupName = "Pierwsza grupa";

	private User user;
	private String name = "Jan";
	private String surname = "Kowalski";
	private String mail = "jan@janowie.pl";
	private String login = "Janek";

	@Before
	public void setUp() {
		this.user = new User();
		user.setSurname(this.surname);
		user.setName(this.name);
		user.setLogin(this.login);
		user.setMail(this.mail);
		this.userDAO.saveUser(user);

		this.group = new Group();
		group.setName(groupName);
		this.groupDAO.saveGroup(group);

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
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testRemoveGroupMembership() {
		fail("Not yet implemented");
	}

}
