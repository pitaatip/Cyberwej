package pl.edu.agh.cyberwej.business.services.impl;

import org.agh.iosr.cyberwej.data.dao.interfaces.PaymentDAO;
import org.agh.iosr.cyberwej.data.objects.Payment;
import org.agh.iosr.cyberwej.data.objects.PaymentItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.agh.cyberwej.business.services.api.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDAO paymentDAO;

    @Override
    public float getPaymentCost(Payment payment) {
        float result = 0.0f;
        for (PaymentItem paymentItem : payment.getPaymentItems())
            result += paymentItem.getPrice() * paymentItem.getCount();
        return result;
    }

}
