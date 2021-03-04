package com.example.quickacc.RecyclerViewHistoryFragment.HistoryDetailsRecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickacc.R;

import java.util.List;

public class ExpenseHistoryAdapterInDetails extends RecyclerView.Adapter<ExpenseHistoryAdapterInDetails.ViewHolder> {

    private Context context;
    private List<ExpenseHistoryDetailsItem> listItems;

    public ExpenseHistoryAdapterInDetails(Context context, List<ExpenseHistoryDetailsItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ExpenseHistoryAdapterInDetails.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_expense_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHistoryAdapterInDetails.ViewHolder holder, int position) {

        ExpenseHistoryDetailsItem expenseHistoryDetailsItem = listItems.get(position);

        holder.itemName.setText(expenseHistoryDetailsItem.getItemName());
        holder.itemAmount.setText(expenseHistoryDetailsItem.getItemAmount());
        holder.timeAndDate.setText(expenseHistoryDetailsItem.getTimeAndDate());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView itemName;
        public TextView itemAmount;
        public TextView timeAndDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemNameInHistoryDetailsId);
            itemAmount = itemView.findViewById(R.id.itemAmountInHistoryDetailsId);
            timeAndDate = itemView.findViewById(R.id.timeAndDateStampInHistoryDetailsId);

        }
    }
}
