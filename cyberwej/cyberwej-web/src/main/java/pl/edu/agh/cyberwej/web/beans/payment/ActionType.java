package pl.edu.agh.cyberwej.web.beans.payment;

public enum ActionType {

    STEP("Step 2"), ADDPAYMENTITEMS("Add payment items");

    private String description;

    private ActionType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    };
}
