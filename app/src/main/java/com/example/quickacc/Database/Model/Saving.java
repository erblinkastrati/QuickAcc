package com.example.quickacc.Database.Model;

public class Saving {

    private int savingId;

    //automatic dateStamp
    private int savingYear;
    private int savingMonth;
    private int savingDay;
    private String savingDayName;
    private String savingMonthName;

    //retrieved values by input
    private String savingDescription;
    private double savingAmount;

    //retrieved values otherwise
    private boolean executedSaving;
    private String savingType;

    public Saving() {
    }

    public Saving(int savingId, int savingYear, int savingMonth, int savingDay,
                  String savingDayName, String savingMonthName, String savingDescription,
                  double savingAmount, boolean executedSaving, String savingType) {
        this.savingId = savingId;
        this.savingYear = savingYear;
        this.savingMonth = savingMonth;
        this.savingDay = savingDay;
        this.savingDayName = savingDayName;
        this.savingMonthName = savingMonthName;
        this.savingDescription = savingDescription;
        this.savingAmount = savingAmount;
        this.executedSaving = executedSaving;
        this.savingType = savingType;
    }

    public Saving(int savingYear, int savingMonth, int savingDay, String savingDayName,
                  String savingMonthName, String savingDescription, double savingAmount,
                  boolean executedSaving, String savingType) {
        this.savingYear = savingYear;
        this.savingMonth = savingMonth;
        this.savingDay = savingDay;
        this.savingDayName = savingDayName;
        this.savingMonthName = savingMonthName;
        this.savingDescription = savingDescription;
        this.savingAmount = savingAmount;
        this.executedSaving = executedSaving;
        this.savingType = savingType;
    }

    public int getSavingId() {
        return savingId;
    }

    public void setSavingId(int savingId) {
        this.savingId = savingId;
    }

    public int getSavingYear() {
        return savingYear;
    }

    public void setSavingYear(int savingYear) {
        this.savingYear = savingYear;
    }

    public int getSavingMonth() {
        return savingMonth;
    }

    public void setSavingMonth(int savingMonth) {
        this.savingMonth = savingMonth;
    }

    public int getSavingDay() {
        return savingDay;
    }

    public void setSavingDay(int savingDay) {
        this.savingDay = savingDay;
    }

    public String getSavingDayName() {
        return savingDayName;
    }

    public void setSavingDayName(String savingDayName) {
        this.savingDayName = savingDayName;
    }

    public String getSavingMonthName() {
        return savingMonthName;
    }

    public void setSavingMonthName(String savingMonthName) {
        this.savingMonthName = savingMonthName;
    }

    public String getSavingDescription() {
        return savingDescription;
    }

    public void setSavingDescription(String savingDescription) {
        this.savingDescription = savingDescription;
    }

    public double getSavingAmount() {
        return savingAmount;
    }

    public void setSavingAmount(double savingAmount) {
        this.savingAmount = savingAmount;
    }

    public boolean isExecutedSaving() {
        return executedSaving;
    }

    public void setExecutedSaving(boolean executedSaving) {
        this.executedSaving = executedSaving;
    }

    public String getSavingType() {
        return savingType;
    }

    public void setSavingType(String savingType) {
        this.savingType = savingType;
    }
}
