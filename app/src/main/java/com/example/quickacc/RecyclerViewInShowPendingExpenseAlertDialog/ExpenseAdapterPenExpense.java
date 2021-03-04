package com.example.quickacc.RecyclerViewInShowPendingExpenseAlertDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickacc.R;

import java.util.List;

public class ExpenseAdapterPenExpense extends RecyclerView.Adapter<ExpenseAdapterPenExpense.ViewHolder> {

    private Context context;
    private List<ExpenseItem> listItems;

    public ExpenseAdapterPenExpense(Context context, List<ExpenseItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ExpenseAdapterPenExpense.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_pendingexpense_alterdialog, parent, false);
        return new ExpenseAdapterPenExpense.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapterPenExpense.ViewHolder holder, int position) {

        ExpenseItem expenseItem = listItems.get(position);

        holder.expenseAmount.setText(expenseItem.getExpenseAmount());
        holder.expenseDescription.setText(expenseItem.getExpenseDescription());
        holder.expectedExpenseDate.setText(expenseItem.getExpectedExpenseDate());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView expenseAmount;
        public TextView expenseDescription;
        public TextView expectedExpenseDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            expenseAmount = itemView.findViewById(R.id.pendingExpenseAmountInAlertDialogRecViewId);
            expenseDescription = itemView.findViewById(R.id.expenseDescriptionInPenExAlertDialogId);
            expectedExpenseDate = itemView.findViewById(R.id.expectedExpenseDateId);

        }
    }
}
