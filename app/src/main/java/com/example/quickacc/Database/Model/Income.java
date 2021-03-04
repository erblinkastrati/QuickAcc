package com.example.quickacc.Database.Model;

public class Income {

    private int incomeId;

    //automatic dateStamp
    private int monthOfIncome;
    private int yearOfIncome;
    private int dayOfIncome;
    private String dayNameOfIncome;
    private String monthNameOfIncome;

    //retrieved values by input
    private String incomeDescription;
    private double incomeAmount;

    //retrieved values otherwise
    private String incomeType; //one-time or recurring
    private boolean executedIncome;

    public Income(int incomeId, int monthOfIncome, int yearOfIncome,
                  int dayOfIncome, String dayNameOfIncome, String monthNameOfIncome,
                  String incomeDescription, double incomeAmount, String incomeType,
                  boolean executedIncome) {
        this.incomeId = incomeId;
        this.monthOfIncome = monthOfIncome;
        this.yearOfIncome = yearOfIncome;
        this.dayOfIncome = dayOfIncome;
        this.dayNameOfIncome = dayNameOfIncome;
        this.monthNameOfIncome = monthNameOfIncome;
        this.incomeDescription = incomeDescription;
        this.incomeAmount = incomeAmount;
        this.incomeType = incomeType;
        this.executedIncome = executedIncome;
    }

    public Income(int monthOfIncome, int yearOfIncome, int dayOfIncome, String dayNameOfIncome,
                  String monthNameOfIncome, String incomeDescription, double incomeAmount,
                  String incomeType, boolean executedIncome) {
        this.monthOfIncome = monthOfIncome;
        this.yearOfIncome = yearOfIncome;
        this.dayOfIncome = dayOfIncome;
        this.dayNameOfIncome = dayNameOfIncome;
        this.monthNameOfIncome = monthNameOfIncome;
        this.incomeDescription = incomeDescription;
        this.incomeAmount = incomeAmount;
        this.incomeType = incomeType;
        this.executedIncome = executedIncome;
    }

    public Income() {
    }

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public int getMonthOfIncome() {
        return monthOfIncome;
    }

    public void setMonthOfIncome(int monthOfIncome) {
        this.monthOfIncome = monthOfIncome;
    }

    public int getYearOfIncome() {
        return yearOfIncome;
    }

    public void setYearOfIncome(int yearOfIncome) {
        this.yearOfIncome = yearOfIncome;
    }

    public int getDayOfIncome() {
        return dayOfIncome;
    }

    public void setDayOfIncome(int dayOfIncome) {
        this.dayOfIncome = dayOfIncome;
    }

    public String getDayNameOfIncome() {
        return dayNameOfIncome;
    }

    public void setDayNameOfIncome(String dayNameOfIncome) {
        this.dayNameOfIncome = dayNameOfIncome;
    }

    public String getMonthNameOfIncome() {
        return monthNameOfIncome;
    }

    public void setMonthNameOfIncome(String monthNameOfIncome) {
        this.monthNameOfIncome = monthNameOfIncome;
    }

    public String getIncomeDescription() {
        return incomeDescription;
    }

    public void setIncomeDescription(String incomeDescription) {
        this.incomeDescription = incomeDescription;
    }

    public double getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(double incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public boolean isExecutedIncome() {
        return executedIncome;
    }

    public void setExecutedIncome(boolean executedIncome) {
        this.executedIncome = executedIncome;
    }
}
