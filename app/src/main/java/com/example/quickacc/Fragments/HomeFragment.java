package com.example.quickacc.Fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickacc.Database.Data.DatabaseHandler;
import com.example.quickacc.Database.Model.Current;
import com.example.quickacc.Database.Model.Expense;
import com.example.quickacc.Database.Model.Income;
import com.example.quickacc.Database.Model.Saving;
import com.example.quickacc.R;
import com.example.quickacc.RecyclerViewInShowPendingExpenseAlertDialog.ExpenseAdapterPenExpense;
import com.example.quickacc.RecyclerViewInShowPendingExpenseAlertDialog.ExpenseItem;
import com.example.quickacc.RecyclerViewInShowPendingIncomeAlertDialog.IncomeAdapterPenIncome;
import com.example.quickacc.RecyclerViewInShowPendingIncomeAlertDialog.IncomeItem;
import com.example.quickacc.RecyclerViewInShowPendingSavingAlertDialog.SavingAdapterPenSaving;
import com.example.quickacc.RecyclerViewInShowPendingSavingAlertDialog.SavingItem;
import com.google.android.material.internal.NavigationMenu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import io.github.yavski.fabspeeddial.FabSpeedDial;

public class HomeFragment extends Fragment {

    // AlertDialog Variables
    private Button addIncomeButtonInDialog;
    private Button addExpenseButtonInDialog;
    private Button addSavingButtonInDialog;
    private EditText addIncomeEditTextInAlertDialog;
    private EditText addExpenseEditTextInAlertDialog;
    private EditText addSavingEditTextInAlertDialog;
    private EditText addIncomeDescriptionEditTextInAlertDialog;
    private EditText addExpenseDescriptionEditTextInAlertDialog;
    private EditText addSavingDescriptionEditTextInAlertDialog;

    // UI Elements in homefragment itself
    private TextView availableAmount;
    private CardView availableAmountCardView1;
    private TextView currentAmount;
    private TextView pendingIncomeAmount;
    private TextView pendingExpensesAmount;
    private TextView pendingSavingAmount;
    private RelativeLayout dimBackground;
    private Toolbar toolbarInMain;
    private View dimBackgroundForToolbar;
    private Button currentDetails;
    private Button pendingIncomeDetails;
    private Button pendingExpenseDetails;
    private Button toSaveDetails;

    //RecyclerView in pending income alertdialog
    private RecyclerView recyclerViewPendingIncome;
    private RecyclerView.Adapter adapterPendingIncome;
    private List<IncomeItem> pendingIncomeListItems;

    //RecyclerView in pending expense alertdialog
    private RecyclerView recyclerViewPendingExpense;
    private RecyclerView.Adapter adapterPendingExpense;
    private List<ExpenseItem> pendingExpenseListItems;

    //RecyclerView in pending saving alertdialog
    private RecyclerView recyclerViewInPendingSaving;
    private RecyclerView.Adapter adapterPendingSaving;
    private List<SavingItem> pendingSavingListItems;

    // Floating ActionButton req. variables
    private FabSpeedDial fabSpeedDial;
    private FrameLayout frameLayoutUnderFAB;
    
    //UI Elements for current alertdialog and variables
    private TextView currentlyAvailableInCurrentTV;
    private EditText getBankAmountET;
    private EditText getWalletAmountET;
    private EditText getOthersAmountET;
    private ImageButton saveBankAmount;
    private ImageButton saveWalletAmount;
    private ImageButton saveOthersAmount;
    private double bank;
    private double wallet;
    private double others;
    private double currentlyAvailable;

    //Date and time variables
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private String currentMonthName;
    private String currentDayName;

    // textview elements for pending stuff alertdialogs
    private TextView pendingIncomeTitleInAlertDialog;
    private TextView pendingExpenseTitleInAlertDialog;
    private TextView pendingSavingTitleInAlertDialog;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //TODO: Fix whatever is wrong with FabSpeedDial

