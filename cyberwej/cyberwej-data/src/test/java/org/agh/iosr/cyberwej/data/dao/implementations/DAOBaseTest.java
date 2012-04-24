package org.agh.iosr.cyberwej.data.dao.implementations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
public class DAOBaseTest {

	@Autowired
	private GroupDAOImpl dao;

	private Group group;
	private final String groupName = "Pierwsza grupa";

	@Before
	public void setUp() {
		this.group = new Group();
		group.setName(groupName);
	}

	@Transactional
	@Rollback(true)
	@Test
	public void testSave() {
		assertTrue(this.dao.save(this.group));
		setUp();
		assertFalse(this.dao.save(this.group));
	}
}
