package org.agh.iosr.cyberwej.web.beans;

import org.agh.iosr.cyberwej.data.dao.interfaces.UserDAO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginTest {

	@Autowired
	private UserDAO userDAO;
	
	@Test
	public void test() {
		assertNotNull(userDAO);
	}

}
