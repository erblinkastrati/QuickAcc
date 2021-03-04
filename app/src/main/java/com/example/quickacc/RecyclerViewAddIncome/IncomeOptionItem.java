package com.example.quickacc.RecyclerViewAddIncome;

public class IncomeOptionItem {

    private int incomeReActId;
    private String incomeStreamType;
    private String specificIncomeAmount;

    public IncomeOptionItem(int incomeReActId, String text, String specIncA) {
        this.incomeReActId = incomeReActId;
        this.incomeStreamType = text;
        specificIncomeAmount = specIncA;
    }

    public IncomeOptionItem() {
    }

    public int getIncomeReActId() {
        return incomeReActId;
    }

    public void setIncomeReActId(int incomeReActId) {
        this.incomeReActId = incomeReActId;
    }

    public String getIncomeStreamType() {
        return incomeStreamType;
    }

    public void setIncomeStreamType(String text) {
        this.incomeStreamType = text;
    }

    public String getSpecificIncomeAmount() {
        return specificIncomeAmount;
    }

    public void setSpecificIncomeAmount(String specificIncomeAmount) {
        this.specificIncomeAmount = specificIncomeAmount;
    }
}
