package com.example.quickacc.RecyclerViewHistoryFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickacc.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private List<HistoryItem> listItems;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public HistoryAdapter(Context context, List<HistoryItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recycler_item, parent, false);

        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {

        HistoryItem historyItem = listItems.get(position);

        holder.monthNumber.setText(historyItem.getMonthNumber());
        holder.monthName.setText(historyItem.getMonthName());
        holder.yearHistory.setText(historyItem.getYear());


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView monthNumber;
        public TextView monthName;
        public TextView yearHistory;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            monthNumber = itemView.findViewById(R.id.monthNumberTextViewId);
            monthName = itemView.findViewById(R.id.monthNameTextViewId);
            yearHistory = itemView.findViewById(R.id.yearTextViewHistoryId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
