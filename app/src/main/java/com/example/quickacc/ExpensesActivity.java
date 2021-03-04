package com.example.quickacc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.quickacc.Database.Data.DatabaseHandler;
import com.example.quickacc.Database.Model.Expense;
import com.example.quickacc.Database.Model.Income;
import com.example.quickacc.RecyclerViewAddExpense.AddExpenseRecyclerAdapter;
import com.example.quickacc.RecyclerViewAddExpense.ExpenseOptionItem;
import com.example.quickacc.RecyclerViewAddIncome.AddIncomeRecyclerAdapter;
import com.example.quickacc.RecyclerViewAddIncome.IncomeOptionItem;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.zip.DataFormatException;

public class ExpensesActivity extends AppCompatActivity {

    //Recyclerview instance variables
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ExpenseOptionItem> listItems;

    private TextView toSpendTextView;
    private Button addRecurringExpenseButton;
    private double toSpendAmount;

    //Date and time variables
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private String currentMonthName;
    private String currentDayName;

    // AlertDialog Variables
    private Button addExpenseButtonInDialog;
    private EditText addExpenseEditTextInAlertDialog;
    private EditText addExpenseDescriptionEditTextInAlertDialog;

    private DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);



        getDateInfo();
        setUpUI();
        setUpRecyclerView(db);
        setTotalToSpendExpense(db);

        addRecurringExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogForTakingNewExpenseFromFAB(db);
            }
        });
    }

    private void setTotalToSpendExpense(DatabaseHandler db) {
        toSpendAmount = 0;
        List<Expense> allExpenseItems = db.getAllExpense();

        for (int i = 0; i < db.getExpenseCount(); i++) {

            if (allExpenseItems.get(i).getExpenseType().equals("recurring") && !allExpenseItems.get(i).isExecutedExpense()
                    && allExpenseItems.get(i).getExpenseYear() == currentYear && allExpenseItems.get(i).getExpenseMonth() == currentMonth) {
                toSpendAmount += allExpenseItems.get(i).getExpenseAmount();
            }

        }
        toSpendTextView.setText(String.valueOf(round(toSpendAmount, 2)));
    }

    private void setUpUI() {

        Toolbar toolbar = findViewById(R.id.toolbarInAddExpenseID);
        toolbar.setTitleTextColor(Color.parseColor("#2C445E"));
        toolbar.setTitle("Add recurring expenses");
        toolbar.setElevation(20);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpensesActivity.this, MainActivity.class));
            }
        });

        toSpendTextView = findViewById(R.id.totalExpensesTextViewID);
        addRecurringExpenseButton = findViewById(R.id.addRecurringExpenseToDbViewButtonID);



    }

    private void setUpRecyclerView(DatabaseHandler db) {
        recyclerView = findViewById(R.id.recyclerViewInAddExpenseID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        List<Expense> allExpenseItems = db.getAllExpense();

        for (int i = 0; i < db.getExpenseCount(); i++) {

            if (allExpenseItems.get(i).getExpenseType().equals("recurring") && !allExpenseItems.get(i).isExecutedExpense()
                    && allExpenseItems.get(i).getExpenseYear() == currentYear && allExpenseItems.get(i).getExpenseMonth() == currentMonth) {

                ExpenseOptionItem expenseOptionItem = new ExpenseOptionItem(allExpenseItems.get(i).getExpenseId(), allExpenseItems.get(i).getExpenseDescription(),
                                                                            String.valueOf(round(allExpenseItems.get(i).getExpenseAmount(), 2)));
                listItems.add(expenseOptionItem);

            }
        }

        adapter = new AddExpenseRecyclerAdapter(this, listItems);

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);

    }

    public void alertDialogForTakingNewExpenseFromFAB(DatabaseHandler db) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        View customLayout= getLayoutInflater().inflate(R.layout.add_expense_alertdialog_layout, null);
        builder.setView(customLayout);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        addExpenseEditTextInAlertDialog = customLayout.findViewById(R.id.addExpenseDialogEditTextID);
        addExpenseButtonInDialog = customLayout.findViewById(R.id.addExpenseButtonInDialogID);
        addExpenseDescriptionEditTextInAlertDialog = customLayout.findViewById(R.id.addExpenseNotesDialogEditTextID);

        addExpenseButtonInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double inputAmount = Double.parseDouble(addExpenseEditTextInAlertDialog.getText().toString().trim());
                String inputDescription = addExpenseDescriptionEditTextInAlertDialog.getText().toString().trim();

                Expense expense = new Expense();
                expense.setExpenseMonth(currentMonth);
                expense.setExpenseYear(currentYear);
                expense.setExpenseDay(currentDay);
                expense.setExpenseDayName(currentDayName);
                expense.setExpenseMonthName(currentMonthName);
                expense.setExpenseDescription(inputDescription);
                expense.setExpenseAmount(inputAmount);
                expense.setExpenseType("recurring");
                expense.setExecutedExpense(false);

                db.addExpense(expense);
                reloadActivity();
                dialog.dismiss();
            }
        });
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private void getDateInfo() {
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        currentMonthName = new SimpleDateFormat("MMMM").format(calendar.getTime());
        currentDayName = new SimpleDateFormat("EEEE").format(calendar.getTime());
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            List<Expense> allExpenseItems = db.getAllExpense();
            String expenseDescription = listItems.get(viewHolder.getAdapterPosition()).getExpenseType();
            double expenseAmount = Double.parseDouble(listItems.get(viewHolder.getAdapterPosition()).getSpecificExpenseAmount());
            int expenseId = listItems.get(viewHolder.getAdapterPosition()).getExpenseReActId();

            Expense thisSpecificExpense = null;

            for (int i = 0; i < db.getExpenseCount(); i++) {
                if (allExpenseItems.get(i).getExpenseId() == expenseId && allExpenseItems.get(i).getExpenseDescription().equals(expenseDescription) && allExpenseItems.get(i).getExpenseAmount() == expenseAmount) {
                    thisSpecificExpense = allExpenseItems.get(i);
                }
            }

            thisSpecificExpense.setExecutedExpense(true);
            Log.d("fuckThisSpecificIncome", thisSpecificExpense.getExpenseDescription() + ", " + thisSpecificExpense.isExecutedExpense());
            db.updateExpense(thisSpecificExpense);

            Expense checkExpense = db.getExpense(thisSpecificExpense.getExpenseId());
            Log.d("fuckCheckInExpense", checkExpense.getExpenseDescription() + ", " + checkExpense.isExecutedExpense());


            listItems.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
            reloadActivity();

        }
    };

    private void reloadActivity() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}