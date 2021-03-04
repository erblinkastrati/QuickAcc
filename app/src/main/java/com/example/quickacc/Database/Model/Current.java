package com.example.quickacc.Database.Model;

public class Current {

    private int currentId;
    private double bank;
    private double wallet;
    private double others;

    public Current(int currentId, double bank, double wallet, double others) {
        this.currentId = currentId;
        this.bank = bank;
        this.wallet = wallet;
        this.others = others;
    }

    public Current(double bank, double wallet, double others) {
        this.bank = bank;
        this.wallet = wallet;
        this.others = others;
    }

    public Current() {
    }

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public double getBank() {
        return bank;
    }

    public void setBank(double bank) {
        this.bank = bank;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public double getOthers() {
        return others;
    }

    public void setOthers(double others) {
        this.others = others;
    }
}
