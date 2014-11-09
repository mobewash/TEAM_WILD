package com.example.androidbanking;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.databasedemo.DBAdapter;


public class ChangeBalance extends Activity {

    Bundle USER = new Bundle();

    public static final String USER_TAG = "Username";
    public static final String ID_TAG = "Num_ID";
    public static final String OPTION_TAG = "Option";
    public static final String DB1_TAG = "DB1";
    public static final String DB2_TAG = "DB2";
    public static final String TYPE_TAG = "AcctType"; // User or Teller
    public static final String CREDIT_DATA = "Credit";
    public static final String DEBIT_DATA = "Debit";
    public static final String ACCOUNT_TAG = "Account";

    private String Operation;
    private String Account;
    private String KEY_ID;
    private String W = "Withdrawal", D = "Deposit", T = "Transfer";
    private String user;
    private String pw;
    private Long id;

    private String user1;
    private String user2;
    private String user1acct;
    private String user2acct;

    private Double creditAmount = 0.00;
    private Double debitAmount = 0.00;
    private Double savingsAmount = 0.00;

    // View Objects
    Button transferButton;
    TextView pinEnter;
    TextView transferEnter;
    TextView optionText;
    TextView userAccount;
    TextView pwLine;
    TextView user2Account;
    TextView type1;
    TextView type2;
    //DB
    DBAdapter DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changebalance);


         //Retrieve Bundles
        USER = this.getIntent().getBundleExtra("USER");
        Operation = USER.getString(OPTION_TAG);
        Account = USER.getString(DB1_TAG);
        user = USER.getString(USER_TAG);
        pw = USER.getString("pw");
        id = USER.getLong(ID_TAG);

        // TextView Initializations
        userAccount = (TextView)findViewById(R.id.user1text);
        user2Account = (TextView)findViewById(R.id.user2text);
        type1 = (TextView)findViewById(R.id.accountType1);
        type2 = (TextView)findViewById(R.id.accountType2);
        //pwLine = (TextView)findViewById(R.id.pwLine);
        pinEnter = (TextView)findViewById(R.id.pinEnter);
        transferEnter = (TextView)findViewById(R.id.transferEnter);
        optionText = (TextView)findViewById(R.id.withdrawText);

        //Set Page Text
        optionText.setText("Please enter the amount you wish to " + Operation + "This is your id" + id);
        userAccount.setText(Account);

        //Open SQLite Database
        openDB();

        transferButton = (Button) findViewById(R.id.debitButton);
        transferButton.setText(Operation);
        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                // Operation decides sub/add
                //user = userAccount.getText().toString();
                //pw = pwLine.getText().toString();

                final Double amount = Double.parseDouble(transferEnter.getText().toString());
                if(amount <= 0) return;

                user1 = userAccount.getText().toString();
                user2 = user2Account.getText().toString();
                user1acct = type1.getText().toString();
                user2acct = type2.getText().toString();
                DB.transferBetween(user1, user2, user1acct, user2acct, amount);
                //DB.updateAccountValue(id, Account, amount);

                // if (Option.equals (Debit))
                // {
                //  amount = amount * -0.1;
                // }
                //

                //Cursor fundsCursor = DB.queryStuff(user, password); // Cursor points @ Key_ID
                // UNCOMMENT THIS BELOW
                // Cursor fundsCursor = DB.queryAll(user, pw); // Cursor points @ Key_ID

                /*
                if (Account.equals("Debit"))
                {
                    if (Operation.equals(W)) {
                        debitAmount = -amount;
                    }

                    else if (Operation.equals(D)) {
                        debitAmount = amount;
                    }
                }

                if (Account.equals("Savings"))
                {
                    if (Operation.equals(W)) {
                        savingsAmount = -amount;
                    }

                    else if (Operation.equals(D)) {
                        savingsAmount = amount;
                    }
                }
                */
                //pinEnter.setText(user + " " + pw);
                //displayRecordSet(fundsCursor);
/*
                fundsCursor.moveToFirst();
                pinEnter.setText(fundsCursor.getString(0)+
                                //+fundsCursor.getString(1)
                                //+fundsCursor.getString(2)
                                //+fundsCursor.getString(3)+ ", "
                                "Credit: "+fundsCursor.getString(4)+ ", "+
                                "Debit:" +fundsCursor.getString(5)+ ", "+
                                "Savings:" +fundsCursor.getString(6)+ ", +"
                );
*/

                /* Delete comment
                fundsCursor.moveToFirst();
                DB.updateRow(fundsCursor.getLong(0),
                        fundsCursor.getString(1),
                        fundsCursor.getString(2),
                        fundsCursor.getInt(3),
                        2,

                        fundsCursor.getDouble(5) + amount,
                        fundsCursor.getDouble(6) + savingsAmount);
                //pinEnter.setText(fundsCursor.getString(0));
                fundsCursor.moveToFirst();
                pinEnter.setText(fundsCursor.getString(0)+
                                //+fundsCursor.getString(1)
                                //+fundsCursor.getString(2)
                                //+fundsCursor.getString(3)+ ", "
                                "Credit: "+fundsCursor.getString(4)+ ", "+
                                "Debit:" +fundsCursor.getString(5)+ ", "+
                                "Savings:" +fundsCursor.getString(6)+ ", +"
                );


                */


                //pinEnter.setText(fundsCursor.getString(3));
                //pinEnter.setText(fundsCursor.getString(4));

/*

                Intent mainIntent = new Intent(v.getContext(), MainActivity.class);


                //manageIntent.putExtra("credit", fundsCursor.getString(3));
                //manageIntent.putExtra("debit",fundsCursor.getString(4));
                //manageIntent.putExtra("savings",fundsCursor.getString(5));
                startActivity(mainIntent); // Not expecting sth back
                //finish(); //Closes this activity so its not taking up space, go to Main Page
                fundsCursor.close();
                DB.close();
                //finish();



*/

            }


        });










        //TESTING


        //Operation = getIntent().getExtras().getString("Operation");
        //Account = getIntent().getExtras().getString("Account");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.change_balance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDB() {
        DB = new DBAdapter(this);
        DB.open();
    }

    private void displayRecordSet(Cursor cursor) {
        String message = "";
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String user = cursor.getString(DBAdapter.COL_USER);
                String password = cursor.getString(DBAdapter.COL_PASSWORD);
                String pin = cursor.getString(DBAdapter.COL_PIN);
                String credit = cursor.getString(DBAdapter.COL_CREDIT);
                String debit = cursor.getString(DBAdapter.COL_DEBIT);
                String savings = cursor.getString(DBAdapter.COL_SAVINGS);

                // Append data to the message:
                message += "id=" + id
                        + ", user=" + user
                        + ", password=" + password
                        + ", pin=" + pin
                        + ", c=" + credit
                        + ", d=" + debit
                        + ", s=" + savings
                        + "\n";
            } while (cursor.moveToNext());
        }
    }

}
