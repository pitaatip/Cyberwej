package pl.edu.agh.cyberwej.business.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.agh.cyberwej.common.objects.service.PaymentInformation;
import pl.edu.agh.cyberwej.data.objects.Group;
import pl.edu.agh.cyberwej.data.objects.GroupMembership;
import pl.edu.agh.cyberwej.data.objects.Payback;
import pl.edu.agh.cyberwej.data.objects.Payment;
import pl.edu.agh.cyberwej.data.objects.PaymentItem;
import pl.edu.agh.cyberwej.data.objects.PaymentParticipation;
import pl.edu.agh.cyberwej.data.objects.Product;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Pita
 * 
 */
@ContextConfiguration(locations = { "TestContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentAndPaybackTest extends BaseIntegrationTest {

    private static final String MOCK_PAYMENT = "Mock payment";

    /**
     * The mock payment is saved to database in this example. Mock payment has 3
     * items - Coke - cost:10, amount:2, consumers:user1,user2 Pizza - cost:30,
     * amount:1, consumers:user1,user2,user3 Beer - cost:20, amount:1,
     * consumers:user2 Mock payment has 2 payers - user1 - payed 40 user3 -
     * payed 30
     */
    @Before
    public void registerUsersGroupPayment() {
        setUserNr(4);
        // create and save users
        createUsers();
        LinkedList<User> users = retrieveUsers();

        // create and save group
        createGroup(users);
        Group group = getGroupService().getGroupById(groupId);

        // accept invitations so that all users are members of group
        acceptAllInvitations();

        // create payment items
        Set<PaymentItem> paymentItems = new HashSet<PaymentItem>();
        paymentItems.add(createPaymentItem("Coke", 2, 10, users.get(0), users.get(1)));
        paymentItems
                .add(createPaymentItem("Pizza", 1, 30, users.get(0), users.get(1), users.get(2)));
        paymentItems.add(createPaymentItem("Beer", 1, 20, users.get(1)));

        // create payment participators
        Set<PaymentParticipation> paymentParticipators = new HashSet<PaymentParticipation>();
        paymentParticipators.add(createPaymentParticipation(users.get(0), 40));
        paymentParticipators.add(createPaymentParticipation(users.get(2), 30));
        // save payment

        getPaymentService()
                .registerPayment(group, paymentItems, paymentParticipators, MOCK_PAYMENT);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testRetrievePayment() {
        Group group = getGroupService().getGroupById(groupId);
        List<PaymentInformation> groupPayments = getPaymentService().getGroupPayments(group);
        assertNotNull(groupPayments);
        assertEquals(1, groupPayments.size());
        assertEquals(MOCK_PAYMENT, groupPayments.get(0).getPayment().getDescription());
        assertEquals(new Float(70.0), new Float(groupPayments.get(0).getAmount()));
        assertEquals(3, groupPayments.get(0).getParticipantsCount());
    }

    /**
     * Actual group status should be: user1: +20 user2: -40 user3: +20 user4:0
     */
    @Transactional
    @Rollback(true)
    @Test
    public void testCorrectGroupStatusAfterPayment() {
        Group group = getGroupService().getGroupById(groupId);
        LinkedList<User> users = retrieveUsers();

        // check group membership status
        for (GroupMembership groupMember : group.getGroupMembers()) {
            if (groupMember.getUser().getId().equals(users.get(0).getId())) {
                assertEquals(new Double(groupMember.getOverdraw()), new Double(20));
            }
            if (groupMember.getUser().getId().equals(users.get(1).getId())) {
                assertEquals(new Double(groupMember.getOverdraw()), new Double(-40));
            }
            if (groupMember.getUser().getId().equals(users.get(2).getId())) {
                assertEquals(new Double(groupMember.getOverdraw()), new Double(20));
            }
            if (groupMember.getUser().getId().equals(users.get(3).getId())) {
                assertEquals(new Double(groupMember.getOverdraw()), new Double(0));
            }

        }

    }

    /**
     * In this test another payment items are created and added to payment:
     * wodka - cost:60, amount:1, consumers:user1,user2,user3 cake - cost:15,
     * amount:1, consumers:user1 After this, group status should be: user1: -15
     * user2: -60 user3: 0 user4: 0
     * 
     */
    @Transactional
    @Rollback(true)
    @Test
    public void testAddAnotherItem() {
        LinkedList<User> users = retrieveUsers();
        Group group = getGroupService().getGroupById(groupId);

        // create new payment items
        LinkedList<PaymentItem> hashSet = new LinkedList<PaymentItem>();
        hashSet.add(createPaymentItem("wodka", 1, 60, users.get(0), users.get(1), users.get(2)));
        hashSet.add(createPaymentItem("cake", 1, 15, users.get(0)));

        // save new items
        List<PaymentInformation> groupPayments = getPaymentService().getGroupPayments(group);
        Payment payment = groupPayments.get(0).getPayment();
        getPaymentService().addPaymentItems(payment, hashSet);

        // check group membership status
        group = getGroupService().getGroupById(groupId);
        for (GroupMembership groupMember : group.getGroupMembers()) {
            if (groupMember.getUser().getId().equals(users.get(0).getId())) {
                assertEquals(new Double(groupMember.getOverdraw()), new Double(-15));
            }
            if (groupMember.getUser().getId().equals(users.get(1).getId())) {
                assertEquals(new Double(groupMember.getOverdraw()), new Double(-60));
            }
            if (groupMember.getUser().getId().equals(users.get(2).getId())) {
                assertEquals(new Double(groupMember.getOverdraw()), new Double(0));
            }
            if (groupMember.getUser().getId().equals(users.get(3).getId())) {
                assertEquals(new Double(groupMember.getOverdraw()), new Double(0));
            }
        }
    }

    /**
     * In this test payback is registered and accepted: user2 is giving back 20
     * to user3 After this, group status should be: user1: 20 user2: -20 user3:
     * 0 user4: 0
     * 
     */
    @Transactional
    @Rollback(true)
    @Test
    public void testRegisterPayback() {
        LinkedList<User> users = retrieveUsers();
        Group group = getGroupService().getGroupById(groupId);

        // register payback
        getPaybackService().createPayback(users.get(1), users.get(2), group, 20);

        // get payback notification
        List<Payback> paybacksForUser = getPaybackService().getPaybacksForUser(users.get(2), true);
        assertNotNull(paybacksForUser);
        assertEquals(1, paybacksForUser.size());

        // accept payback
        Payback payback = paybacksForUser.get(0);
        getPaybackService().acceptPayback(payback, true);

        // check group membership status
        group = getGroupService().getGroupById(groupId);
        for (GroupMembership groupMember : group.getGroupMembers()) {
            if (groupMember.getUser().getId().equals(users.get(0).getId())) {
                assertEquals(new Double(groupMember.getOverdraw()), new Double(20));
            }
            if (groupMember.getUser().getId().equals(users.get(1).getId())) {
                assertEquals(new Double(groupMember.getOverdraw()), new Double(-20));
            }
            if (groupMember.getUser().getId().equals(users.get(2).getId())) {
                assertEquals(new Double(groupMember.getOverdraw()), new Double(0));
            }
            if (groupMember.getUser().getId().equals(users.get(3).getId())) {
                assertEquals(new Double(groupMember.getOverdraw()), new Double(0));
            }
        }
    }

    private PaymentParticipation createPaymentParticipation(User user, float price) {
        PaymentParticipation participation = new PaymentParticipation();
        participation.setAmount(price);
        participation.setUser(user);
        return participation;
    }

    private PaymentItem createPaymentItem(String name, int count, float price, User... users) {
        Set<User> consumers = new HashSet<User>();
        PaymentItem item = new PaymentItem();
        final Product product = new Product();
        product.setName(name);
        item.setProduct(product);
        item.setCount(count);
        item.setPrice(price);
        for (User user : users) {
            consumers.add(user);
        }
        item.setConsumers(consumers);
        return item;
    }

}
