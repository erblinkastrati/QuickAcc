package com.example.quickacc.RecyclerViewInShowPendingExpenseAlertDialog;

public class ExpenseItem {

    private int expenseReId;
    private String expenseDescription;
    private String expenseAmount;
    private String expectedExpenseDate;

    public ExpenseItem(int expenseId, String expenseDescription, String expenseAmount, String expectedExpenseDate) {
        this.expenseReId = expenseId;
        this.expenseDescription = expenseDescription;
        this.expenseAmount = expenseAmount;
        this.expectedExpenseDate = expectedExpenseDate;
    }

    public ExpenseItem() {
    }


    public int getExpenseId() {
        return expenseReId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseReId = expenseId;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpectedExpenseDate() {
        return expectedExpenseDate;
    }

    public void setExpectedExpenseDate(String expectedExpenseDate) {
        this.expectedExpenseDate = expectedExpenseDate;
    }
}
