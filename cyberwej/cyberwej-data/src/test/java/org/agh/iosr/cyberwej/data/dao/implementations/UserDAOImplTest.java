package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
    private final String name = "Jan";
    private final String surname = "Kowalski";
    private final String mail = "jan@janowie.pl";
    private final String login = "Janek";

    private final String nonExistingMail = "non@existing.mail";

    @Before
    public void setUp() {
        this.user = new User();
        this.user.setSurname(this.surname);
        this.user.setName(this.name);
        this.user.setLogin(this.login);
        this.user.setMail(this.mail);
        this.userDAO.saveUser(this.user);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testSaveUser() {
        User retrievedUser = this.userDAO.findUserByMail(this.user.getMail());
        assertNotNull(retrievedUser);
        assertEquals(this.user.getName(), retrievedUser.getName());
        assertEquals(this.user.getSurname(), retrievedUser.getSurname());
        assertEquals(this.user.getLogin(), retrievedUser.getLogin());
        assertEquals(this.user.getLocation(), retrievedUser.getLocation());
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRemoveUser() {
        this.userDAO.removeUser(this.user);
        User retrievedUser = this.userDAO.findUserByMail(this.user.getMail());
        assertNull(retrievedUser);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testFindUserByMail() {
        User retrievedUser = this.userDAO.findUserByMail(this.mail);
        assertNotNull(retrievedUser);
        assertEquals(retrievedUser.getMail(), this.mail);
        retrievedUser = this.userDAO.findUserByMail(this.nonExistingMail);
        assertNull(retrievedUser);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testGetAllUsers() {
        List<User> users = this.userDAO.getAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertTrue(users.contains(this.user));
    }
}
