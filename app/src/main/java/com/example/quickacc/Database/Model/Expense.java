package com.example.quickacc.Database.Model;

import java.util.Calendar;

public class Expense {

    private int expenseId;

    //automatic dateStamp
    private int expenseYear;
    private int expenseMonth;
    private int expenseDay;
    private String expenseDayName;
    private String expenseMonthName;

    //retrieved values by input
    private String expenseDescription;
    private double expenseAmount;

    //retrieved values otherwise
    private boolean executedExpense;
    private String expenseType;

    public Expense() {
    }

    public Expense(int expenseId, int expenseYear, int expenseMonth, int expenseDay,
                   String expenseDayName, String expenseMonthName, String expenseDescription,
                   double expenseAmount, boolean executedExpense, String expenseType) {
        this.expenseId = expenseId;
        this.expenseYear = expenseYear;
        this.expenseMonth = expenseMonth;
        this.expenseDay = expenseDay;
        this.expenseDayName = expenseDayName;
        this.expenseMonthName = expenseMonthName;
        this.expenseDescription = expenseDescription;
        this.expenseAmount = expenseAmount;
        this.executedExpense = executedExpense;
        this.expenseType = expenseType;
    }

    public Expense(int expenseYear, int expenseMonth, int expenseDay, String expenseDayName,
                   String expenseMonthName, String expenseDescription, double expenseAmount,
                   boolean executedExpense, String expenseType) {
        this.expenseYear = expenseYear;
        this.expenseMonth = expenseMonth;
        this.expenseDay = expenseDay;
        this.expenseDayName = expenseDayName;
        this.expenseMonthName = expenseMonthName;
        this.expenseDescription = expenseDescription;
        this.expenseAmount = expenseAmount;
        this.executedExpense = executedExpense;
        this.expenseType = expenseType;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getExpenseYear() {
        return expenseYear;
    }

    public void setExpenseYear(int expenseYear) {
        this.expenseYear = expenseYear;
    }

    public int getExpenseMonth() {
        return expenseMonth;
    }

    public void setExpenseMonth(int expenseMonth) {
        this.expenseMonth = expenseMonth;
    }

    public int getExpenseDay() {
        return expenseDay;
    }

    public void setExpenseDay(int expenseDay) {
        this.expenseDay = expenseDay;
    }

    public String getExpenseDayName() {
        return expenseDayName;
    }

    public void setExpenseDayName(String expenseDayName) {
        this.expenseDayName = expenseDayName;
    }

    public String getExpenseMonthName() {
        return expenseMonthName;
    }

    public void setExpenseMonthName(String expenseMonthName) {
        this.expenseMonthName = expenseMonthName;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public boolean isExecutedExpense() {
        return executedExpense;
    }

    public void setExecutedExpense(boolean executedExpense) {
        this.executedExpense = executedExpense;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }
}