        View view = inflater.inflate(R.layout.home_fragment, container, false);

        fabSpeedDial =  (FabSpeedDial) view.findViewById(R.id.fabSpeedDialInHomeFragmentID);
        frameLayoutUnderFAB = view.findViewById(R.id.fabSpeedDialFrameLayoutID);
        dimBackground = view.findViewById(R.id.dimmBackgroundID);
        toolbarInMain = view.findViewById(R.id.toolbarID);

        // TextViews in homefragment
        availableAmount = view.findViewById(R.id.availableTextViewID);
        currentAmount = view.findViewById(R.id.currentTextViewID);
        pendingIncomeAmount = view.findViewById(R.id.pendingIncomeTextViewID);
        pendingExpensesAmount = view.findViewById(R.id.pendingExpenseTextViewID);
        pendingSavingAmount = view.findViewById(R.id.savingsTextViewID);

        // Buttons in homefragment
        currentDetails = view.findViewById(R.id.currentButtonInHomeFragmentID);
        pendingIncomeDetails = view.findViewById(R.id.pendingIncomeButtonInHomeFragmentId);
        pendingExpenseDetails = view.findViewById(R.id.pendingExpensesButtonInHomeFragmentId);
        toSaveDetails = view.findViewById(R.id.pendingSavingsButtonInHomeFragmentId);

        // available cardview
        availableAmountCardView1 = view.findViewById(R.id.cardviewAvailableID);

        //Date info available from here on
        getDateInfo();

        //--------------------------------------------------------------------------------------
        // DATABASE
        DatabaseHandler db = new DatabaseHandler(getContext());


        // correct display and manipulation of income TextView -------------------------------
        //TODO: Find a clean way to extract all types/dates of income (separate methods??)
        double allNotExecutedIncomeCurrentlyInDB = getAllNotExecutedIncomeOfThisMonthCurrentlyInDB(db);
        String allNotExecITextualOutput = String.valueOf(round(allNotExecutedIncomeCurrentlyInDB, 2)) + "€";

        if (allNotExecITextualOutput.length() > 8) {
            pendingIncomeAmount.setTextSize(getResources().getDimension(R.dimen.textsize_pending_stuff_tv_more_t_1_million));
        }

        pendingIncomeAmount.setText(allNotExecITextualOutput);


        // correct display and manipulation of expenses TextView -------------------------------
        //TODO: Same as with the income
        double allNotExecExpensesCurrentlyInDB = getAllNotExecutedExpensesOfThisMonthCurrentlyInDB(db);
        String allNotExecExTextualOutput = String.valueOf(round(allNotExecExpensesCurrentlyInDB, 2)) + "€";

        if (allNotExecExTextualOutput.length() > 8) {
            pendingExpensesAmount.setTextSize(getResources().getDimension(R.dimen.textsize_pending_stuff_tv_more_t_1_million));
        }

        pendingExpensesAmount.setText(allNotExecExTextualOutput);



        // correct display and manipulation of savings TextView -------------------------------
        double allNotExecSavingsCurrentlyInDB = getAllNotExecutedSavingsOfThisMonthCurrentlyInDB(db);
        String allNotExecSavTextualOutput = String.valueOf(round(allNotExecSavingsCurrentlyInDB, 2) + "€");

        if (allNotExecSavTextualOutput.length() > 8) {
            pendingSavingAmount.setTextSize(getResources().getDimension(R.dimen.textsize_pending_stuff_tv_more_t_1_million));
        }

        pendingSavingAmount.setText(allNotExecSavTextualOutput);


        // correct display and manipulation of current TextView -------------------------------

        if (db.getRowsCountInCurrentTable() == 0) {
            Current firstCurrentEntry = new Current(0,0,0);
            db.addCurrentInfosForFirstTime(firstCurrentEntry);
            Log.d("dopeInsertedCurr1time", "Inserting for first time to db currentlyAvailable...");
        }

