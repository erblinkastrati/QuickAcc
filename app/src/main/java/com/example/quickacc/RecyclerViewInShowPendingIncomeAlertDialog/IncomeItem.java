package com.example.quickacc.RecyclerViewInShowPendingIncomeAlertDialog;

public class IncomeItem {

    private int incomeReId;
    private String incomeDescription;
    private String incomeAmount;
    private String expectedDate;

    public IncomeItem(int id, String incomeDescription, String incomeAmount, String expectedDate) {
        this.incomeReId = id;
        this.incomeDescription = incomeDescription;
        this.incomeAmount = incomeAmount;
        this.expectedDate = expectedDate;
    }

    public IncomeItem() {
    }

    public int getIncomeReId() {
        return incomeReId;
    }

    public void setIncomeReId(int incomeReId) {
        this.incomeReId = incomeReId;
    }

    public String getIncomeDescription() {
        return incomeDescription;
    }

    public void setIncomeDescription(String incomeDescription) {
        this.incomeDescription = incomeDescription;
    }

    public String getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(String incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }
}
