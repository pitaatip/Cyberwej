package pl.edu.agh.cyberwej.business.tests;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.agh.cyberwej.business.services.impl.GroupMembershipServiceImpl;
import pl.edu.agh.cyberwej.data.dao.interfaces.GroupMembershipDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */
@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupMembershipServiceTest {

    @Autowired
    private GroupMembershipServiceImpl groupMembershipService;

    private GroupMembershipDAO groupMembershipDAOMock;
    private User userMock;
    private Group groupMock;
    private GroupMembership groupMembershipMock;
    private final float overdraw = 12.34f;
    private final float amount = -23.50f;

    @Before
    public void init() {
        this.groupMembershipDAOMock = mock(GroupMembershipDAO.class);
        this.groupMembershipService.setGroupMembershipDAO(this.groupMembershipDAOMock);
    }

    @Test
    public void testAddGroupMember() {
        this.groupMock = mock(Group.class);
        this.userMock = mock(User.class);
        when(this.groupMembershipDAOMock.addGroupMembership(this.groupMock, this.userMock))
                .thenReturn(true);
        this.groupMembershipService.addGroupMember(this.groupMock, this.userMock);
        verify(this.groupMembershipDAOMock, only()).addGroupMembership(this.groupMock,
                this.userMock);
    }

    @Test
    public void testUpdateGroupMembershipStatus() {
        this.groupMembershipMock = mock(GroupMembership.class);
        when(this.groupMembershipMock.getOverdraw()).thenReturn(this.overdraw);
        doNothing().when(this.groupMembershipMock).setOverdraw(this.overdraw + this.amount);
        when(this.groupMembershipDAOMock.save(this.groupMembershipMock)).thenReturn(false);
        this.groupMembershipService.updateGroupMembershipStatus(this.groupMembershipMock,
                this.amount);
        verify(this.groupMembershipMock, atLeastOnce()).setOverdraw(this.overdraw + this.amount);
        verify(this.groupMembershipDAOMock, only()).save(this.groupMembershipMock);
    }
}
