package org.agh.iosr.cyberwej.web.beans;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.agh.cyberwej.business.services.api.UserService;

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginTest {

	@Autowired
	private UserService service;

	@Test
	public void test() {
		assertNotNull(service);
	}

}
