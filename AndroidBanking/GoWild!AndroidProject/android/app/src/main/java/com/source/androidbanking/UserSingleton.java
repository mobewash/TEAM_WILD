package com.example.androidbanking;


/**
 * UserSingleton.java
 *
 * Purpose: Singleton Object that serves as the model in the MVC design pattern for the Android Client
 *          Singleton fills all the UI with user's relevant information
 *          This class also combines Connect.class 's  code so that it can
 *               1. Send HTTP Post Requests
 *               2. Parse JSon Packet
 *               3. Fill the singleton with the parsed info.
 *               4. Retrieve needed user info easily
 */
import android.view.Gravity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by admin on 11/24/14.
 */
public class UserSingleton {

    // Limits
    private int MAXACCOUNTNUM;
    private String CREDIT = "credit";
    private String SAVINGS = "savings";

    // Request Strings
    final String LOGIN_REQUEST = "login";
    final String TRANSFER_REQUEST = "transfer";
    final String WITHDRAW_REQUEST = "withdraw";

    // Singleton
    private volatile static UserSingleton userInstance = null;


    //User Fields
    private String userType;// We should make this an object
    private String userName;
    private String password;  // Needed for Querying
    private boolean success;
    private int userID;

    // Accounts
    private int AccountNumbers[];
    private int numOfAccounts;
    private double AccountValues[];    // Related by index to Account Values
    private String AccountTypes[];  //  ex. First account==0, Debit.


    //Transactions
    private int numOfTransactions[];
    private Sextuple transactions[][];


    private int transactionID[];
    private String transactionBy[];
    private double transactionAmount[];
    private String transactionType[];
    private double transactionBalance[];

    // No-Access Dummy Constructor
    private UserSingleton() {
    }

    ;

    public static synchronized UserSingleton getInstance() {
        if (userInstance == null)               // Lazy Instantiation
        {
            userInstance = new UserSingleton();
        }

        return userInstance;
    }

    // Getter Methods
    public String getPassword() {
        return password;
    }

    public String getuserType() {
        return userType;
    }

    public String getuserName() {
        return userName;
    }

    public int getnumOfAccounts() {
        return numOfAccounts;
    }

    public int getuserID() {
        return userID;
    }

    public double[] getAccountValues() {
        return AccountValues;
    }

    public int[] getAccountNumbers() {
        return AccountNumbers;
    }

    public String[] getAccountTypes() {
        return AccountTypes;
    }

    public String getSingleAccountType(int index) {
        return AccountTypes[index];
    }

    public double getSingleAccountValue(int index) {
        return AccountValues[index];
    }

    public int getSingleAccountNumber(int index) {
        return AccountNumbers[index];
    }


    public String getTransactionDate(int i, int j) {
        return transactions[i][j].getDate(); //index i = which account, j = which row of transaction
    }

    public int getTransactionId(int i, int j) {
        return transactions[i][j].getId(); //index i = which account, j = which row of transaction
    }

    public String getTransactionWho(int i, int j) {
        return transactions[i][j].getWho(); //index i = which account, j = which row of transaction
    }

    public double getTransactionAmount(int i, int j) {
        return transactions[i][j].getAmount(); //index i = which account, j = which row of transaction
    }

    public String getTransactionTransaction(int i, int j) {
        return transactions[i][j].getTransaction(); //index i = which account, j = which row of transaction
    }

    public double getTransactionBalance(int i, int j) {
        return transactions[i][j].getBalance(); //index i = which account, j = which row of transaction
    }

    public int[] getnumOfTransactions() {
        return numOfTransactions; //index i = which account, j = which row of transaction
    }


    // Setter Methods
    protected void setPassword(String s) {
        password = s;
    }

    public void setuserType(int type) //
    {
        if (type == 0)
            userType = "Customer";
        if (type == 1)
            userType = "Teller";
        else
            userType = "Customer";
    }

    public void setuserName(String newuserName) {
        userName = newuserName;
    }

    public void setuserID(int newuserID) {
        userID = newuserID;
    }

