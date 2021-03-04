package com.example.quickacc.RecyclerViewHistoryFragment;

public class HistoryItem {

    private String monthNumber;
    private String monthName;
    private String year;

    public HistoryItem(String monthNumber, String monthName, String year) {
        this.monthNumber = monthNumber;
        this.monthName = monthName;
        this.year = year;
    }

    public HistoryItem() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(String monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
}
