package com.example.quickacc.RecyclerViewInShowPendingSavingAlertDialog;

public class SavingItem {

    private int savingReid;
    private String savingDescription;
    private String savingAmount;
    private String expectedSavingDate;

    public SavingItem(int id, String savingDescription, String savingAmount, String expectedSavingDate) {
        this.savingReid = id;
        this.savingDescription = savingDescription;
        this.savingAmount = savingAmount;
        this.expectedSavingDate = expectedSavingDate;
    }

    public SavingItem() {
    }

    public int getSavingReid() {
        return savingReid;
    }

    public void setSavingReid(int savingReid) {
        this.savingReid = savingReid;
    }

    public String getSavingDescription() {
        return savingDescription;
    }

    public void setSavingDescription(String savingDescription) {
        this.savingDescription = savingDescription;
    }

    public String getSavingAmount() {
        return savingAmount;
    }

    public void setSavingAmount(String savingAmount) {
        this.savingAmount = savingAmount;
    }

    public String getExpectedSavingDate() {
        return expectedSavingDate;
    }

    public void setExpectedSavingDate(String expectedSavingDate) {
        this.expectedSavingDate = expectedSavingDate;
    }
}
