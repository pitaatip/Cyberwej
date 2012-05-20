package pl.edu.agh.cyberwej.web.beans.payment;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.cyberwej.data.objects.PaymentItem;
import pl.edu.agh.cyberwej.data.objects.User;

/**
 * 
 * @author Krzysztof
 * 
 */
public class PaymentItemWrapper extends PaymentItem {

    public PaymentItemWrapper(PaymentItem paymentItem) {
        super();
        setConsumers(paymentItem.getConsumers());
        setCount(paymentItem.getCount());
        setId(paymentItem.getId());
        setPrice(paymentItem.getPrice());
        setProduct(paymentItem.getProduct());
    }

    public List<User> getConsumersList() {
        return new LinkedList<User>(super.getConsumers());
    }

}
