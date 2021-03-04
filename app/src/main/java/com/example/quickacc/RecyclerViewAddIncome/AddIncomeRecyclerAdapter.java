package com.example.quickacc.RecyclerViewAddIncome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickacc.R;

import java.util.List;

public class AddIncomeRecyclerAdapter extends RecyclerView.Adapter<AddIncomeRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<IncomeOptionItem> listItems;

    public AddIncomeRecyclerAdapter(Context context, List<IncomeOptionItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public AddIncomeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_income_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddIncomeRecyclerAdapter.ViewHolder holder, int position) {

        IncomeOptionItem incomeOptionItem = listItems.get(position);

        holder.incomeStreamType.setText(incomeOptionItem.getIncomeStreamType());
        holder.specificIncomeAmount.setHint(incomeOptionItem.getSpecificIncomeAmount());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView incomeStreamType;
        public EditText specificIncomeAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            incomeStreamType = itemView.findViewById(R.id.optionIncomeTypeTextViewID);
            specificIncomeAmount = itemView.findViewById(R.id.specificSalaryEditTextID);
        }
    }
}
