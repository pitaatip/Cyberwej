package pl.edu.agh.cyberwej.common.objects.service;

import pl.edu.agh.cyberwej.data.objects.Payment;

/**
 * 
 * @author Krzysztof
 * 
 */
public class PaymentInformation {

    private Payment payment;

    private float amount;

    private int participantsCount;

    /**
     * @return the payment
     */
    public Payment getPayment() {
        return this.payment;
    }

    /**
     * @param payment
     *            the payment to set
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * @return the amount
     */
    public float getAmount() {
        return this.amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(float amount) {
        this.amount = amount;
    }

    /**
     * @return the participantsCount
     */
    public int getParticipantsCount() {
        return this.participantsCount;
    }

    /**
     * @param participantsCount
     *            the participantsCount to set
     */
    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

}
