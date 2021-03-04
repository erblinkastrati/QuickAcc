package com.example.quickacc.RecyclerViewAddExpense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickacc.R;
import com.example.quickacc.RecyclerViewAddIncome.IncomeOptionItem;

import java.util.List;

public class AddExpenseRecyclerAdapter extends RecyclerView.Adapter<AddExpenseRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<ExpenseOptionItem> listItems;

    public AddExpenseRecyclerAdapter(Context context, List<ExpenseOptionItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public AddExpenseRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_expense_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddExpenseRecyclerAdapter.ViewHolder holder, int position) {

        ExpenseOptionItem expenseOptionItem = listItems.get(position);

        holder.expenseType.setText(expenseOptionItem.getExpenseType());

        //TODO: Set hint to what is currently in database!!
        holder.specificExpenseAmount.setHint(expenseOptionItem.getSpecificExpenseAmount());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView expenseType;
        public EditText specificExpenseAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            expenseType = itemView.findViewById(R.id.optionExpenseTextViewID);
            specificExpenseAmount = itemView.findViewById(R.id.specificExpenseAmountEditTextID);
        }
    }
}
