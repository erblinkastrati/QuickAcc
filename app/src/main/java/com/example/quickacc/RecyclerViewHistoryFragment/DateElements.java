package com.example.quickacc.RecyclerViewHistoryFragment;

public class DateElements {

    private String monthName;
    private String monthNumber;
    private String year;

    public DateElements(String monthName, String monthNumber, String year) {
        this.monthName = monthName;
        this.monthNumber = monthNumber;
        this.year = year;
    }

    public DateElements() {
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(String monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
