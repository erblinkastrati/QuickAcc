package com.example.quickacc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickacc.Database.Data.DatabaseHandler;
import com.example.quickacc.Database.Model.Expense;
import com.example.quickacc.Database.Model.Income;
import com.example.quickacc.Database.Model.Saving;
import com.example.quickacc.Fragments.HistoryFragment;
import com.example.quickacc.Fragments.HomeFragment;
import com.example.quickacc.Fragments.SavingsFragment;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView availableAmount;
    private TextView currentAmount;
    private TextView pendingIncomeAmount;
    private TextView pendingExpensesAmount;

    private DrawerLayout drawer;

    //date info instance variables
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private String currentMonthName;
    private String currentDayName;

    private static final String PRE_TAG_FOR_LOGS = "dope";


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup(savedInstanceState);

        DatabaseHandler db = new DatabaseHandler(this);

        //Log.d("Insert", "Inserting this sheaat.......


        //TODO: Make methods for the printing to log logic down below ----------------
        //Reading income and show in log
        Log.d(PRE_TAG_FOR_LOGS + "infoIncomeCount", String.valueOf(db.getIncomeCount()));
        Log.d(PRE_TAG_FOR_LOGS + "infoIncomeReading", "Reading income ...");
        List<Income> incomeList = db.getAllIncome();

        for (Income i : incomeList) {

            String log = "ID: " + i.getIncomeId()
                            + ", Year: " + i.getYearOfIncome()
                            + ", Month: " + i.getMonthOfIncome() + " (" + i.getMonthNameOfIncome() + ")"
                            + ", Day: " + i.getDayOfIncome() + " (" + i.getDayNameOfIncome() + ")"
                            + ", Description: " + i.getIncomeDescription()
                            + ", Amount: " + i.getIncomeAmount()
                            + ", Type: " + i.getIncomeType()
                            + ", has been obtained: " + i.isExecutedIncome();

            Log.d(PRE_TAG_FOR_LOGS +"infoIncome", log);

        }


        //Reading expenses and showing in log
        Log.d(PRE_TAG_FOR_LOGS +"infoExpenseCount", String.valueOf(db.getExpenseCount()));
        Log.d(PRE_TAG_FOR_LOGS +"infoExpenseReading", "Reading expenses...");
        List<Expense> expensesList = db.getAllExpense();

        for (Expense i : expensesList) {

            String log = "ID: " + i.getExpenseId()
                    + ", Year: " + i.getExpenseYear()
                    + ", Month: " + i.getExpenseMonth() + " (" + i.getExpenseMonthName() + ")"
                    + ", Day: " + i.getExpenseDay() + " (" + i.getExpenseDayName() + ")"
                    + ", Description: " + i.getExpenseDescription()
                    + ", Amount: " + i.getExpenseAmount()
                    + ", Type: " + i.getExpenseType()
                    + ", has been deducted: " + i.isExecutedExpense();

            Log.d(PRE_TAG_FOR_LOGS +"infoExpense", log);

        }

        //Reading savings and showing in log
        Log.d(PRE_TAG_FOR_LOGS +"infoSavingCount", String.valueOf(db.getSavingCount()));
        Log.d(PRE_TAG_FOR_LOGS +"infoSavingReading", "Reading savings...");
        List<Saving> savingList = db.getAllSaving();

        for (Saving i : savingList) {

            String log = "ID: " + i.getSavingId()
                    + ", Year: " + i.getSavingYear()
                    + ", Month: " + i.getSavingMonth() + " (" + i.getSavingMonthName() + ")"
                    + ", Day: " + i.getSavingDay() + " (" + i.getSavingDayName() + ")"
                    + ", Description: " + i.getSavingDescription()
                    + ", Amount: " + i.getSavingAmount()
                    + ", Type: " + i.getSavingType()
                    + ", has been deducted: " + i.isExecutedSaving();

            Log.d(PRE_TAG_FOR_LOGS +"infoSaving", log);

        }

        if (db.getRowsCountInCurrentTable() != 0) {
            double[] allCurrentlyAvailableSources = db.getAllCurrentlyAvailableSources();

            double bank = allCurrentlyAvailableSources[0];
            double wallet = allCurrentlyAvailableSources[1];
            double others = allCurrentlyAvailableSources[2];

            Log.d(PRE_TAG_FOR_LOGS + "CurrentAmountCount", String.valueOf(db.getRowsCountInCurrentTable()));
            Log.d(PRE_TAG_FOR_LOGS + "CurrentAmounts", "Bank: " + bank + ",Wallet: " + wallet + ", Others: " + others);
        }

    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_info_and_calendar_menu, menu);
        return true;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.infoId) {
            showInfoAlertDialog();
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoryFragment()).commit();
                break;
            case R.id.nav_savings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SavingsFragment()).commit();
                break;
            case R.id.nav_income:
                startActivity(new Intent(MainActivity.this, IncomeActivity.class));
                break;
            case R.id.nav_expenses:
                startActivity(new Intent(MainActivity.this, ExpensesActivity.class));
                break;
            case R.id.nav_help_and_feedback:
                Toast.makeText(this, "Opens Help and Feedback Section", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                Toast.makeText(this, "Opens About Section", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    private void setup(Bundle savedInstanceState){
        availableAmount = findViewById(R.id.availableTextViewID);
        currentAmount = findViewById(R.id.currentTextViewID);
        pendingIncomeAmount = findViewById(R.id.pendingIncomeTextViewID);
        pendingExpensesAmount = findViewById(R.id.pendingExpenseTextViewID);

        getDateInfo();

        Toolbar toolbar = findViewById(R.id.toolbarID);
        toolbar.setTitleTextColor(Color.parseColor("#2C445E"));
        toolbar.setTitle(currentMonthName);
        toolbar.setElevation(20);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void getDateInfo() {
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        currentMonthName = new SimpleDateFormat("MMMM").format(calendar.getTime());
        currentDayName = new SimpleDateFormat("EEEE").format(calendar.getTime());
    }

    private void showInfoAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        View customLayout= getLayoutInflater().inflate(R.layout.info_alertdialog_homefragment, null);
        builder.setView(customLayout);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }


    // Income objects to copy when needed

    /*
        Income income = new Income();
        income.setMonthOfIncome(1);
        income.setYearOfIncome(currentYear);
        income.setDayOfIncome(currentDay);
        income.setDayNameOfIncome(currentDayName);
        income.setMonthNameOfIncome(currentMonthName);
        income.setIncomeDescription("GoStudent");
        income.setIncomeAmount(740.0);
        income.setIncomeType("recurring");
        income.setExecutedIncome(false);

        db.addIncome(income);



        Income income1 = new Income();
        income1.setMonthOfIncome(1);
        income1.setYearOfIncome(currentYear);
        income1.setDayOfIncome(currentDay);
        income1.setDayNameOfIncome(currentDayName);
        income1.setMonthNameOfIncome(currentMonthName);
        income1.setIncomeDescription("Peek & Cloppenburg");
        income1.setIncomeAmount(1100.0);
        income1.setIncomeType("recurring");
        income1.setExecutedIncome(true);

        db.addIncome(income1);

        Income income2 = new Income();
        income2.setMonthOfIncome(12);
        income2.setYearOfIncome(2020);
        income2.setDayOfIncome(currentDay);
        income2.setDayNameOfIncome(currentDayName);
        income2.setMonthNameOfIncome(currentMonthName);
        income2.setIncomeDescription("Ikea");
        income2.setIncomeAmount(7430.0);
        income2.setIncomeType("recurring");
        income2.setExecutedIncome(true);

        db.addIncome(income2);



        Income income3 = new Income();
        income3.setMonthOfIncome(12);
        income3.setYearOfIncome(2020);
        income3.setDayOfIncome(currentDay);
        income3.setDayNameOfIncome(currentDayName);
        income3.setMonthNameOfIncome(currentMonthName);
        income3.setIncomeDescription("Nespresso");
        income3.setIncomeAmount(41100.0);
        income3.setIncomeType("recurring");
        income3.setExecutedIncome(false);

        db.addIncome(income3);

        Income income4 = new Income();
        income4.setMonthOfIncome(currentMonth);
        income4.setYearOfIncome(currentYear);
        income4.setDayOfIncome(currentDay);
        income4.setDayNameOfIncome(currentDayName);
        income4.setMonthNameOfIncome(currentMonthName);
        income4.setIncomeDescription("OVB");
        income4.setIncomeAmount(7660.0);
        income4.setIncomeType("recurring");
        income4.setExecutedIncome(false);

        db.addIncome(income4);



        Income income5 = new Income();
        income5.setMonthOfIncome(currentMonth);
        income5.setYearOfIncome(currentYear);
        income5.setDayOfIncome(currentDay);
        income5.setDayNameOfIncome(currentDayName);
        income5.setMonthNameOfIncome(currentMonthName);
        income5.setIncomeDescription("The Event Company");
        income5.setIncomeAmount(87100.0);
        income5.setIncomeType("recurring");
        income5.setExecutedIncome(true);

        db.addIncome(income5);

        Income income = new Income();
        income.setMonthOfIncome(1);
        income.setYearOfIncome(currentYear);
        income.setDayOfIncome(currentDay);
        income.setDayNameOfIncome(currentDayName);
        income.setMonthNameOfIncome(currentMonthName);
        income.setIncomeDescription("GoStudent");
        income.setIncomeAmount(740.0);
        income.setIncomeType("recurring");
        income.setExecutedIncome(false);
        db.addIncome(income);

        Income income1 = new Income();
        income1.setMonthOfIncome(1);
        income1.setYearOfIncome(currentYear);
        income1.setDayOfIncome(currentDay);
        income1.setDayNameOfIncome(currentDayName);
        income1.setMonthNameOfIncome(currentMonthName);
        income1.setIncomeDescription("Beihilfe");
        income1.setIncomeAmount(500.0);
        income1.setIncomeType("recurring");
        income1.setExecutedIncome(false);
        db.addIncome(income1);

        Expense expense = new Expense();
        expense.setExpenseMonth(currentMonth);
        expense.setExpenseYear(currentYear);
        expense.setExpenseDay(currentDay);
        expense.setExpenseDayName(currentDayName);
        expense.setExpenseMonthName(currentMonthName);
        expense.setExpenseDescription("stupid shit");
        expense.setExpenseAmount(200);
        expense.setExpenseType("recurring");
        expense.setExecutedExpense(false);
        db.addExpense(expense);

        Expense expense1 = new Expense();
        expense1.setExpenseMonth(currentMonth);
        expense1.setExpenseYear(currentYear);
        expense1.setExpenseDay(currentDay);
        expense1.setExpenseDayName(currentDayName);
        expense1.setExpenseMonthName(currentMonthName);
        expense1.setExpenseDescription("more stupid shit");
        expense1.setExpenseAmount(350);
        expense1.setExpenseType("one-time");
        expense1.setExecutedExpense(false);
        db.addExpense(expense1);


        Saving saving = new Saving();
        saving.setSavingMonth(currentMonth);
        saving.setSavingYear(currentYear);
        saving.setSavingDay(currentDay);
        saving.setSavingDayName(currentDayName);
        saving.setSavingMonthName(currentMonthName);
        saving.setSavingDescription("For Monitor");
        saving.setSavingAmount(200);
        saving.setSavingType("recurring");
        saving.setExecutedSaving(false);
        db.addSaving(saving);

        Saving saving1 = new Saving();
        saving1.setSavingMonth(currentMonth);
        saving1.setSavingYear(currentYear);
        saving1.setSavingDay(currentDay);
        saving1.setSavingDayName(currentDayName);
        saving1.setSavingMonthName(currentMonthName);
        saving1.setSavingDescription("To Invest");
        saving1.setSavingAmount(100);
        saving1.setSavingType("recurring");
        saving1.setExecutedSaving(false);
        db.addSaving(saving1);

        */
}