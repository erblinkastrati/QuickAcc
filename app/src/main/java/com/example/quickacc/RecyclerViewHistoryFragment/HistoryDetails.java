package com.example.quickacc.RecyclerViewHistoryFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.quickacc.Database.Data.DatabaseHandler;
import com.example.quickacc.R;
import com.example.quickacc.RecyclerViewHistoryFragment.HistoryDetailsRecyclerViews.ExpenseHistoryAdapterInDetails;
import com.example.quickacc.RecyclerViewHistoryFragment.HistoryDetailsRecyclerViews.ExpenseHistoryDetailsItem;
import com.example.quickacc.RecyclerViewHistoryFragment.HistoryDetailsRecyclerViews.IncomeHistoryAdapterInDetails;
import com.example.quickacc.RecyclerViewHistoryFragment.HistoryDetailsRecyclerViews.IncomeHistoryDetailsItem;
import com.example.quickacc.RecyclerViewHistoryFragment.HistoryDetailsRecyclerViews.SavingHistoryAdapterInDetails;
import com.example.quickacc.RecyclerViewHistoryFragment.HistoryDetailsRecyclerViews.SavingHistoryDetailsItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class HistoryDetails extends AppCompatActivity {

    private TextView monthTitleInHistoryDetails;
    private TextView yearTitleInHistoryDetails;

    private TextView allIncomeAmountTV;
    private TextView allExpenseAmountTV;
    private TextView allSavingAmountTV;
    private TextView endAmountOfCertainMonthTV;

    private double allIncomeAmount = 0;
    private double allExpenseAmount = 0;
    private double allSavingAmount = 0;
    private double endAmountOfCertainMonth = 0;

    private RecyclerView incomeRecyclerView;
    private RecyclerView expenseRecyclerView;
    private RecyclerView savingRecyclerView;

    private IncomeHistoryAdapterInDetails incomeItemAdapter;
    private ExpenseHistoryAdapterInDetails expenseItemAdapter;
    private SavingHistoryAdapterInDetails savingItemAdapter;

    private List<IncomeHistoryDetailsItem> incomeListItems;
    private List<ExpenseHistoryDetailsItem> expenseListItems;
    private List<SavingHistoryDetailsItem> savingListItems;

    private String monthName = null;
    private String year = null;
    private String month = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        setupUI();

        Bundle extras = getIntent().getExtras();
        DatabaseHandler db = new DatabaseHandler(this);


        if (extras != null) {
            monthName = extras.getString("month_name");
            year = extras.getString("year");
            month = extras.getString("month");
        }

        monthTitleInHistoryDetails.setText(monthName);
        yearTitleInHistoryDetails.setText(year);

        incomeRecyclerView = findViewById(R.id.recyclerViewIncomeInHistoryId);
        expenseRecyclerView = findViewById(R.id.recyclerViewExpenseInHistoryId);
        savingRecyclerView = findViewById(R.id.recyclerViewSavingInHistoryId);

        incomeListItems = new ArrayList<>();
        expenseListItems = new ArrayList<>();
        savingListItems = new ArrayList<>();

        incomeRecyclerView.setHasFixedSize(true);
        expenseRecyclerView.setHasFixedSize(true);
        savingRecyclerView.setHasFixedSize(true);

        incomeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        savingRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        for (int i = 1; i < db.getIncomeCount() + 1; i++) {

            boolean isSameMonth = db.getIncome(i).getMonthNameOfIncome().equals(monthName);
            boolean isSameYear = db.getIncome(i).getYearOfIncome() == Integer.parseInt(year);

            if (isSameMonth && isSameYear) {

                String description = db.getIncome(i).getIncomeDescription();
                String amount = String.valueOf(db.getIncome(i).getIncomeAmount());
                String date = "Added on: " + db.getIncome(i).getDayOfIncome() + "." + db.getIncome(i).getMonthOfIncome() + "." + db.getIncome(i).getYearOfIncome();


                IncomeHistoryDetailsItem item = new IncomeHistoryDetailsItem(description, amount, date);
                incomeListItems.add(item);
                allIncomeAmount += db.getIncome(i).getIncomeAmount();

            }
        }

        for (int i = 1; i < db.getExpenseCount() + 1; i++) {

            boolean isSameMonth = db.getExpense(i).getExpenseMonthName().equals(monthName);
            boolean isSameYear = db.getExpense(i).getExpenseYear() == Integer.parseInt(year);

            Log.d("dopeExpenseYear", String.valueOf(db.getExpense(i).getExpenseYear()));
            Log.d("dopeGeneralYear", String.valueOf(year));
            Log.d("dopeIsSameMonthExpense", String.valueOf(isSameMonth));
            Log.d("dopeIsSameYearExpense", String.valueOf(isSameYear));

            if (isSameMonth && isSameYear) {

                String description = db.getExpense(i).getExpenseDescription();
                String amount = String.valueOf(db.getExpense(i).getExpenseAmount());
                String date = "Added on: " + db.getExpense(i).getExpenseDay() + "." + db.getExpense(i).getExpenseMonth() + "." + db.getExpense(i).getExpenseYear();


                ExpenseHistoryDetailsItem item = new ExpenseHistoryDetailsItem(description, amount, date);
                expenseListItems.add(item);
                allExpenseAmount += db.getExpense(i).getExpenseAmount();

            }
        }

        for (int i = 1; i < db.getSavingCount() + 1; i++) {

            boolean isSameMonth = db.getSaving(i).getSavingMonthName().equals(monthName);
            boolean isSameYear = db.getSaving(i).getSavingYear() == Integer.parseInt(year);

            if (isSameMonth && isSameYear) {

                String description = db.getSaving(i).getSavingDescription();
                String amount = String.valueOf(db.getSaving(i).getSavingAmount());
                String date = db.getSaving(i).getSavingDay() + "." + db.getSaving(i).getSavingMonth() + "." + db.getSaving(i).getSavingYear();


                SavingHistoryDetailsItem item = new SavingHistoryDetailsItem(description, amount, date);
                savingListItems.add(item);
                allSavingAmount += db.getSaving(i).getSavingAmount();

            }
        }

        endAmountOfCertainMonth = allIncomeAmount - allSavingAmount - allExpenseAmount;

        allIncomeAmountTV.setText(formatNumber(allIncomeAmount));
        allExpenseAmountTV.setText(formatNumber(allExpenseAmount));
        allSavingAmountTV.setText(formatNumber(allSavingAmount));
        endAmountOfCertainMonthTV.setText(formatNumber(endAmountOfCertainMonth));



        incomeItemAdapter = new IncomeHistoryAdapterInDetails(this, incomeListItems);
        incomeRecyclerView.setAdapter(incomeItemAdapter);

        expenseItemAdapter = new ExpenseHistoryAdapterInDetails(this, expenseListItems);
        expenseRecyclerView.setAdapter(expenseItemAdapter);


        savingItemAdapter = new SavingHistoryAdapterInDetails(this, savingListItems);
        savingRecyclerView.setAdapter(savingItemAdapter);

    }

    private void setupUI(){
        Toolbar toolbar = findViewById(R.id.toolbarInHistoryDetailsID);
        toolbar.setTitleTextColor(Color.parseColor("#2C445E"));
        toolbar.setTitle("Review");
        toolbar.setElevation(20);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        monthTitleInHistoryDetails = findViewById(R.id.historyDetailsMonthTitleId);
        yearTitleInHistoryDetails = findViewById(R.id.historyDetailsYearTitleId);

        allIncomeAmountTV = findViewById(R.id.incomeAmountHistoryId);
        allExpenseAmountTV = findViewById(R.id.expenseAmountHistoryId);
        allSavingAmountTV = findViewById(R.id.savingAmountHistoryId);
        endAmountOfCertainMonthTV = findViewById(R.id.endAmountOfCertainMonthInHistoryId);
    }

    private String formatNumber(double toBeFormattedNumber) {

        boolean isMillion = toBeFormattedNumber >= 1000000 || toBeFormattedNumber <= -1000000;
        boolean isThousand = toBeFormattedNumber >= 1000 || toBeFormattedNumber <= -1000;
        double formattedNumber = 0;
        String output = null;

        if (isMillion) {
            formattedNumber = toBeFormattedNumber / 1000000;
            output = round(formattedNumber, 2) + "mio €";
            return output;
        }

        if (isThousand) {
            formattedNumber = toBeFormattedNumber / 1000;
            output = round(formattedNumber, 2) + "k €";
            return output;
        }

        if (toBeFormattedNumber < 1000 && toBeFormattedNumber > -1000) {
            output = round(toBeFormattedNumber, 2) + "€";
            return output;
        }

        return String.valueOf(round(toBeFormattedNumber, 2) + "€");
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}