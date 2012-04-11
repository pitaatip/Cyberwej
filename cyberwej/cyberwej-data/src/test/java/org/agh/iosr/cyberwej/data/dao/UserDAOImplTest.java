package org.agh.iosr.cyberwej.data.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.agh.iosr.cyberwej.data.objects.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDAOImplTest {

	@Autowired
	private UserDAO userDAO;

	@Test
	public void testSaveUser() {
		User user = new User();
		user.setSurname("Kowalski");
		user.setName("Jan");
		user.setLogin("Janek");
		user.setMail("jan@janowie.pl");
		this.userDAO.saveUser(user);
		User retrievedUser = this.userDAO.findUserByMail(user.getMail());
		assertNotNull(retrievedUser);
		assertEquals(user.getName(), retrievedUser.getName());
		assertEquals(user.getSurname(), retrievedUser.getSurname());
		assertEquals(user.getLogin(), retrievedUser.getLogin());
		assertEquals(user.getLocation(), retrievedUser.getLocation());
	}

}
