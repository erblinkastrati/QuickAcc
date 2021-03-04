package com.example.quickacc.Database.Util;

public class Util {

    //Databse version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "quickAccDB.db";
    public static final String TABLE_NAME_INCOME = "incomeTable";
    public static final String TABLE_NAME_EXPENSE = "expenseTable";
    public static final String TABLE_NAME_SAVING = "savingTable";
    public static final String TABLE_NAME_CURRENT = "currentTable";


    //Table Columns
    public static final String COLUMN_INCOME_ID = "incomeId";
    public static final String COLUMN_INCOME_MONTH = "monthOfIncome";
    public static final String COLUMN_INCOME_YEAR = "yearOfIncome";
    public static final String COLUMN_INCOME_DAY = "dayOfIncome";
    public static final String COLUMN_INCOME_DAY_NAME = "dayNameOfIncome";
    public static final String COLUMN_INCOME_MONTH_NAME = "monthNameOfIncome";
    public static final String COLUMN_INCOME_DESCRIPTION = "incomeDescription";
    public static final String COLUMN_INCOME_AMOUNT = "incomeAmount";
    public static final String COLUMN_INCOME_TYPE = "incomeType";
    public static final String COLUMN_INCOME_EXECUTED = "executedIncome";

    public static final String COLUMN_EXPENSE_ID = "expenseId";
    public static final String COLUMN_EXPENSE_MONTH = "expenseMonth";
    public static final String COLUMN_EXPENSE_YEAR = "expenseYear";
    public static final String COLUMN_EXPENSE_DAY = "expenseDay";
    public static final String COLUMN_EXPENSE_DAY_NAME = "expenseDayName";
    public static final String COLUMN_EXPENSE_MONTH_NAME = "expenseMonthName";
    public static final String COLUMN_EXPENSE_DESCRIPTION = "expenseDescription";
    public static final String COLUMN_EXPENSE_AMOUNT = "expenseAmount";
    public static final String COLUMN_EXPENSE_TYPE = "expenseType";
    public static final String COLUMN_EXPENSE_EXECUTED = "executedExpense";

    public static final String COLUMN_SAVING_ID = "savingId";
    public static final String COLUMN_SAVING_YEAR = "savingYear";
    public static final String COLUMN_SAVING_MONTH = "savingMonth";
    public static final String COLUMN_SAVING_DAY = "savingDay";
    public static final String COLUMN_SAVING_DAY_NAME = "savingDayName";
    public static final String COLUMN_SAVING_MONTH_NAME = "savingMonthName";
    public static final String COLUMN_SAVING_DESCRIPTION = "savingDescription";
    public static final String COLUMN_SAVING_AMOUNT = "savingAmount";
    public static final String COLUMN_SAVING_TYPE = "savingType";
    public static final String COLUMN_SAVING_EXECUTED = "executedSaving";

    public static final String COLUMN_CURRENT_ID = "currentId";
    public static final String COLUMN_CURRENT_IN_BANK = "bank";
    public static final String COLUMN_CURRENT_IN_WALLET = "wallet";
    public static final String COLUMN_CURRENT_IN_OTHERS = "others";



}
