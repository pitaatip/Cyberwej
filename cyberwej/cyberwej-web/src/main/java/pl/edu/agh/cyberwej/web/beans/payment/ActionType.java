package pl.edu.agh.cyberwej.web.beans.payment;

public enum ActionType {

    STEP("Step 2"), ADDPAYMENTITEMS("Add payment items"), ADDPAYERS(
            "Add payers"), STEP3("Step 3");

    private String description;

    private ActionType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    };
}
