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
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.quickacc.Database.Data.DatabaseHandler;
import com.example.quickacc.Database.Model.Income;
import com.example.quickacc.RecyclerViewAddIncome.AddIncomeRecyclerAdapter;
import com.example.quickacc.RecyclerViewAddIncome.IncomeOptionItem;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class IncomeActivity extends AppCompatActivity {

    //Recyclerview instance variables
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<IncomeOptionItem> listItems;

    private Button addRecurringIncomeToDbButton;

    private TextView toReceiveTextView;
    private double toReceiveAmount;

    //Date and time variables
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private String currentMonthName;
    private String currentDayName;

    // AlertDialog Variables
    private Button addIncomeButtonInDialog;
    private EditText addIncomeEditTextInAlertDialog;
    private EditText addIncomeDescriptionEditTextInAlertDialog;

    private DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        setUpUI();
        getDateInfo();

        setUpRecyclerView(db);
        setTotalToReceiveIncome(db);


        addRecurringIncomeToDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogForTakingNewIncomeFromFAB(db);
            }
        });


    }

    private void setTotalToReceiveIncome(DatabaseHandler db) {
        toReceiveAmount = 0;
        List<Income> allIncomeItems = db.getAllIncome();

        for (int i = 0; i < db.getIncomeCount(); i++) {
            if (allIncomeItems.get(i).getIncomeType().equals("recurring") && !allIncomeItems.get(i).isExecutedIncome()
                            && allIncomeItems.get(i).getYearOfIncome() == currentYear && allIncomeItems.get(i).getMonthOfIncome() == currentMonth) {
                toReceiveAmount += allIncomeItems.get(i).getIncomeAmount();
            }
        }

        toReceiveTextView.setText(String.valueOf(round(toReceiveAmount, 0)));
    }

    public double getTotalIncomeAmount() {
        return toReceiveAmount;
    }

    private void setUpUI() {

        Toolbar toolbar = findViewById(R.id.toolbarInAddIncomeID);
        toolbar.setTitleTextColor(Color.parseColor("#2C445E"));
        toolbar.setTitle("Add recurring income");
        toolbar.setElevation(20);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IncomeActivity.this, MainActivity.class));
            }
        });

        toReceiveTextView = findViewById(R.id.toReceiveTextViewID);
        addRecurringIncomeToDbButton = findViewById(R.id.addIncomeToDbButtonID);



    }

    private void setUpRecyclerView(DatabaseHandler db) {
        recyclerView = findViewById(R.id.recyclerViewInAddIncomeID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        List<Income> allIncomeItems = db.getAllIncome();

        for (int i = 0; i < db.getIncomeCount(); i++) {
            if (allIncomeItems.get(i).getIncomeType().equals("recurring") && !allIncomeItems.get(i).isExecutedIncome()
                    && allIncomeItems.get(i).getYearOfIncome() == currentYear && allIncomeItems.get(i).getMonthOfIncome() == currentMonth) {
                IncomeOptionItem incomeOptionItem = new IncomeOptionItem(allIncomeItems.get(i).getIncomeId(), allIncomeItems.get(i).getIncomeDescription(), String.valueOf(round(allIncomeItems.get(i).getIncomeAmount(),2)));
                listItems.add(incomeOptionItem);
            }
        }


        adapter = new AddIncomeRecyclerAdapter(this, listItems);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

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

    public void alertDialogForTakingNewIncomeFromFAB(DatabaseHandler db) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        View customLayout= getLayoutInflater().inflate(R.layout.add_income_alertdialog_layout, null);
        builder.setView(customLayout);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Setup stuff from db

        addIncomeEditTextInAlertDialog = customLayout.findViewById(R.id.addIncomeDialogEditTextID);
        addIncomeButtonInDialog = customLayout.findViewById(R.id.addIncomeButtonInDialogID);
        addIncomeDescriptionEditTextInAlertDialog = customLayout.findViewById(R.id.addIncomeNotesDialogEditTextID);

        addIncomeButtonInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double inputAmount = Double.parseDouble(addIncomeEditTextInAlertDialog.getText().toString().trim());
                String inputDescription = addIncomeDescriptionEditTextInAlertDialog.getText().toString().trim();

                Income income = new Income();
                income.setMonthOfIncome(currentMonth);
                income.setYearOfIncome(currentYear);
                income.setDayOfIncome(currentDay);
                income.setDayNameOfIncome(currentDayName);
                income.setMonthNameOfIncome(currentMonthName);
                income.setIncomeDescription(inputDescription);
                income.setIncomeAmount(inputAmount);
                income.setIncomeType("recurring");
                income.setExecutedIncome(false);

                db.addIncome(income);

                finish();
                startActivity(getIntent());

                dialog.dismiss();
            }
        });
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

            List<Income> allIncomeItems = db.getAllIncome();
            String incomeDescription = listItems.get(viewHolder.getAdapterPosition()).getIncomeStreamType();
            double incomeAmount = Double.parseDouble(listItems.get(viewHolder.getAdapterPosition()).getSpecificIncomeAmount());
            int incomeId = listItems.get(viewHolder.getAdapterPosition()).getIncomeReActId();

            Income thisSpecificIncome = null;

            for (int i = 0; i < db.getIncomeCount(); i++) {
                if (allIncomeItems.get(i).getIncomeId() == incomeId && allIncomeItems.get(i).getIncomeDescription().equals(incomeDescription) && allIncomeItems.get(i).getIncomeAmount() == incomeAmount) {
                    thisSpecificIncome = allIncomeItems.get(i);
                }
            }

            thisSpecificIncome.setExecutedIncome(true);
            Log.d("dopeThisSpecificIncome", thisSpecificIncome.getIncomeDescription() + ", " + thisSpecificIncome.isExecutedIncome());
            db.updateIncome(thisSpecificIncome);

            Income checkIncome = db.getIncome(thisSpecificIncome.getIncomeId());
            Log.d("dopeCheckIncome", checkIncome.getIncomeDescription() + ", " + checkIncome.isExecutedIncome());


            listItems.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();

            Intent intent = getIntent();
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);

        }
    };
}