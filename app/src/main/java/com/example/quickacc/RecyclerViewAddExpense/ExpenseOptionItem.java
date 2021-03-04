package com.example.quickacc.RecyclerViewAddExpense;

public class ExpenseOptionItem {

    private int expenseReActId;
    private String expenseType;
    private String specificExpenseAmount;

    public ExpenseOptionItem(int expenseReActId, String text, String specIncA) {
        this.expenseReActId = expenseReActId;
        this.expenseType = text;
        specificExpenseAmount = specIncA;
    }

    public ExpenseOptionItem() {
    }

    public int getExpenseReActId() {
        return expenseReActId;
    }

    public void setExpenseReActId(int expenseReActId) {
        this.expenseReActId = expenseReActId;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String text) {
        this.expenseType = text;
    }

    public String getSpecificExpenseAmount() {
        return specificExpenseAmount;
    }

    public void setSpecificExpenseAmount(String specificExpenseAmount) {
        this.specificExpenseAmount = specificExpenseAmount;
    }
}
