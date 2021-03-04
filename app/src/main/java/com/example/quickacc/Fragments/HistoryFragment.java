package com.example.quickacc.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickacc.Database.Data.DatabaseHandler;
import com.example.quickacc.Database.Model.Expense;
import com.example.quickacc.Database.Model.Income;
import com.example.quickacc.Database.Model.Saving;
import com.example.quickacc.R;
import com.example.quickacc.RecyclerViewHistoryFragment.DateElements;
import com.example.quickacc.RecyclerViewHistoryFragment.HistoryAdapter;
import com.example.quickacc.RecyclerViewHistoryFragment.HistoryDetails;
import com.example.quickacc.RecyclerViewHistoryFragment.HistoryItem;
import com.example.quickacc.RecyclerViewInShowPendingExpenseAlertDialog.ExpenseAdapterPenExpense;
import com.example.quickacc.RecyclerViewInShowPendingExpenseAlertDialog.ExpenseItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;
    private List<HistoryItem> listItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);

        DatabaseHandler db = new DatabaseHandler(getContext());

        historyRecyclerView = view.findViewById(R.id.recyclerViewHistoryFragmentId);
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<DateElements> dates = new ArrayList<>();


        for (int i = 1; i < db.getIncomeCount() + 1; i++) {

            DateElements dateElements = new DateElements();
            dateElements.setMonthName(db.getIncome(i).getMonthNameOfIncome());
            dateElements.setMonthNumber(String.valueOf(db.getIncome(i).getMonthOfIncome()));
            dateElements.setYear(String.valueOf(db.getIncome(i).getYearOfIncome()));

            boolean containsSameElement = false;

            for (int j = 0; j < dates.size(); j++) {
                if (dates.get(j).getYear().equals(dateElements.getYear()) && dates.get(j).getMonthNumber().equals(dateElements.getMonthNumber())) {
                    containsSameElement = true;
                }
            }

            if (!containsSameElement) {
                dates.add(dateElements);
            }

        }

        for (int i = 1; i < db.getExpenseCount() + 1; i++) {

            DateElements dateElements = new DateElements();
            dateElements.setMonthName(db.getExpense(i).getExpenseMonthName());
            dateElements.setMonthNumber(String.valueOf(db.getExpense(i).getExpenseMonth()));
            dateElements.setYear(String.valueOf(db.getExpense(i).getExpenseYear()));

            boolean containsSameElement = false;

            for (int j = 0; j < dates.size(); j++) {
                if (dates.get(j).getYear().equals(dateElements.getYear()) && dates.get(j).getMonthNumber().equals(dateElements.getMonthNumber())) {
                    containsSameElement = true;
                }
            }

            if (!containsSameElement) {
                dates.add(dateElements);
            }

        }

        for (int i = 1; i < db.getSavingCount() + 1; i++) {

            DateElements dateElements = new DateElements();
            dateElements.setMonthName(db.getSaving(i).getSavingMonthName());
            dateElements.setMonthNumber(String.valueOf(db.getSaving(i).getSavingMonth()));
            dateElements.setYear(String.valueOf(db.getSaving(i).getSavingYear()));

            boolean containsSameElement = false;

            for (int j = 0; j < dates.size(); j++) {
                if (dates.get(j).getYear().equals(dateElements.getYear()) && dates.get(j).getMonthNumber().equals(dateElements.getMonthNumber())) {
                    containsSameElement = true;
                }
            }

            if (!containsSameElement) {
                dates.add(dateElements);
            }

        }

        Log.d("dopeDatesSize", String.valueOf(dates.size()));

        listItems = new ArrayList<>();

        for (int i = 0; i < dates.size(); i++) {

            if (Integer.parseInt(dates.get(i).getMonthNumber()) < 13) {
                HistoryItem historyItem = new HistoryItem(dates.get(i).getMonthNumber(), dates.get(i).getMonthName(), dates.get(i).getYear());
                listItems.add(historyItem);
            }
        }

        historyAdapter = new HistoryAdapter(getContext(), listItems);
        historyRecyclerView.setAdapter(historyAdapter);

        historyAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                listItems.get(position);

                Intent intent = new Intent(getContext(), HistoryDetails.class);
                intent.putExtra("month_name", listItems.get(position).getMonthName());
                intent.putExtra("year", listItems.get(position).getYear());
                intent.putExtra("month", listItems.get(position).getMonthNumber());
                startActivity(intent);

            }
        });



        return view;
    }
}
