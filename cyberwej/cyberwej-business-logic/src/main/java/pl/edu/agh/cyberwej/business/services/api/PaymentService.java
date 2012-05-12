package pl.edu.agh.cyberwej.business.services.api;

import org.agh.iosr.cyberwej.data.objects.Payment;

public interface PaymentService {

    public float getPaymentCost(Payment payment);
}
