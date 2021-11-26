package com.anahuac.calidad.tareaUnitTest;

public class Account {

    int balance, zone;
    String holder;
    AlertListener alerts;

    /**
     * <h1>Constructors</h1>
     * Le agregué un constructor vacío
     */
    public Account(String holder, int initialBalance, AlertListener alerts){
        this.holder = holder;
        this.balance = initialBalance;
        this.alerts = alerts;
    }

    public Account() {
    }

    /**
     * <h1>Getters</h1>
     */
    public int getBalance() {
        return this.balance;
    }

    public String getHolder(){
        return this.holder;
    }

    public int getZone() {
        if (this.zone < 4 && this.zone > 0){
            return zone;
        } else {
            return 0;
        }
    }

    /**
     * <h1>Setters</h1>
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void setAlerts(AlertListener alerts) {
        this.alerts = alerts;
    }

    /**
     * <h1>Methods</h1>
     * Solo le agregué deposit para hacer depositos
     */
    void debit(int balance) {
        this.balance -= balance;
        if(this.balance < 100){
            this.alerts.sendAlert(this.holder+", your account balance is below 100");
        }
    }

    void credit(int balance) {
        this.balance += balance;
    }

    void setAlertListener(AlertListener listener){
        this.alerts = listener;
    }

    int deposit(int deposit){
        return this.balance + deposit;
    }
}
