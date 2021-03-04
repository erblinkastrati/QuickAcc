package com.example.quickacc.RecyclerViewInShowPendingIncomeAlertDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickacc.R;
import com.example.quickacc.RecyclerViewAddIncome.AddIncomeRecyclerAdapter;

import java.util.List;

public class IncomeAdapterPenIncome extends RecyclerView.Adapter<IncomeAdapterPenIncome.ViewHolder> {

    private Context context;
    private List<IncomeItem> listItem;

    public IncomeAdapterPenIncome(Context context, List<IncomeItem> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public IncomeAdapterPenIncome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_pendingincome_alterdialog, parent, false);
        return new IncomeAdapterPenIncome.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeAdapterPenIncome.ViewHolder holder, int position) {

        IncomeItem incomeItem = listItem.get(position);

        holder.incomeAmount.setText(incomeItem.getIncomeAmount());
        holder.incomeDescription.setText(incomeItem.getIncomeDescription());
        holder.expectedDate.setText(incomeItem.getExpectedDate());


    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView incomeDescription;
        public TextView incomeAmount;
        public TextView expectedDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            incomeAmount = itemView.findViewById(R.id.pendingIncomeAmountInAlertDialogRecViewId);
            incomeDescription = itemView.findViewById(R.id.incomeDescriptionInPenInAlertDialogId);
            expectedDate = itemView.findViewById(R.id.expectedDateId);

        }
    }
}
