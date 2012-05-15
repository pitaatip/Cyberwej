/**
 * 
 */
package pl.edu.agh.cyberwej.business.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.agh.cyberwej.business.services.api.UserService;
import pl.edu.agh.cyberwej.data.dao.interfaces.UserDAO;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author pita
 * 
 */
@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {
    private static final User user = new User();

    @BeforeClass
    public static void init() {
        user.setName("Waldek");
        user.setId(12);
    }

    @Autowired
    UserService service;

    /**
     * Test method for
     * {@link pl.edu.agh.cyberwej.business.services.impl.UserServiceImpl#getAllUsers()}
     * .
     */
    @Test
    public void testGetAllUsers() {
        // prepare returened list
        List<User> list = new ArrayList<User>();
        list.add(user);

        // prepare mock
        UserDAO dao = mock(UserDAO.class);
        when(dao.getAllUsers()).thenReturn(list);
        this.service.setDao(dao);

        // verify
        assertEquals(this.service.getAllUsers(), list);

    }

    /**
     * Test method for
     * {@link pl.edu.agh.cyberwej.business.services.impl.UserServiceImpl#saveUser(pl.edu.agh.cyberwej.data.objects.User)}
     * .
     */
    @Test(expected = Throwable.class)
    public void testSaveUser() {
        // prepare mock
        UserDAO dao = mock(UserDAO.class);
        doThrow(new Exception()).when(dao).saveUser(user);
        this.service.setDao(dao);

        // verify
        this.service.saveUser(user);

    }

    /**
     * Test method for
     * {@link pl.edu.agh.cyberwej.business.services.impl.UserServiceImpl#removeUser(java.lang.String)}
     * .
     */
    @Test(expected = Throwable.class)
    public void testRemoveUser() {
        // prepare mock
        UserDAO dao = mock(UserDAO.class);
        doThrow(new Exception()).when(dao).removeUser(user);
        this.service.setDao(dao);

        // verify
        this.service.removeUser(String.valueOf(user.getId()));
    }
}
