package pl.edu.agh.cyberwej.business.tests;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.agh.cyberwej.business.services.api.PaybackService;
import pl.edu.agh.cyberwej.data.dao.interfaces.PaybackDAO;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.Payback;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PaybackServiceTest {

    @Autowired
    private PaybackService paybackService;

    private User debtorMock = mock(User.class);
    private User investorMock = mock(User.class);
    private Group groupMock = mock(Group.class);
    private Payback paybackMock;
    private PaybackDAO paybackDAOMock;

    private final float amount = 32.50f;
    private final boolean isAccepted = true;

    @Before
    public void init() {
        this.paybackDAOMock = mock(PaybackDAO.class);
        this.paybackService.setPaybackDAO(this.paybackDAOMock);
    }

    @Ignore
    @Test(expected = RuntimeException.class)
    public void testCreatePayback() {
        when(
                this.paybackDAOMock.addPayback(this.debtorMock, this.investorMock, this.groupMock,
                        this.amount)).thenThrow(new RuntimeException());
        this.paybackService.createPayback(this.debtorMock, this.investorMock, this.groupMock,
                this.amount);
    }

    @Ignore
    @Test
    public void testAcceptPayback() {
        // given
        paybackMock = mock(Payback.class);
        when(paybackDAOMock.updatePayback(paybackMock)).thenReturn(true);
        when(paybackMock.getReceiver()).thenReturn(investorMock);
        when(paybackMock.getSender()).thenReturn(debtorMock);
        when(paybackMock.getGroup()).thenReturn(groupMock);
        GroupMembership groupMembershipMock = mock(GroupMembership.class);
        Set<GroupMembership> groupMemberships = new HashSet<GroupMembership>();
        groupMemberships.add(groupMembershipMock);
        when(debtorMock.getGroupMemberships()).thenReturn(groupMemberships);
        when(groupMembershipMock.getGroup()).thenReturn(groupMock);
        when(groupMock.getId()).thenReturn(1);

        // when
        this.paybackService.acceptPayback(this.paybackMock, this.isAccepted);

        // then
        verify(this.paybackMock, atLeastOnce()).setAccepted(this.isAccepted);
        verify(this.paybackMock, never()).setAccepted(!this.isAccepted);
    }
}
