package pl.edu.agh.cyberwej.business.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.business.services.api.PaymentService;
import pl.edu.agh.cyberwej.data.dao.interfaces.PaymentDAO;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.PaymentItem;
import pl.edu.agh.cyberwej.data.objects.PaymentParticipation;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */

@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    private Payment paymentMock;

    private PaymentItem paymentItemMock;
    private final int count = 3;
    private final float price = 2.50f;

    private PaymentItem paymentItemMock2;
    private final int count2 = 1;
    private final float price2 = 3.00f;

    private Set<PaymentItem> paymentItems;

    private PaymentDAO paymentDAO;

    @Before
    public void init() {
        this.paymentMock = mock(Payment.class);
        this.paymentItems = new HashSet<PaymentItem>();
        when(this.paymentMock.getPaymentItems()).thenReturn(this.paymentItems);
        this.paymentItemMock = mock(PaymentItem.class);
        when(this.paymentItemMock.getCount()).thenReturn(this.count);
        when(this.paymentItemMock.getPrice()).thenReturn(this.price);
        this.paymentItemMock2 = mock(PaymentItem.class);
        when(this.paymentItemMock2.getCount()).thenReturn(this.count2);
        when(this.paymentItemMock2.getPrice()).thenReturn(this.price2);
        this.paymentItems.add(this.paymentItemMock);
        this.paymentItems.add(this.paymentItemMock2);
    }

    @Test
    @Transactional
    public void testGetPaymentCost() {
        assertEquals(this.paymentService.getPaymentCost(this.paymentMock), this.count * this.price
                + this.count2 * this.price2, 0.0f);
        this.paymentItems.remove(this.paymentItemMock);
        this.paymentItems.remove(this.paymentItemMock2);
        assertEquals(this.paymentService.getPaymentCost(this.paymentMock), 0.0f, 0.0f);
    }

    @Test
    public void testGetUserStatusInPayment() {
        User user1Mock = mock(User.class);
        User user2Mock = mock(User.class);
        int user1Id = 1;
        int user2Id = 2;
        when(user1Mock.getId()).thenReturn(user1Id);
        when(user2Mock.getId()).thenReturn(user2Id);

        Set<User> consumers1 = new HashSet<User>();
        Set<User> consumers2 = new HashSet<User>();
        consumers1.add(user2Mock);
        consumers1.add(user1Mock);
        consumers2.add(user1Mock);

        when(this.paymentItemMock.getConsumers()).thenReturn(consumers1);
        when(this.paymentItemMock2.getConsumers()).thenReturn(consumers2);

        PaymentParticipation participation1Mock = mock(PaymentParticipation.class);

        float amount1 = 2.50f;

        Set<PaymentParticipation> participations1 = new HashSet<PaymentParticipation>();

        when(participation1Mock.getAmount()).thenReturn(amount1);

        when(participation1Mock.getUser()).thenReturn(user1Mock);

        participations1.add(participation1Mock);

        when(this.paymentMock.getParticipations()).thenReturn(participations1);

        assertEquals(this.paymentService.getUserStatusInPayment(this.paymentMock, user1Mock),
                amount1 - this.price * this.count / 2.0f - this.price2 * this.count2, 0.0f);

        assertEquals(this.paymentService.getUserStatusInPayment(this.paymentMock, user2Mock),
                -this.price * this.count / 2.0f, 0.0f);

    }

    @Test
    public void testGetLastPayments() {
        Payment payment2Mock = mock(Payment.class);
        Payment payment3Mock = mock(Payment.class);

        Date[] dates = new Date[] { new Date(300), new Date(200), new Date(100) };
        when(this.paymentMock.getDate()).thenReturn(dates[0]);
        when(payment2Mock.getDate()).thenReturn(dates[1]);
        when(payment3Mock.getDate()).thenReturn(dates[2]);

        User user1Mock = mock(User.class);
        int user1Id = 1;
        when(user1Mock.getId()).thenReturn(user1Id);

        Set<User> consumers1 = new HashSet<User>();
        consumers1.add(user1Mock);

        when(this.paymentItemMock.getConsumers()).thenReturn(consumers1);

        Set<PaymentParticipation> participationsEmpty = new HashSet<PaymentParticipation>();
        PaymentParticipation participation1Mock = mock(PaymentParticipation.class);
        float amount1 = 2.50f;
        when(participation1Mock.getAmount()).thenReturn(amount1);
        when(participation1Mock.getUser()).thenReturn(user1Mock);

        when(this.paymentMock.getParticipations()).thenReturn(participationsEmpty);
        when(payment2Mock.getParticipations()).thenReturn(participationsEmpty);
        when(payment3Mock.getParticipations()).thenReturn(participationsEmpty);

        when(this.paymentMock.getPaymentItems()).thenReturn(this.paymentItems);
        when(payment2Mock.getPaymentItems()).thenReturn(this.paymentItems);
        when(payment3Mock.getPaymentItems()).thenReturn(this.paymentItems);

        Map<Payment, Float> lastPayments = this.paymentService.getLastPayments(0, user1Mock);
        assertTrue(lastPayments.isEmpty());

        List<Payment> consumedPayments = new LinkedList<Payment>();
        List<Payment> participatedPayments = new LinkedList<Payment>();
        consumedPayments.add(this.paymentMock);
        consumedPayments.add(payment3Mock);

        participatedPayments.add(payment2Mock);
        participatedPayments.add(payment3Mock);

        int count = 1;
        this.paymentDAO = mock(PaymentDAO.class);
        this.paymentService.setPaymentDAO(this.paymentDAO);

        when(this.paymentDAO.getLastConsumedPayments(count, user1Mock))
                .thenReturn(consumedPayments);
        when(this.paymentDAO.getLastParticipatedPayments(count, user1Mock)).thenReturn(
                participatedPayments);

        lastPayments = this.paymentService.getLastPayments(count, user1Mock);
        assertFalse(lastPayments.isEmpty());
        assertEquals(lastPayments.size(), count);
        for (Payment payment : lastPayments.keySet())
            assertEquals(dates[0], payment.getDate());

        count = 2;
        consumedPayments.clear();
        participatedPayments.add(this.paymentMock);
        when(this.paymentDAO.getLastConsumedPayments(count, user1Mock))
                .thenReturn(consumedPayments);
        when(this.paymentDAO.getLastParticipatedPayments(count, user1Mock)).thenReturn(
                participatedPayments);

        lastPayments = this.paymentService.getLastPayments(count, user1Mock);
        assertFalse(lastPayments.isEmpty());
        assertEquals(lastPayments.size(), count);

        for (Payment payment : lastPayments.keySet())
            assertTrue(payment.getDate().equals(dates[0]) || payment.getDate().equals(dates[1]));
        count = 4;
        consumedPayments.clear();
        consumedPayments.addAll(participatedPayments);
        participatedPayments.clear();
        when(this.paymentDAO.getLastConsumedPayments(count, user1Mock))
                .thenReturn(consumedPayments);
        when(this.paymentDAO.getLastParticipatedPayments(count, user1Mock)).thenReturn(
                participatedPayments);

        lastPayments = this.paymentService.getLastPayments(count, user1Mock);
        assertFalse(lastPayments.isEmpty());
        assertEquals(lastPayments.size(), consumedPayments.size());
    }

}
