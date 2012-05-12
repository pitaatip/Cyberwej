package pl.edu.agh.cyberwej.business.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.agh.iosr.cyberwej.data.dao.interfaces.GroupDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.agh.cyberwej.business.services.impl.GroupServiceImpl;

/**
 * 
 * @author Krzysztof
 * 
 */
@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupServiceTest {

    @Autowired
    private GroupServiceImpl groupService;

    private GroupDAO groupDAOMock;
    private Group groupMock;

    @Before
    public void init() {
        this.groupDAOMock = mock(GroupDAO.class);
        this.groupMock = mock(Group.class);
    }

    @Test(expected = RuntimeException.class)
    public void testSaveGroup() {
        when(this.groupDAOMock.saveGroup(this.groupMock)).thenThrow(
                new RuntimeException());
        this.groupService.setGroupDAO(this.groupDAOMock);
        this.groupService.saveGroup(this.groupMock);
    }
}