    public void setnumOfAccounts(int newnumOfAccounts) {
        numOfAccounts = newnumOfAccounts;
    }

    public void setAccountValues(double[] newAccountValues) {
        AccountValues = newAccountValues;
    }

    public void setAccountTypes(String[] newAccountTypes) {
        AccountTypes = newAccountTypes;
    }


    public void setBobInfo() {
        MAXACCOUNTNUM = 3;
        userInstance = getInstance();
        userType = "Customer";
        userName = "Bob";
        userID = 1;
        numOfAccounts = 3;
        AccountValues = new double[]{1, 2, 3};    // Related by index to Account Values
        AccountTypes = new String[]{CREDIT, SAVINGS, CREDIT};  //  ex. First account==0, Debit.

    }


    public void jsonParser(String fullJson) throws JSONException {

        // Turn Json Packet into array
        // Turn each entry into array into a Json Packet
        // For Loop to Extract each Json Packet
        //A Check
        //fullJson = fullJson.substring(fullJson.indexOf("{"));
        //int test = 3;
        if (fullJson != null)

            try {
                JSONObject Json = new JSONObject(fullJson);

                JSONObject info = Json.getJSONObject("info");
                JSONArray Accounts = info.getJSONArray("accounts");

                ArrayList<AccountItem> accountData = new ArrayList<AccountItem>();


                //Populate UserSingleton!

                this.numOfAccounts = Accounts.length();

                this.numOfTransactions = new int[Accounts.length()];
                this.AccountNumbers = new int[Accounts.length()];
                this.AccountValues = new double[Accounts.length()];
                this.AccountTypes = new String[Accounts.length()];
                this.transactions = new Sextuple[Accounts.length()][];

                System.err.println("Number of Accounts: " + Accounts.length());

                for (int i = 0; i < Accounts.length(); i++) {

                    AccountTypes[i] = Accounts.getJSONObject(i).getString("account_type");
                    AccountValues[i] = Accounts.getJSONObject(i).getInt("balance");
                    AccountNumbers[i] = Accounts.getJSONObject(i).getInt("account_number");

                    System.err.println("Account: " + i + " Number of Transactions: " + Accounts.getJSONObject(i).getJSONArray("transactions").length());
                    if (Accounts.getJSONObject(i).getJSONArray("transactions") != null) {

                        this.transactions[i] = new Sextuple[Accounts.getJSONObject(i).getJSONArray("transactions").length()];
                        if (Accounts.getJSONObject(i).getJSONArray("transactions").length() != 0) {

                            this.numOfTransactions[i] = Accounts.getJSONObject(i).getJSONArray("transactions").length(); // Get number of transactions
                            for (int j = 0; j < Accounts.getJSONObject(i).getJSONArray("transactions").length(); j++) {
                                transactions[i][j] = new Sextuple(
                                        Accounts.getJSONObject(i).getJSONArray("transactions").getJSONObject(j).getString("created_at"),
                                        Accounts.getJSONObject(i).getJSONArray("transactions").getJSONObject(j).getInt("id"),
                                        Accounts.getJSONObject(i).getJSONArray("transactions").getJSONObject(j).getString("by"),
                                        Accounts.getJSONObject(i).getJSONArray("transactions").getJSONObject(j).getDouble("amount"),
                                        Accounts.getJSONObject(i).getJSONArray("transactions").getJSONObject(j).getString("transaction_type"),
                                        Accounts.getJSONObject(i).getJSONArray("transactions").getJSONObject(j).getDouble("balance"))
                                ;

                                System.err.println("Number of Transactions: " + Accounts.getJSONObject(i).getJSONArray("transactions").length());
                                System.err.println("Transaction: " + i + " ID: " + transactions[i][j].accountId + " amount: " + transactions[i][j].amount);
                            }
                        } else {
                            transactions[i] = null;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Exception WTF");
                e.printStackTrace();
            }

        /*
        JSONArray arr = obj.getJSONArray("" + 0);
        ArrayList<AccountItem> accountData = new ArrayList<AccountItem>();

        for (int i = 0; i < arr.length(); i++) {
            String accountType = arr.getJSONObject(i).getString("accountType");
            int balance = arr.getJSONObject(i).getInt("balance");


            accountData.add(new AccountItem(accountType, balance));
        }


        */
        //return accountData;
    }


    // Might be redundant
    public void setSuccess(String fullJson) throws JSONException {
        if (fullJson != null)

            try {
                JSONObject Json = new JSONObject(fullJson);

                boolean success = Json.getBoolean("success");
                this.success = success;
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    public void userInit(String usr, String pw)  // For initial query
    {
        String request = LOGIN_REQUEST;
        String email = usr;
        String password = pw;
        //String account_number = "1294759818";
        //String amount = "0";
        //String account_new = "n";
        //String deposit = "d";
        //String account_from = "1";
        //String account_to = "1";

        String[] params = new String[]{request, email, password};
                //account_from, account_to, amount, account_number};
        //sendConnection
        Connect test = new Connect();
        test.execute(params);
        String temp1 = null;
        try {
            temp1 = test.get();
            if (temp1 != null) {
                this.jsonParser(temp1);
                this.setSuccess(temp1);

                this.setPassword(password);
                this.setuserName(email);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Catch Incorrect User & Password
        try {
            System.out.println("YOLO!!!" + this.getnumOfAccounts());
            System.out.println("Get Baited!!!!" + this.getSingleAccountType(0) + this.getSingleAccountType(1));
            System.out.println("WHOOT" + this.getSuccess());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void queryRequest(String inRequest, String... parameters)  // For initial query
    {
        // These will be set in stone, format of API
        String email = this.getuserName();
        String password = this.getPassword();

        // Specific format ==! For each
        String accountnumber = "1294759818";
        String amount = "0";
        String account_new = "n";
        String deposit = "d";
        String account_from = "1";
        String account_to = "1";
        String request = inRequest;


        if (inRequest == TRANSFER_REQUEST)
        {
            account_from = parameters[0];
            account_to = parameters[1];
            amount = parameters[2];
            String[] params = new String[]{request, email, password, account_from, account_to, amount, accountnumber};

            //sendConnection
            Connect test = new Connect();

            test.execute(params);//


        }

        else if (inRequest == WITHDRAW_REQUEST)
        {
            amount = parameters[0];
            accountnumber = parameters[1];
            String[] params = new String[]{request, email, password, account_from, account_to, amount, accountnumber};

            //sendConnection
            Connect test = new Connect();
            test.execute(params);
            /*
            String temp1 = null;
            try {
                temp1 = test.get();
                if (temp1 != null) {
                    this.jsonParser(temp1);
                    this.setSuccess(temp1);

                    this.setPassword(password);
                    this.setuserName(email);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Catch Incorrect User & Password
            try {
                System.out.println("YOLO!!!" + this.getnumOfAccounts());
                System.out.println("Get Baited!!!!" + this.getSingleAccountType(0) + this.getSingleAccountType(1));
                System.out.println("WHOOT" + this.getSuccess());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            */
        }

        else if (inRequest == LOGIN_REQUEST)
        {
            //amount = parameters[0];
           // accountnumber = parameters[1];
            String[] params = new String[]{request, email, password, account_from, account_to, amount, accountnumber};
            //sendConnection
            Connect test = new Connect();
            test.execute(params);

            String temp1 = null;
            try {
                temp1 = test.get();
                if (temp1 != null) {
                    this.jsonParser(temp1);
                    this.setSuccess(temp1);

                    this.setPassword(password);
                    this.setuserName(email);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Catch Incorrect User & Password
            try {
                System.out.println("YOLO!!!" + this.getnumOfAccounts());
                System.out.println("Get Baited!!!!" + this.getSingleAccountType(0) + this.getSingleAccountType(1));
                System.out.println("WHOOT" + this.getSuccess());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }


    }







    public boolean getSuccess() {
        return this.success;
    }
}
