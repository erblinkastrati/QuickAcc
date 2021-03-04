package com.example.quickacc.Database.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.quickacc.Database.Model.Current;
import com.example.quickacc.Database.Model.Expense;
import com.example.quickacc.Database.Model.Income;
import com.example.quickacc.Database.Model.Saving;
import com.example.quickacc.Database.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_INCOME_TABLE = "create table "
                                        + Util.TABLE_NAME_INCOME
                                        + "("   + Util.COLUMN_INCOME_ID + " integer primary key,"
                                                + Util.COLUMN_INCOME_MONTH + " integer,"
                                                + Util.COLUMN_INCOME_YEAR + " integer,"
                                                + Util.COLUMN_INCOME_DAY + " integer,"
                                                + Util.COLUMN_INCOME_DAY_NAME + " text,"
                                                + Util.COLUMN_INCOME_MONTH_NAME + " text,"
                                                + Util.COLUMN_INCOME_DESCRIPTION + " text,"
                                                + Util.COLUMN_INCOME_AMOUNT + " real,"
                                                + Util.COLUMN_INCOME_TYPE + " text,"
                                                + Util.COLUMN_INCOME_EXECUTED + " integer"
                                        + ");";

        String CREATE_EXPENSE_TABLE = "create table "
                                        + Util.TABLE_NAME_EXPENSE
                                        + "("   + Util.COLUMN_EXPENSE_ID + " integer primary key,"
                                                + Util.COLUMN_EXPENSE_MONTH + " integer,"
                                                + Util.COLUMN_EXPENSE_YEAR + " integer,"
                                                + Util.COLUMN_EXPENSE_DAY + " integer,"
                                                + Util.COLUMN_EXPENSE_DAY_NAME + " text,"
                                                + Util.COLUMN_EXPENSE_MONTH_NAME + " text,"
                                                + Util.COLUMN_EXPENSE_DESCRIPTION + " text,"
                                                + Util.COLUMN_EXPENSE_AMOUNT + " real,"
                                                + Util.COLUMN_EXPENSE_TYPE + " text,"
                                                + Util.COLUMN_EXPENSE_EXECUTED + " integer"
                                        + ");";

        String CREATE_SAVING_TABLE = "create table "
                                        + Util.TABLE_NAME_SAVING
                                        + "("   + Util.COLUMN_SAVING_ID + " integer primary key,"
                                                + Util.COLUMN_SAVING_MONTH + " integer,"
                                                + Util.COLUMN_SAVING_YEAR + " integer,"
                                                + Util.COLUMN_SAVING_DAY + " integer,"
                                                + Util.COLUMN_SAVING_DAY_NAME + " text,"
                                                + Util.COLUMN_SAVING_MONTH_NAME + " text,"
                                                + Util.COLUMN_SAVING_DESCRIPTION + " text,"
                                                + Util.COLUMN_SAVING_AMOUNT + " real,"
                                                + Util.COLUMN_SAVING_TYPE + " text,"
                                                + Util.COLUMN_SAVING_EXECUTED + " integer"
                                        + ");";

        String CREATE_CURRENT_TABLE = "create table "
                                        + Util.TABLE_NAME_CURRENT
                                        + "("   + Util.COLUMN_CURRENT_ID + " integer primary key,"
                                                + Util.COLUMN_CURRENT_IN_BANK + " integer,"
                                                + Util.COLUMN_CURRENT_IN_WALLET + " integer,"
                                                + Util.COLUMN_CURRENT_IN_OTHERS + " integer"
                                        +");";



        db.execSQL(CREATE_INCOME_TABLE);
        db.execSQL(CREATE_EXPENSE_TABLE);
        db.execSQL(CREATE_SAVING_TABLE);
        db.execSQL(CREATE_CURRENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //delete
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME_SAVING);
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME_CURRENT);

        //recreate
        onCreate(db);

    }

    /**
     * CRUD Operations:
     */


    //Necessary ops for current table



    public void addCurrentInfosForFirstTime(Current current) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(Util.COLUMN_CURRENT_IN_BANK, current.getBank());
        value.put(Util.COLUMN_CURRENT_IN_WALLET, current.getWallet());
        value.put(Util.COLUMN_CURRENT_IN_OTHERS, current.getOthers());

        db.insert(Util.TABLE_NAME_CURRENT, null, value);
        db.close();
    }

    public int getRowsCountInCurrentTable() {
        String countQuery = "select * from " + Util.TABLE_NAME_CURRENT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cursorCount = cursor.getCount();
        cursor.close();

        return cursorCount;

    }

    public int updateCurrentInfos(Current current){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(Util.COLUMN_CURRENT_IN_BANK, current.getBank());
        value.put(Util.COLUMN_CURRENT_IN_WALLET, current.getWallet());
        value.put(Util.COLUMN_CURRENT_IN_OTHERS, current.getOthers());


        return db.update(Util.TABLE_NAME_CURRENT, value, Util.COLUMN_CURRENT_ID + "=?",
                new String[]{String.valueOf(1)});
    }

    public void deleteCurrentInfos(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME_CURRENT, Util.COLUMN_CURRENT_ID + "=?",
                new String[]{String.valueOf(id)});

        db.close();

    }

    public double[] getAllCurrentlyAvailableSources() {

        SQLiteDatabase db = this.getReadableDatabase();
        double[] currentlyAvailableOutput = new double[3];
        String getQuery = "select * from " + Util.TABLE_NAME_CURRENT;

        String[] columns = new String[]{
                Util.COLUMN_CURRENT_ID,
                Util.COLUMN_CURRENT_IN_BANK,
                Util.COLUMN_CURRENT_IN_WALLET,
                Util.COLUMN_CURRENT_IN_OTHERS
        };

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(getQuery, null);


        /*
        @SuppressLint("Recycle")
        Cursor cursor = db.query(
                Util.TABLE_NAME_CURRENT,
                columns,
                Util.COLUMN_CURRENT_ID + " =? ",
                new String[]{String.valueOf(0)},
                null, null, null, null
        );

         */

        if (cursor != null) {
            cursor.moveToFirst();
        }



        double bankAmount = cursor.getDouble(1);
        double walletAmount = cursor.getDouble(2);
        double othersAmount = cursor.getDouble(3);

        currentlyAvailableOutput[0] = bankAmount;
        currentlyAvailableOutput[1] = walletAmount;
        currentlyAvailableOutput[2] = othersAmount;


        return currentlyAvailableOutput;
    }



    //ADDING

    public void addIncome(Income income) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(Util.COLUMN_INCOME_MONTH, income.getMonthOfIncome());
        value.put(Util.COLUMN_INCOME_YEAR, income.getYearOfIncome());
        value.put(Util.COLUMN_INCOME_DAY, income.getDayOfIncome());
        value.put(Util.COLUMN_INCOME_DAY_NAME, income.getDayNameOfIncome());
        value.put(Util.COLUMN_INCOME_MONTH_NAME, income.getMonthNameOfIncome());
        value.put(Util.COLUMN_INCOME_DESCRIPTION, income.getIncomeDescription());
        value.put(Util.COLUMN_INCOME_AMOUNT, income.getIncomeAmount());
        value.put(Util.COLUMN_INCOME_TYPE, income.getIncomeType());

        int boolRepresent;

        if(income.isExecutedIncome()) {
            boolRepresent = 1;
        } else {
            boolRepresent = 0;
        }

        value.put(Util.COLUMN_INCOME_EXECUTED, boolRepresent);

        db.insert(Util.TABLE_NAME_INCOME, null, value);
        db.close();
    }

    public void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(Util.COLUMN_EXPENSE_MONTH, expense.getExpenseMonth());
        value.put(Util.COLUMN_EXPENSE_YEAR, expense.getExpenseYear());
        value.put(Util.COLUMN_EXPENSE_DAY, expense.getExpenseDay());
        value.put(Util.COLUMN_EXPENSE_DAY_NAME, expense.getExpenseDayName());
        value.put(Util.COLUMN_EXPENSE_MONTH_NAME, expense.getExpenseMonthName());
        value.put(Util.COLUMN_EXPENSE_DESCRIPTION, expense.getExpenseDescription());
        value.put(Util.COLUMN_EXPENSE_AMOUNT, expense.getExpenseAmount());
        value.put(Util.COLUMN_EXPENSE_TYPE, expense.getExpenseType());
        value.put(Util.COLUMN_EXPENSE_EXECUTED, expense.isExecutedExpense());

        db.insert(Util.TABLE_NAME_EXPENSE, null, value);
        db.close();
    }

    public void addSaving(Saving saving) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(Util.COLUMN_SAVING_MONTH, saving.getSavingMonth());
        value.put(Util.COLUMN_SAVING_YEAR, saving.getSavingYear());
        value.put(Util.COLUMN_SAVING_DAY, saving.getSavingDay());
        value.put(Util.COLUMN_SAVING_DAY_NAME, saving.getSavingDayName());
        value.put(Util.COLUMN_SAVING_MONTH_NAME, saving.getSavingMonthName());
        value.put(Util.COLUMN_SAVING_DESCRIPTION, saving.getSavingDescription());
        value.put(Util.COLUMN_SAVING_AMOUNT, saving.getSavingAmount());
        value.put(Util.COLUMN_SAVING_TYPE, saving.getSavingType());
        value.put(Util.COLUMN_SAVING_EXECUTED, saving.isExecutedSaving());

        db.insert(Util.TABLE_NAME_SAVING, null, value);
        db.close();
    }

    //GETTING SINGULAR

    public Income getIncome(int incomeId) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] colums = new String[]{
                Util.COLUMN_INCOME_ID, //0
                Util.COLUMN_INCOME_MONTH, //1
                Util.COLUMN_INCOME_YEAR, //2
                Util.COLUMN_INCOME_DAY, //3
                Util.COLUMN_INCOME_DAY_NAME, //4
                Util.COLUMN_INCOME_MONTH_NAME, //5
                Util.COLUMN_INCOME_DESCRIPTION, //6
                Util.COLUMN_INCOME_AMOUNT, //7
                Util.COLUMN_INCOME_TYPE, //8
                Util.COLUMN_INCOME_EXECUTED //9
        };

        @SuppressLint("Recycle")
        Cursor cursor = db.query(
                Util.TABLE_NAME_INCOME,
                colums,
                Util.COLUMN_INCOME_ID + "=?",
                new String[]{String.valueOf(incomeId)},
                null, null, null, null
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        boolean isExecuted = Integer.parseInt(cursor.getString(9)) == 1;

        return new Income(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                Double.parseDouble(cursor.getString(7)),
                cursor.getString(8),
                isExecuted
        );
    }

    public Expense getExpense(int expenseId) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[]{
                Util.COLUMN_EXPENSE_ID, //0
                Util.COLUMN_EXPENSE_YEAR, //1
                Util.COLUMN_EXPENSE_MONTH, //2
                Util.COLUMN_EXPENSE_DAY, //3
                Util.COLUMN_EXPENSE_DAY_NAME, //4
                Util.COLUMN_EXPENSE_MONTH_NAME, //5
                Util.COLUMN_EXPENSE_DESCRIPTION, //6
                Util.COLUMN_EXPENSE_AMOUNT, //7
                Util.COLUMN_EXPENSE_TYPE, //8
                Util.COLUMN_EXPENSE_EXECUTED //9
        };

        @SuppressLint("Recycle")
        Cursor cursor = db.query(
                Util.TABLE_NAME_EXPENSE,
                columns,
                Util.COLUMN_EXPENSE_ID + "=?",
                new String[]{String.valueOf(expenseId)},
                null, null, null, null
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        boolean isExecuted = Integer.parseInt(cursor.getString(9)) == 1;

        return new Expense(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                Double.parseDouble(cursor.getString(7)),
                isExecuted,
                cursor.getString(8)
        );
    }

    public Saving getSaving(int savingId) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[]{
                Util.COLUMN_SAVING_ID, //0
                Util.COLUMN_SAVING_YEAR, //1
                Util.COLUMN_SAVING_MONTH, //2
                Util.COLUMN_SAVING_DAY, //3
                Util.COLUMN_SAVING_DAY_NAME, //4
                Util.COLUMN_SAVING_MONTH_NAME, //5
                Util.COLUMN_SAVING_DESCRIPTION, //6
                Util.COLUMN_SAVING_AMOUNT, //7
                Util.COLUMN_SAVING_TYPE, //8
                Util.COLUMN_SAVING_EXECUTED //9
        };

        @SuppressLint("Recycle")
        Cursor cursor = db.query(
                Util.TABLE_NAME_SAVING,
                columns,
                Util.COLUMN_SAVING_ID + "=?",
                new String[]{String.valueOf(savingId)},
                null, null, null, null
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        boolean isExecuted = Integer.parseInt(cursor.getString(9)) == 1;

        return new Saving(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                Double.parseDouble(cursor.getString(7)),
                isExecuted,
                cursor.getString(8)
        );
    }

    //GETTING ALL

    public List<Income> getAllIncome() {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Income> allIncome = new ArrayList<>();

        String selectAll = "select * from " + Util.TABLE_NAME_INCOME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do{
                Income income = new Income();

                boolean isExecuted = Integer.parseInt(cursor.getString(9)) == 1;

                income.setIncomeId(Integer.parseInt(cursor.getString(0)));
                income.setMonthOfIncome(Integer.parseInt(cursor.getString(1)));
                income.setYearOfIncome(Integer.parseInt(cursor.getString(2)));
                income.setDayOfIncome(Integer.parseInt(cursor.getString(3)));
                income.setDayNameOfIncome(cursor.getString(4));
                income.setMonthNameOfIncome(cursor.getString(5));
                income.setIncomeDescription(cursor.getString(6));
                income.setIncomeAmount(Double.parseDouble(cursor.getString(7)));
                income.setIncomeType(cursor.getString(8));
                income.setExecutedIncome(isExecuted);

                allIncome.add(income);
            } while (cursor.moveToNext());
        }

        return allIncome;
    }

    public List<Expense> getAllExpense() {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Expense> allExpense = new ArrayList<>();

        String selectAll = "select * from " + Util.TABLE_NAME_EXPENSE;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do{
                Expense expense = new Expense();

                boolean isExecuted = Integer.parseInt(cursor.getString(9)) == 1;

                expense.setExpenseId(Integer.parseInt(cursor.getString(0)));
                expense.setExpenseYear(Integer.parseInt(cursor.getString(2)));
                expense.setExpenseMonth(Integer.parseInt(cursor.getString(1)));
                expense.setExpenseDay(Integer.parseInt(cursor.getString(3)));
                expense.setExpenseDayName(cursor.getString(4));
                expense.setExpenseMonthName(cursor.getString(5));
                expense.setExpenseDescription(cursor.getString(6));
                expense.setExpenseAmount(Double.parseDouble(cursor.getString(7)));
                expense.setExpenseType(cursor.getString(8));
                expense.setExecutedExpense(isExecuted);

                allExpense.add(expense);
            } while (cursor.moveToNext());
        }

        return allExpense;
    }

    public List<Saving> getAllSaving() {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Saving> allSaving = new ArrayList<>();

        String selectAll = "select * from " + Util.TABLE_NAME_SAVING;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do{
                Saving saving = new Saving();

                boolean isExecuted = Integer.parseInt(cursor.getString(9)) == 1;

                saving.setSavingId(Integer.parseInt(cursor.getString(0)));
                saving.setSavingYear(Integer.parseInt(cursor.getString(2)));
                saving.setSavingMonth(Integer.parseInt(cursor.getString(1)));
                saving.setSavingDay(Integer.parseInt(cursor.getString(3)));
                saving.setSavingDayName(cursor.getString(4));
                saving.setSavingMonthName(cursor.getString(5));
                saving.setSavingDescription(cursor.getString(6));
                saving.setSavingAmount(Double.parseDouble(cursor.getString(7)));
                saving.setSavingType(cursor.getString(8));
                saving.setExecutedSaving(isExecuted);

                allSaving.add(saving);
            } while (cursor.moveToNext());
        }

        return allSaving;
    }

    //GET SPECIFIC ONES ------------------------------------------------------------------------------------

    //Income

    public List<Income> getDateSpecificIncome(int monthOfIncome, int yearOfIncome) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Income> allSpecificIncome = new ArrayList<>();

        String selectAllDateSpecific = "select * from " + Util.TABLE_NAME_INCOME + " where " + Util.COLUMN_INCOME_MONTH + " = " + monthOfIncome + " and " + Util.COLUMN_INCOME_YEAR + " = " + yearOfIncome;
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectAllDateSpecific, null);

        if (cursor.moveToFirst()) {
            do{
                Income income = new Income();

                boolean isExecuted = Integer.parseInt(cursor.getString(9)) == 1;

                income.setIncomeId(Integer.parseInt(cursor.getString(0)));
                income.setMonthOfIncome(Integer.parseInt(cursor.getString(1)));
                income.setYearOfIncome(Integer.parseInt(cursor.getString(2)));
                income.setDayOfIncome(Integer.parseInt(cursor.getString(3)));
                income.setDayNameOfIncome(cursor.getString(4));
                income.setMonthNameOfIncome(cursor.getString(5));
                income.setIncomeDescription(cursor.getString(6));
                income.setIncomeAmount(Double.parseDouble(cursor.getString(7)));
                income.setIncomeType(cursor.getString(8));
                income.setExecutedIncome(isExecuted);

                allSpecificIncome.add(income);
            } while (cursor.moveToNext());
        }


        return allSpecificIncome;
    }

    public List<Income> getDateAndExecutedSpecificIncome(int monthOfIncome, int yearOfIncome, int executed) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Income> allSpecificIncome = new ArrayList<>();

        String selectAllDateSpecific = "select * from " + Util.TABLE_NAME_INCOME + " where " + Util.COLUMN_INCOME_MONTH + " = " + monthOfIncome
                + " and " + Util.COLUMN_INCOME_YEAR + " = " + yearOfIncome
                + " and " + Util.COLUMN_INCOME_EXECUTED + " = " + executed;

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectAllDateSpecific, null);

        if (cursor.moveToFirst()) {
            do{
                Income income = new Income();

                boolean isExecuted = Integer.parseInt(cursor.getString(9)) == 1;

                income.setIncomeId(Integer.parseInt(cursor.getString(0)));
                income.setMonthOfIncome(Integer.parseInt(cursor.getString(1)));
                income.setYearOfIncome(Integer.parseInt(cursor.getString(2)));
                income.setDayOfIncome(Integer.parseInt(cursor.getString(3)));
                income.setDayNameOfIncome(cursor.getString(4));
                income.setMonthNameOfIncome(cursor.getString(5));
                income.setIncomeDescription(cursor.getString(6));
                income.setIncomeAmount(Double.parseDouble(cursor.getString(7)));
                income.setIncomeType(cursor.getString(8));
                income.setExecutedIncome(isExecuted);

                allSpecificIncome.add(income);
            } while (cursor.moveToNext());
        }


        return allSpecificIncome;
    }

    //Expenses

    public List<Expense> getDateSpecificExpense(int monthOfExpense, int yearOfExpense) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Expense> allSpecificExpense = new ArrayList<>();

        String selectAllDateSpecific = "select * from " + Util.TABLE_NAME_EXPENSE + " where " + Util.COLUMN_EXPENSE_MONTH + " = " + monthOfExpense + " and " + Util.COLUMN_EXPENSE_YEAR + " = " + yearOfExpense;
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectAllDateSpecific, null);

        if (cursor.moveToFirst()) {
            do{

                Expense expense = new Expense();

                boolean isExecuted = Integer.parseInt(cursor.getString(9)) == 1;

                expense.setExpenseId(Integer.parseInt(cursor.getString(0)));
                expense.setExpenseMonth(Integer.parseInt(cursor.getString(1)));
                expense.setExpenseYear(Integer.parseInt(cursor.getString(2)));
                expense.setExpenseDay(Integer.parseInt(cursor.getString(3)));
                expense.setExpenseDayName(cursor.getString(4));
                expense.setExpenseMonthName(cursor.getString(5));
                expense.setExpenseDescription(cursor.getString(6));
                expense.setExpenseAmount(Double.parseDouble(cursor.getString(7)));
                expense.setExpenseType(cursor.getString(8));
                expense.setExecutedExpense(isExecuted);

                allSpecificExpense.add(expense);
            } while (cursor.moveToNext());
        }


        return allSpecificExpense;
    }

    public List<Expense> getDateAndExecutedSpecificExpense(int monthOfExpense, int yearOfExpense, int executed) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Expense> allSpecificExpense = new ArrayList<>();

        String selectAllDateSpecific = "select * from " + Util.TABLE_NAME_EXPENSE + " where " + Util.COLUMN_EXPENSE_MONTH + " = " + monthOfExpense
                + " and " + Util.COLUMN_EXPENSE_YEAR + " = " + yearOfExpense
                + " and " + Util.COLUMN_EXPENSE_EXECUTED + " = " + executed;

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectAllDateSpecific, null);

        if (cursor.moveToFirst()) {
            do{
                Expense expense = new Expense();

                boolean isExecuted = Integer.parseInt(cursor.getString(9)) == 1;

                expense.setExpenseId(Integer.parseInt(cursor.getString(0)));
                expense.setExpenseMonth(Integer.parseInt(cursor.getString(1)));
                expense.setExpenseYear(Integer.parseInt(cursor.getString(2)));
                expense.setExpenseDay(Integer.parseInt(cursor.getString(3)));
                expense.setExpenseDayName(cursor.getString(4));
                expense.setExpenseMonthName(cursor.getString(5));
                expense.setExpenseDescription(cursor.getString(6));
                expense.setExpenseAmount(Double.parseDouble(cursor.getString(7)));
                expense.setExpenseType(cursor.getString(8));
                expense.setExecutedExpense(isExecuted);

                allSpecificExpense.add(expense);
            } while (cursor.moveToNext());
        }


        return allSpecificExpense;
    }

    //Savings

    public List<Saving> getDateSpecificSaving(int monthOfSaving, int yearOfSaving) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Saving> allSpecificSavings = new ArrayList<>();

        String selectAllDateSpecific = "select * from " + Util.TABLE_NAME_SAVING + " where " + Util.COLUMN_SAVING_MONTH + " = " + monthOfSaving + " and " + Util.COLUMN_SAVING_YEAR + " = " + yearOfSaving;
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectAllDateSpecific, null);

        if (cursor.moveToFirst()) {
            do{

                Saving saving = new Saving();

                boolean isExecuted = Integer.parseInt(cursor.getString(9)) == 1;

                saving.setSavingId(Integer.parseInt(cursor.getString(0)));
                saving.setSavingMonth(Integer.parseInt(cursor.getString(1)));
                saving.setSavingYear(Integer.parseInt(cursor.getString(2)));
                saving.setSavingDay(Integer.parseInt(cursor.getString(3)));
                saving.setSavingDayName(cursor.getString(4));
                saving.setSavingMonthName(cursor.getString(5));
                saving.setSavingDescription(cursor.getString(6));
                saving.setSavingAmount(Double.parseDouble(cursor.getString(7)));
                saving.setSavingType(cursor.getString(8));
                saving.setExecutedSaving(isExecuted);

                allSpecificSavings.add(saving);
            } while (cursor.moveToNext());
        }


        return allSpecificSavings;
    }

    public List<Saving> getDateAndExecutedSpecificSaving(int monthOfSaving, int yearOfSaving, int executed) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Saving> allSpecificSaving = new ArrayList<>();

        String selectAllDateSpecific = "select * from " + Util.TABLE_NAME_SAVING + " where " + Util.COLUMN_SAVING_MONTH + " = " + monthOfSaving
                + " and " + Util.COLUMN_SAVING_YEAR + " = " + yearOfSaving
                + " and " + Util.COLUMN_SAVING_EXECUTED + " = " + executed;

        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectAllDateSpecific, null);

        if (cursor.moveToFirst()) {
            do{
                Saving saving = new Saving();

                boolean isExecuted = Integer.parseInt(cursor.getString(9)) == 1;

                saving.setSavingId(Integer.parseInt(cursor.getString(0)));
                saving.setSavingMonth(Integer.parseInt(cursor.getString(1)));
                saving.setSavingYear(Integer.parseInt(cursor.getString(2)));
                saving.setSavingDay(Integer.parseInt(cursor.getString(3)));
                saving.setSavingDayName(cursor.getString(4));
                saving.setSavingMonthName(cursor.getString(5));
                saving.setSavingDescription(cursor.getString(6));
                saving.setSavingAmount(Double.parseDouble(cursor.getString(7)));
                saving.setSavingType(cursor.getString(8));
                saving.setExecutedSaving(isExecuted);

                allSpecificSaving.add(saving);
            } while (cursor.moveToNext());
        }


        return allSpecificSaving;
    }



    //REMOVING SINGULAR ------------------------------------------------------------------------------------------------

    public void deleteIncome(Income income) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME_INCOME, Util.COLUMN_INCOME_ID + "=?",
                new String[]{String.valueOf(income.getIncomeId())});

        db.close();
    }

    public void deleteExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME_EXPENSE, Util.COLUMN_EXPENSE_ID + "=?",
                new String[]{String.valueOf(expense.getExpenseId())});

        db.close();
    }

    public void deleteSaving(Saving saving) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME_SAVING, Util.COLUMN_SAVING_ID + "=?",
                new String[]{String.valueOf(saving.getSavingId())});

        db.close();
    }

    //REMOVING ALL

    //UPDATING SINGULAR

    public void updateIncome(Income income) {

        SQLiteDatabase db = this.getWritableDatabase();

        int isExecuted;

        if (income.isExecutedIncome()) {
            isExecuted = 1;
        } else {
            isExecuted = 0;
        }

        ContentValues values = new ContentValues();
        values.put(Util.COLUMN_INCOME_MONTH, income.getMonthOfIncome());
        values.put(Util.COLUMN_INCOME_YEAR, income.getYearOfIncome());
        values.put(Util.COLUMN_INCOME_DAY, income.getDayOfIncome());
        values.put(Util.COLUMN_INCOME_DAY_NAME, income.getDayNameOfIncome());
        values.put(Util.COLUMN_INCOME_MONTH_NAME, income.getMonthNameOfIncome());
        values.put(Util.COLUMN_INCOME_DESCRIPTION, income.getIncomeDescription());
        values.put(Util.COLUMN_INCOME_AMOUNT, income.getIncomeAmount());
        values.put(Util.COLUMN_INCOME_TYPE, income.getIncomeType());
        values.put(Util.COLUMN_INCOME_EXECUTED, isExecuted);


        db.update(Util.TABLE_NAME_INCOME, values, Util.COLUMN_INCOME_ID + "=?",
                new String[]{String.valueOf(income.getIncomeId())});
    }

    public void updateExpense(Expense expense) {

        SQLiteDatabase db = this.getWritableDatabase();

        int isExecuted;

        if (expense.isExecutedExpense()) {
            isExecuted = 1;
        } else {
            isExecuted = 0;
        }

        ContentValues values = new ContentValues();
        values.put(Util.COLUMN_EXPENSE_YEAR, expense.getExpenseYear());
        values.put(Util.COLUMN_EXPENSE_MONTH, expense.getExpenseMonth());
        values.put(Util.COLUMN_EXPENSE_DAY, expense.getExpenseDay());
        values.put(Util.COLUMN_EXPENSE_DAY_NAME, expense.getExpenseDayName());
        values.put(Util.COLUMN_EXPENSE_MONTH_NAME, expense.getExpenseMonthName());
        values.put(Util.COLUMN_EXPENSE_DESCRIPTION, expense.getExpenseDescription());
        values.put(Util.COLUMN_EXPENSE_AMOUNT, expense.getExpenseAmount());
        values.put(Util.COLUMN_EXPENSE_TYPE, expense.getExpenseType());
        values.put(Util.COLUMN_EXPENSE_EXECUTED, isExecuted);


        db.update(Util.TABLE_NAME_EXPENSE, values, Util.COLUMN_EXPENSE_ID + "=?",
                new String[]{String.valueOf(expense.getExpenseId())});
    }

    public void updateSaving(Saving saving) {

        SQLiteDatabase db = this.getWritableDatabase();

        int isExecuted;

        if (saving.isExecutedSaving()) {
            isExecuted = 1;
        } else {
            isExecuted = 0;
        }

        ContentValues values = new ContentValues();
        values.put(Util.COLUMN_SAVING_YEAR, saving.getSavingYear());
        values.put(Util.COLUMN_SAVING_MONTH, saving.getSavingMonth());
        values.put(Util.COLUMN_SAVING_DAY, saving.getSavingDay());
        values.put(Util.COLUMN_SAVING_DAY_NAME, saving.getSavingDayName());
        values.put(Util.COLUMN_SAVING_MONTH_NAME, saving.getSavingMonthName());
        values.put(Util.COLUMN_SAVING_DESCRIPTION, saving.getSavingDescription());
        values.put(Util.COLUMN_SAVING_AMOUNT, saving.getSavingAmount());
        values.put(Util.COLUMN_SAVING_TYPE, saving.getSavingType());
        values.put(Util.COLUMN_SAVING_EXECUTED, isExecuted);

        db.update(Util.TABLE_NAME_SAVING, values, Util.COLUMN_SAVING_ID + "=?",
                new String[]{String.valueOf(saving.getSavingId())});
    }

    //GET TABLE ITEM COUNT

    public int getIncomeCount() {
        String countQuery = "select * from " + Util.TABLE_NAME_INCOME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cursorCount = cursor.getCount();
        cursor.close();

        return cursorCount;
    }

    public int getExpenseCount() {
        String countQuery = "select * from " + Util.TABLE_NAME_EXPENSE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cursorCount = cursor.getCount();
        cursor.close();

        return cursorCount;
    }

    public int getSavingCount() {
        String countQuery = "select * from " + Util.TABLE_NAME_SAVING;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cursorCount = cursor.getCount();
        cursor.close();

        return cursorCount;
    }





}
