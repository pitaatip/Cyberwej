package pl.edu.agh.cyberwej.common.objects.service;

import pl.edu.agh.cyberwej.data.objects.User;

/**
 * @author Krzysztof
 */
public class ParticipantInformation {
    private User user;

    private int consumedItemsCount = 0;

    private float status;

    private Float amount = null;

    /**
     * @return the consumedItemsCount
     */
    public int getConsumedItemsCount() {
        return this.consumedItemsCount;
    }

    /**
     * @param consumedItemsCount
     *            the consumedItemsCount to set
     */
    public void setConsumedItemsCount(int consumedItemsCount) {
        this.consumedItemsCount = consumedItemsCount;
    }

    /**
     * @return the status
     */
    public float getStatus() {
        return this.status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(float status) {
        this.status = status;
    }

    public void incrementConsumedItemsCount() {
        this.consumedItemsCount++;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return this.user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the amount
     */
    public Float getAmount() {
        return this.amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(Float amount) {
        this.amount = amount;
    }
}