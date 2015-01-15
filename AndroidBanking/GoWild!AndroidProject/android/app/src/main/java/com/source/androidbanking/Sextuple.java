/**
 * This Class facilitates an organized temporary storage of the parsed Json,
 * specifically for recording Transaction information, including the
 * date, account id, who made the transaction, what amount, the transaction itself,
 * and the resulting balance.
 */


package com.example.androidbanking;

/**
 * Created by Kevin on 12/6/2014.
 */
public class Sextuple {
    public String date; // date
    public int accountId; //account id
    public String who; //by who
    public double amount; // amount
    public String transaction; //transaction
    public double balance; //balance


    public Sextuple(String f, int a, String b, double c, String d, double e) {
        this.date = f;
        this.accountId = a;
        this.who = b;
        this.amount = c;
        this.transaction = d;
        this.balance = e;

    }

    public String getDate() {
        return this.date;
    }

    ;

    public int getId() {
        return this.accountId;
    }

    ;

    public String getWho() {
        return this.who;
    }

    ;

    public double getAmount() {
        return this.amount;
    }

    ;

    public String getTransaction() {
        return this.transaction;
    }

    ;

    public double getBalance() {
        return this.balance;
    }

    ;


}
