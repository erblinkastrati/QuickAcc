package com.example.quickacc.RecyclerViewHistoryFragment.HistoryDetailsRecyclerViews;

public class ExpenseHistoryDetailsItem {

    private String itemName;
    private String itemAmount;
    private String timeAndDate;

    public ExpenseHistoryDetailsItem(String itemName, String itemAmount, String timeAndDate) {
        this.itemName = itemName;
        this.itemAmount = itemAmount;
        this.timeAndDate = timeAndDate;
    }

    public ExpenseHistoryDetailsItem() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getTimeAndDate() {
        return timeAndDate;
    }

    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }
}
