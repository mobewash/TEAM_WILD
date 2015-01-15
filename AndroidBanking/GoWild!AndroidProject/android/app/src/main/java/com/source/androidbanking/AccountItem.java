
package com.example.androidbanking;
/**
 * This Class facilitates an organized temporary storage of the parsed Json,
 * for recording account informatoin.
 */

public class AccountItem {

    public String accountType;
    public int balance;

    public AccountItem(String acctType, int money) {
        accountType = acctType;
        balance = money;
    }
}
