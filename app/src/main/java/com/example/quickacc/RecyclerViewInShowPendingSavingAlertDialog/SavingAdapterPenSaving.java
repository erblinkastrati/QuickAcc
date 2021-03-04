package com.example.quickacc.RecyclerViewInShowPendingSavingAlertDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickacc.R;

import java.util.List;

public class SavingAdapterPenSaving extends RecyclerView.Adapter<SavingAdapterPenSaving.ViewHolder> {

    private Context context;
    private List<SavingItem> listItems;

    public SavingAdapterPenSaving(Context context, List<SavingItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    public SavingAdapterPenSaving() {
    }

    @NonNull
    @Override
    public SavingAdapterPenSaving.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_pendingsaving_alterdialog, parent, false);
        return new SavingAdapterPenSaving.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavingAdapterPenSaving.ViewHolder holder, int position) {

        SavingItem savingItem = listItems.get(position);

        holder.expectedSavingDate.setText(savingItem.getExpectedSavingDate());
        holder.savingDescription.setText(savingItem.getSavingDescription());
        holder.savingAmount.setText(savingItem.getSavingAmount());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView savingDescription;
        public TextView savingAmount;
        public TextView expectedSavingDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            savingAmount = itemView.findViewById(R.id.pendingSavingAmountInAlertDialogRecViewId);
            savingDescription = itemView.findViewById(R.id.savingDescriptionInPenSavAlertDialogId);
            expectedSavingDate = itemView.findViewById(R.id.expectedSavingDateId);
        }
    }
}
