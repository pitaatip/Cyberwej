package pl.edu.agh.cyberwej.common.objects.service;

import pl.edu.agh.cyberwej.data.objects.Group;

public class GroupInformation {

    private Group group;

    private int membersCount;

    private int paymentsCount;

    /**
     * @return the group
     */
    public Group getGroup() {
        return this.group;
    }

    /**
     * @param group
     *            the group to set
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * @return the membersCount
     */
    public int getMembersCount() {
        return this.membersCount;
    }

    /**
     * @param membersCount
     *            the membersCount to set
     */
    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }

    /**
     * @return the paymentsCount
     */
    public int getPaymentsCount() {
        return this.paymentsCount;
    }

    /**
     * @param paymentsCount
     *            the paymentsCount to set
     */
    public void setPaymentsCount(int paymentsCount) {
        this.paymentsCount = paymentsCount;
    }

}