        double[] allCurrentlyAvailableSources = db.getAllCurrentlyAvailableSources();

        bank = allCurrentlyAvailableSources[0];
        wallet = allCurrentlyAvailableSources[1];
        others = allCurrentlyAvailableSources[2];


        currentlyAvailable = bank + wallet + others;



        if (db.getRowsCountInCurrentTable() == 1 && currentDay < 2) {

            if (currentlyAvailable != 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set the custom layout
                //View customLayout = getLayoutInflater().inflate(R.layout.show_pending_expenses_alertdialog_layout, null);
                builder.setCancelable(false);
                builder.setTitle("Reset current amounts?");
                builder.setMessage("Would you like to reset your current Amounts to 0?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        allCurrentlyAvailableSources[0] = 0;
                        allCurrentlyAvailableSources[1] = 0;
                        allCurrentlyAvailableSources[2] = 0;

                        bank = 0;
                        wallet = 0;
                        others = 0;
                        currentlyAvailable = 0;

                        Current currentZeroAmount = new Current(bank, wallet, others);

                        db.updateCurrentInfos(currentZeroAmount);
                        refreshHomeFragment();
                    }
                });

                builder.setNegativeButton("No", null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                //Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        }

        Log.d("dopeCheckBANK", String.valueOf(bank));
        Log.d("dopeCheckWALLET", String.valueOf(wallet));
        Log.d("dopeCheckOTHERS", String.valueOf(others));

        currentAmount.setText(String.valueOf(round(currentlyAvailable, 2)) + "€");


        // correct display and manipulation of availableAmount TextView -------------------------------
        double actuallyAvailableAmount = currentlyAvailable + allNotExecutedIncomeCurrentlyInDB - allNotExecExpensesCurrentlyInDB - allNotExecSavingsCurrentlyInDB;
        String actuallyAvailableAmountTextualOutput = String.valueOf(round(actuallyAvailableAmount, 2) + "€");

        if (actuallyAvailableAmountTextualOutput.length() > 8) {
            availableAmount.setTextSize(getResources().getDimension(R.dimen.textsize_available_textview_homefragment_more_than_one_million));
        }

        availableAmount.setText(actuallyAvailableAmountTextualOutput);


        //Button listeners for homefragment ---------------------------------------

        currentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogShowingCurrentlyAvailableAmount(db);
            }
        });

        pendingIncomeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogShowingAllPendingIncome(db);
            }
        });

        pendingExpenseDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogShowingAllPendingExpenses(db);
            }
        });

        toSaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogShowingAllPendingSavings(db);
            }
        });

        //-----------------------------------------------------------------------------


        //Floating Action Button logic
        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                dimBackground.setVisibility(View.VISIBLE);

                return true;
            }

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                dimBackground.setVisibility(View.INVISIBLE);

                switch (menuItem.getItemId()) {
                    case R.id.addIncomeInFABID:
                        alertDialogForTakingNewIncomeFromFAB(db);
                        break;
                    case R.id.addExpenseInFABID:
                        alertDialogForTakingNewExpenseFromFAB(db);
                        break;
                    case R.id.addSavingInFABID:
                        alertDialogForTakingNewSavingsFromFAB(db);
                        break;
                }
                return false;
            }

            @Override
            public void onMenuClosed() {
                dimBackground.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }



    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++METHODS++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    // reading stuff from db

    private double getAllNotExecutedSavingsOfThisMonthCurrentlyInDB(DatabaseHandler db) {
        db = new DatabaseHandler(getContext());
        double savingAmountDouble = 0;
        int currentSavingCount = db.getSavingCount();

        for (int i = 1; i < currentSavingCount+1; i++) {
            if (!db.getSaving(i).isExecutedSaving() && db.getSaving(i).getSavingYear() == currentYear && db.getSaving(i).getSavingMonth() == currentMonth) {
                //get total relevant expensees
                savingAmountDouble += db.getSaving(i).getSavingAmount();
            }
        }

        return savingAmountDouble;

    }

    private double getAllNotExecutedExpensesOfThisMonthCurrentlyInDB(DatabaseHandler db) {
        db = new DatabaseHandler(getContext());
        double expenseAmountDouble = 0;
        int currentExpenseCount = db.getExpenseCount();

        for (int i = 1; i < currentExpenseCount+1; i++) {

            if (!db.getExpense(i).isExecutedExpense() && db.getExpense(i).getExpenseYear() == currentYear && db.getExpense(i).getExpenseMonth() == currentMonth) {
                expenseAmountDouble += db.getExpense(i).getExpenseAmount();
            }

        }

        return expenseAmountDouble;
    }

    private double getAllNotExecutedIncomeOfThisMonthCurrentlyInDB(DatabaseHandler db) {

        double incomeAmountDouble = 0;
        int currentIncomeCount = db.getIncomeCount();

        for (int i = 1; i < currentIncomeCount+1; i++) {

            if (!db.getIncome(i).isExecutedIncome() && db.getIncome(i).getYearOfIncome() == currentYear && db.getIncome(i).getMonthOfIncome() == currentMonth) {
                incomeAmountDouble += db.getIncome(i).getIncomeAmount();
            }

        }

        return incomeAmountDouble;
    }


    //Methods for showing stuff in alertdialog

    public void alertDialogShowingAllPendingSavings(DatabaseHandler db) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // set the custom layout
        View customLayout = getLayoutInflater().inflate(R.layout.show_pending_savings_alertdialog_layout, null);
        builder.setView(customLayout);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        //ui stuff
        pendingSavingTitleInAlertDialog = customLayout.findViewById(R.id.pendingSavingTextViewInAlertDialogID);
        pendingSavingTitleInAlertDialog.setText(String.valueOf(round(getAllNotExecutedSavingsOfThisMonthCurrentlyInDB(db), 2)));

        recyclerViewInPendingSaving = customLayout.findViewById(R.id.recyclerViewInPenSavingAlertDialog);
        recyclerViewInPendingSaving.setHasFixedSize(true);
        recyclerViewInPendingSaving.setLayoutManager(new LinearLayoutManager(getContext()));

        int pendingSavingCount = db.getSavingCount();
        List<Saving> allPendingSavings = db.getAllSaving();

        pendingSavingListItems = new ArrayList<>();

        for (int i = 0; i < pendingSavingCount; i++) {

            if (!allPendingSavings.get(i).isExecutedSaving() && allPendingSavings.get(i).getSavingMonth() == currentMonth && allPendingSavings.get(i).getSavingYear() == currentYear) {
                SavingItem savingItem = new SavingItem(allPendingSavings.get(i).getSavingId(), allPendingSavings.get(i).getSavingDescription(),
                        String.valueOf(allPendingSavings.get(i).getSavingAmount()),
                        "Added on " + allPendingSavings.get(i).getSavingMonthName());
                pendingSavingListItems.add(savingItem);
            }




        }

        adapterPendingSaving = new SavingAdapterPenSaving(getContext(), pendingSavingListItems);
        new ItemTouchHelper(itemTouchHelperCallbackSavingsHomeFragment).attachToRecyclerView(recyclerViewInPendingSaving);
        recyclerViewInPendingSaving.setAdapter(adapterPendingSaving);
    }

    public void alertDialogShowingAllPendingExpenses(DatabaseHandler db) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // set the custom layout
        View customLayout = getLayoutInflater().inflate(R.layout.show_pending_expenses_alertdialog_layout, null);
        builder.setView(customLayout);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        //set ui
        pendingExpenseTitleInAlertDialog = customLayout.findViewById(R.id.pendingExpenseTextViewAlertDialogID);
        pendingExpenseTitleInAlertDialog.setText(String.valueOf(round(getAllNotExecutedExpensesOfThisMonthCurrentlyInDB(db), 2)));

        recyclerViewPendingExpense = customLayout.findViewById(R.id.recyclerViewInPenExpenseAlertDialog);
        recyclerViewPendingExpense.setHasFixedSize(true);
        recyclerViewPendingExpense.setLayoutManager(new LinearLayoutManager(getContext()));

        int pendingExpenseCount = db.getExpenseCount();
        List<Expense> allPendingExpense = db.getAllExpense();


        pendingExpenseListItems = new ArrayList<>();

        for (int i = 0; i < pendingExpenseCount; i++) {

            if (!allPendingExpense.get(i).isExecutedExpense() && allPendingExpense.get(i).getExpenseMonth() == currentMonth && allPendingExpense.get(i).getExpenseYear() == currentYear) {

                ExpenseItem expenseItem = new ExpenseItem(allPendingExpense.get(i).getExpenseId(), allPendingExpense.get(i).getExpenseDescription(),
                                            String.valueOf(allPendingExpense.get(i).getExpenseAmount()),
                                            "Added on " + allPendingExpense.get(i).getExpenseMonthName());
                pendingExpenseListItems.add(expenseItem);

            }

        }

        adapterPendingExpense = new ExpenseAdapterPenExpense(getContext(), pendingExpenseListItems);
        new ItemTouchHelper(itemTouchHelperCallbackExpenseHomeFragment).attachToRecyclerView(recyclerViewPendingExpense);
        recyclerViewPendingExpense.setAdapter(adapterPendingExpense);
    }

    public void alertDialogShowingAllPendingIncome(DatabaseHandler db) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // set the custom layout
        View customLayout = getLayoutInflater().inflate(R.layout.show_pending_income_alertdialog_layout, null);
        builder.setView(customLayout);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        //UI elements
        pendingIncomeTitleInAlertDialog = customLayout.findViewById(R.id.pendingIncomeTextViewInAlertDialogID);
        pendingIncomeTitleInAlertDialog.setText(String.valueOf(round(getAllNotExecutedIncomeOfThisMonthCurrentlyInDB(db), 2)));


        int pendingIncomeCount = db.getIncomeCount();
        List<Income> allPendingIncome = db.getAllIncome();

        recyclerViewPendingIncome = customLayout.findViewById(R.id.recyclerViewInPenIncomeAlertDialog);
        recyclerViewPendingIncome.setHasFixedSize(true);
        recyclerViewPendingIncome.setLayoutManager(new LinearLayoutManager(getContext()));

        pendingIncomeListItems = new ArrayList<>();

        for (int i = 0; i < pendingIncomeCount; i++) {
            if (!allPendingIncome.get(i).isExecutedIncome() && allPendingIncome.get(i).getMonthOfIncome() == currentMonth && allPendingIncome.get(i).getYearOfIncome() == currentYear) {

                IncomeItem incomeItem = new IncomeItem(allPendingIncome.get(i).getIncomeId(), allPendingIncome.get(i).getIncomeDescription(),
                        String.valueOf(allPendingIncome.get(i).getIncomeAmount()), "Added in " + allPendingIncome.get(i).getMonthNameOfIncome());
                pendingIncomeListItems.add(incomeItem);

            }
        }

        adapterPendingIncome = new IncomeAdapterPenIncome(getContext(), pendingIncomeListItems);
        new ItemTouchHelper(itemTouchHelperCallbackIncomeHomeFragment).attachToRecyclerView(recyclerViewPendingIncome);
        recyclerViewPendingIncome.setAdapter(adapterPendingIncome);
    }

    public void alertDialogShowingCurrentlyAvailableAmount(DatabaseHandler db) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // set the custom layout
        View customLayout= getLayoutInflater().inflate(R.layout.show_currently_available_alertdialog_layout, null);
        builder.setView(customLayout);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        currentlyAvailableInCurrentTV = customLayout.findViewById(R.id.availableTextViewInCurrentID);

        double[] allCurrentlyAvailableSources = db.getAllCurrentlyAvailableSources();

        bank = allCurrentlyAvailableSources[0];
        wallet = allCurrentlyAvailableSources[1];
        others = allCurrentlyAvailableSources[2];
        double total = bank+wallet+others;

        currentlyAvailableInCurrentTV.setText(round(total, 2) + "€");

        getBankAmountET = customLayout.findViewById(R.id.bankAmountEditTextID);
        getOthersAmountET = customLayout.findViewById(R.id.othersAmountEditTextID);
        getWalletAmountET = customLayout.findViewById(R.id.walletAmountEditTextID);

        getBankAmountET.setHint(String.valueOf(bank));
        getOthersAmountET.setHint(String.valueOf(others));
        getWalletAmountET.setHint(String.valueOf(wallet));

        saveBankAmount = customLayout.findViewById(R.id.editBankAmountButtonID);
        saveWalletAmount = customLayout.findViewById(R.id.editWalletAmountButtonId);
        saveOthersAmount = customLayout.findViewById(R.id.editOthersAmountButtonId);

        saveBankAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getBankAmountET.getText() != null || getBankAmountET.getText().toString().equals("")) {
                    String input = getBankAmountET.getText().toString().trim();
                    double inputDouble = Double.parseDouble(input);

                    double[] allCurrentlyAvailableSources = db.getAllCurrentlyAvailableSources();

                    bank = inputDouble;
                    wallet = allCurrentlyAvailableSources[1];
                    others = allCurrentlyAvailableSources[2];
                    double total = bank+wallet+others;

                    Current current = new Current(bank, wallet, others);
                    db.updateCurrentInfos(current);
                    refreshHomeFragment();
                    currentlyAvailableInCurrentTV.setText(round(total, 2) + "€");
                } else {
                    Toast.makeText(getContext(), "Please enter something!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        saveWalletAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getWalletAmountET.getText() != null || getWalletAmountET.getText().toString().equals("")) {
                    String input = getWalletAmountET.getText().toString().trim();
                    double inputDouble = Double.parseDouble(input);

                    double[] allCurrentlyAvailableSources = db.getAllCurrentlyAvailableSources();

                    bank = allCurrentlyAvailableSources[0];
                    wallet = inputDouble;
                    others = allCurrentlyAvailableSources[2];
                    double total = bank+wallet+others;


                    Current current = new Current(bank, wallet, others);
                    db.updateCurrentInfos(current);
                    refreshHomeFragment();
                    currentlyAvailableInCurrentTV.setText(round(total, 2) + "€");
                } else {
                    Toast.makeText(getContext(), "Please enter something!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        saveOthersAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getOthersAmountET.getText() != null || getOthersAmountET.getText().toString().equals("")) {
                    String input = getOthersAmountET.getText().toString().trim();
                    double inputDouble = Double.parseDouble(input);

                    double[] allCurrentlyAvailableSources = db.getAllCurrentlyAvailableSources();

                    bank = allCurrentlyAvailableSources[0];
                    wallet = allCurrentlyAvailableSources[1];
                    others = inputDouble;
                    double total = bank+wallet+others;


                    Current current = new Current(bank, wallet, others);
                    db.updateCurrentInfos(current);
                    refreshHomeFragment();
                    currentlyAvailableInCurrentTV.setText(round(total, 2) + "€");
                } else {
                    Toast.makeText(getContext(), "Please enter something!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    //Methods for taking input through alertdialogs

    public void alertDialogForTakingNewIncomeFromFAB(DatabaseHandler db) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // set the custom layout
        View customLayout= getLayoutInflater().inflate(R.layout.add_income_alertdialog_layout, null);
        builder.setView(customLayout);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                income.setIncomeType("one-time");
                income.setExecutedIncome(false);

                db.addIncome(income);

                refreshHomeFragment();
                dialog.dismiss();
            }
        });
    }

    
    public void alertDialogForTakingNewExpenseFromFAB(DatabaseHandler db) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));

        // set the custom layout
        View customLayout= getLayoutInflater().inflate(R.layout.add_expense_alertdialog_layout, null);
        builder.setView(customLayout);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                expense.setExpenseType("one-time");
                expense.setExecutedExpense(false);

                db.addExpense(expense);

                refreshHomeFragment();
                dialog.dismiss();
            }
        });
    }

    public void alertDialogForTakingNewSavingsFromFAB(DatabaseHandler db) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // set the custom layout
        View customLayout= getLayoutInflater().inflate(R.layout.add_saving_alertdialog_layout, null);
        builder.setView(customLayout);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        addSavingEditTextInAlertDialog = customLayout.findViewById(R.id.addSavingDialogEditTextID);
        addSavingButtonInDialog = customLayout.findViewById(R.id.addSavingButtonInDialogID);
        addSavingDescriptionEditTextInAlertDialog = customLayout.findViewById(R.id.addSavingNotesDialogEditTextID);

        addSavingButtonInDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double inputAmount = Double.parseDouble(addSavingEditTextInAlertDialog.getText().toString().trim());
                String inputDescription = addSavingDescriptionEditTextInAlertDialog.getText().toString().trim();

                Saving saving = new Saving();
                saving.setSavingMonth(currentMonth);
                saving.setSavingYear(currentYear);
                saving.setSavingDay(currentDay);
                saving.setSavingDayName(currentDayName);
                saving.setSavingMonthName(currentMonthName);
                saving.setSavingDescription(inputDescription);
                saving.setSavingAmount(inputAmount);
                saving.setSavingType("one-time");
                saving.setExecutedSaving(false);

                db.addSaving(saving);
                refreshHomeFragment();
                dialog.dismiss();
            }
        });
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallbackIncomeHomeFragment = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            DatabaseHandler db = new DatabaseHandler(getContext());

            List<Income> allIncomeItems = db.getAllIncome();
            String incomeDescription = pendingIncomeListItems.get(viewHolder.getAdapterPosition()).getIncomeDescription();
            double incomeAmount = Double.parseDouble(pendingIncomeListItems.get(viewHolder.getAdapterPosition()).getIncomeAmount());
            int incomeId = pendingIncomeListItems.get(viewHolder.getAdapterPosition()).getIncomeReId();

            Income thisSpecificIncome = null;

            for (int i = 0; i < db.getIncomeCount(); i++) {
                if (allIncomeItems.get(i).getIncomeId() == incomeId && allIncomeItems.get(i).getIncomeDescription().equals(incomeDescription) && allIncomeItems.get(i).getIncomeAmount() == incomeAmount) {
                    thisSpecificIncome = allIncomeItems.get(i);
                }
            }

            thisSpecificIncome.setExecutedIncome(true);
            Log.d("dopeThisSpecificIncome", thisSpecificIncome.getIncomeDescription() + ", " + thisSpecificIncome.isExecutedIncome());
            db.updateIncome(thisSpecificIncome);
            pendingIncomeTitleInAlertDialog.setText(String.valueOf(getAllNotExecutedIncomeOfThisMonthCurrentlyInDB(db)));

            Income checkIncome = db.getIncome(thisSpecificIncome.getIncomeId());
            Log.d("dopeCheckIncome", checkIncome.getIncomeDescription() + ", " + checkIncome.isExecutedIncome());


            pendingIncomeListItems.remove(viewHolder.getAdapterPosition());
            adapterPendingIncome.notifyDataSetChanged();

            refreshHomeFragment();

        }
    };

    ItemTouchHelper.SimpleCallback itemTouchHelperCallbackExpenseHomeFragment = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            DatabaseHandler db = new DatabaseHandler(getContext());

            List<Expense> allExpenseItems = db.getAllExpense();
            String expenseDescription = pendingExpenseListItems.get(viewHolder.getAdapterPosition()).getExpenseDescription();
            double expenseAmount = Double.parseDouble(pendingExpenseListItems.get(viewHolder.getAdapterPosition()).getExpenseAmount());
            int expenseId = pendingExpenseListItems.get(viewHolder.getAdapterPosition()).getExpenseId();

            Expense thisSpecificExpense = null;

            for (int i = 0; i < db.getExpenseCount(); i++) {
                if (allExpenseItems.get(i).getExpenseId() == expenseId && allExpenseItems.get(i).getExpenseDescription().equals(expenseDescription) && allExpenseItems.get(i).getExpenseAmount() == expenseAmount) {
                    thisSpecificExpense = allExpenseItems.get(i);
                }
            }

            thisSpecificExpense.setExecutedExpense(true);
            Log.d("dopeThisSpecificExpense", thisSpecificExpense.getExpenseDescription() + ", " + thisSpecificExpense.isExecutedExpense());
            db.updateExpense(thisSpecificExpense);
            pendingExpenseTitleInAlertDialog.setText(String.valueOf(getAllNotExecutedExpensesOfThisMonthCurrentlyInDB(db)));

            Expense checkExpense = db.getExpense(thisSpecificExpense.getExpenseId());
            Log.d("dopeCheckExpense", checkExpense.getExpenseDescription() + ", " + checkExpense.isExecutedExpense());


            pendingExpenseListItems.remove(viewHolder.getAdapterPosition());
            adapterPendingExpense.notifyDataSetChanged();

            refreshHomeFragment();

        }
    };

    ItemTouchHelper.SimpleCallback itemTouchHelperCallbackSavingsHomeFragment = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            DatabaseHandler db = new DatabaseHandler(getContext());

            List<Saving> allSavingItems = db.getAllSaving();
            String savingDescription = pendingSavingListItems.get(viewHolder.getAdapterPosition()).getSavingDescription();
            double savingAmount = Double.parseDouble(pendingSavingListItems.get(viewHolder.getAdapterPosition()).getSavingAmount());
            int savingId = pendingSavingListItems.get(viewHolder.getAdapterPosition()).getSavingReid();

            Saving thisSpecificSaving = null;

            for (int i = 0; i < db.getSavingCount(); i++) {
                if (allSavingItems.get(i).getSavingId() == savingId && allSavingItems.get(i).getSavingDescription().equals(savingDescription) && allSavingItems.get(i).getSavingAmount() == savingAmount) {
                    thisSpecificSaving = allSavingItems.get(i);
                }
            }

            thisSpecificSaving.setExecutedSaving(true);
            Log.d("dopeThisSpecificSaving", thisSpecificSaving.getSavingDescription() + ", " + thisSpecificSaving.isExecutedSaving());
            db.updateSaving(thisSpecificSaving);
            pendingSavingTitleInAlertDialog.setText(String.valueOf(getAllNotExecutedSavingsOfThisMonthCurrentlyInDB(db)));

            Saving checkSaving = db.getSaving(thisSpecificSaving.getSavingId());
            Log.d("dopeCheckSaving", checkSaving.getSavingDescription() + ", " + checkSaving.isExecutedSaving());


            pendingSavingListItems.remove(viewHolder.getAdapterPosition());
            adapterPendingSaving.notifyDataSetChanged();

            refreshHomeFragment();

        }
    };

    //Useful methods for Toasting

    private void sendDialogDataToActivity(String data) {
        Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
    }

    private void sendDialogDataToActivity(String data, String type) {
        Toast.makeText(getContext(), type + " successfully updated to " + data, Toast.LENGTH_SHORT).show();
    }

    //Getting Date and time info

    @SuppressLint("SimpleDateFormat")
    private void getDateInfo() {
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        currentMonthName = new SimpleDateFormat("MMMM").format(calendar.getTime());
        currentDayName = new SimpleDateFormat("EEEE").format(calendar.getTime());
    }

    private void refreshHomeFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(HomeFragment.this).attach(HomeFragment.this).commit();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
