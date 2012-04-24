package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
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
public class UserDAOImplTest {

    @Autowired
    private UserDAO userDAO;

    private User user;
    private String name = "Jan";
    private String surname = "Kowalski";
    private String mail = "jan@janowie.pl";
    private String login = "Janek";

    private String nonExistingMail = "non@existing.mail";

    @Before
    public void setUp() {
        this.user = new User();
        user.setSurname(this.surname);
        user.setName(this.name);
        user.setLogin(this.login);
        user.setMail(this.mail);
        this.userDAO.saveUser(user);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testSaveUser() {
        User retrievedUser = this.userDAO.findUserByMail(user.getMail());
        assertNotNull(retrievedUser);
        assertEquals(user.getName(), retrievedUser.getName());
        assertEquals(user.getSurname(), retrievedUser.getSurname());
        assertEquals(user.getLogin(), retrievedUser.getLogin());
        assertEquals(user.getLocation(), retrievedUser.getLocation());
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRemoveUser() {
        this.userDAO.removeUser(this.user);
        User retrievedUser = this.userDAO.findUserByMail(user.getMail());
        assertNull(retrievedUser);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testFindUserByMail() {
        User retrievedUser = this.userDAO.findUserByMail(this.mail);
        assertNotNull(retrievedUser);
        assertEquals(retrievedUser.getMail(), this.mail);
        retrievedUser = this.userDAO.findUserByMail(nonExistingMail);
        assertNull(retrievedUser);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testGetAllUsers() {
        List<User> users = this.userDAO.getAllUsers();
        assertNotNull(users);
        assertEquals(users.size(), 1);
        assertTrue(users.contains(this.user));
    }
}
