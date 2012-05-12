package pl.edu.agh.cyberwej.business.tests;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.agh.iosr.cyberwej.data.dao.interfaces.PaybackDAO;
import org.agh.iosr.cyberwej.data.objects.Group;
import org.agh.iosr.cyberwej.data.objects.Payback;
import org.agh.iosr.cyberwej.data.objects.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.agh.cyberwej.business.services.impl.PaybackServiceImpl;

/**
 * 
 * @author Krzysztof
 * 
 */

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PaybackServiceTest {

    @Autowired
    private PaybackServiceImpl paybackService;

    private User debtorMock;
    private User investorMock;
    private Group groupMock;
    private Payback paybackMock;
    private PaybackDAO paybackDAOMock;

    private final float amount = 32.50f;
    private final boolean isAccepted = true;

    @Before
    public void init() {
        this.paybackDAOMock = mock(PaybackDAO.class);
        this.paybackService.setPaybackDAO(this.paybackDAOMock);
    }

    @Test(expected = RuntimeException.class)
    public void testCreatePayback() {
        this.debtorMock = mock(User.class);
        this.investorMock = mock(User.class);
        this.groupMock = mock(Group.class);
        when(
                this.paybackDAOMock.addPayback(this.debtorMock,
                        this.investorMock, this.groupMock, this.amount))
                .thenThrow(new RuntimeException());
        this.paybackService.createPayback(this.debtorMock, this.investorMock,
                this.groupMock, this.amount);
    }

    @Test
    public void testAcceptPayback() {
        this.paybackMock = mock(Payback.class);
        when(this.paybackDAOMock.updatePayback(this.paybackMock)).thenReturn(
                true);
        this.paybackService.acceptPayback(this.paybackMock, this.isAccepted);
        verify(this.paybackMock, atLeastOnce()).setAccepted(this.isAccepted);
        verify(this.paybackMock, never()).setAccepted(!this.isAccepted);
    }
}
