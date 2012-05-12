package pl.edu.agh.cyberwej.business.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.PaymentItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.edu.agh.cyberwej.business.services.impl.PaymentServiceImpl;

/**
 * 
 * @author Krzysztof
 * 
 */

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentServiceTest {

    @Autowired
    private PaymentServiceImpl paymentService;

    private Payment paymentMock;

    private PaymentItem paymentItemMock;
    private final int count = 3;
    private final float price = 2.50f;

    private PaymentItem paymentItemMock2;
    private final int count2 = 1;
    private final float price2 = 3.00f;

    @Test
    public void testGetPaymentCost() {
        this.paymentMock = mock(Payment.class);
        Set<PaymentItem> paymentItems = new HashSet<PaymentItem>();
        when(this.paymentMock.getPaymentItems()).thenReturn(paymentItems);
        assertEquals(this.paymentService.getPaymentCost(this.paymentMock),
                0.0f, 0.0f);
        this.paymentItemMock = mock(PaymentItem.class);
        when(this.paymentItemMock.getCount()).thenReturn(this.count);
        when(this.paymentItemMock.getPrice()).thenReturn(this.price);
        this.paymentItemMock2 = mock(PaymentItem.class);
        when(this.paymentItemMock2.getCount()).thenReturn(this.count2);
        when(this.paymentItemMock2.getPrice()).thenReturn(this.price2);
        paymentItems.add(this.paymentItemMock);
        paymentItems.add(this.paymentItemMock2);
        assertEquals(this.paymentService.getPaymentCost(this.paymentMock),
                this.count * this.price + this.count2 * this.price2, 0.0f);

    }
}
