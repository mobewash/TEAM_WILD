package com.example.androidbanking;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.example.databasedemo.DBAdapter;


public class ChangeBalance extends Activity {

    Bundle USER = new Bundle();


    final private String DEBIT_OP = "debit";
    final private String CREDIT_OP = "credit";
    final private String TRANSFER_OP = "transfer";


    // From DB Adapter
    public static final String KEY_CREDIT = "credit";
    public static final String KEY_DEBIT = "debit";
    public static final String KEY_SAVINGS = "savings";


    public static final String USER_TAG = "Username";
    public static final String ID_TAG = "Num_ID";
    public static final String OPTION_TAG = "Option";
    public static final String DB1_TAG = "DB1";
    public static final String DB2_TAG = "DB2";
    public static final String TYPE_TAG = "AcctType"; // User or Teller


    public static final String CREDIT_DATA = "Credit";          // Bundle Tag for Credit Balance
    public static final String DEBIT_DATA = "Debit";            // Bundle Tag for Debit Balance
    public static final String ACCOUNT_TAG = "Account";


    public static Double current_data;

    private String Operation;
    private String Account; // Account type
    private String KEY_ID;
    private String W = "Withdrawal", D = "Deposit", T = "Transfer";
    private String user;
    private String pw;
    private Long numid;


    // View Objects
    Button withdrawButton;
    TextView pinEnter;
    TextView withdrawEnter;
    TextView optionText;
    TextView user1Account;
    TextView user2Account;
    TextView accType1;
    TextView accType2;

    //DB
    DBAdapter DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // If Teller, set this ---> ???
        setContentView(R.layout.changebalance);


         //Retrieve Bundles
        USER = this.getIntent().getBundleExtra("USER");
        Operation = USER.getString(OPTION_TAG);
        Account = USER.getString(DB1_TAG);
        user = USER.getString(USER_TAG);
        pw = USER.getString("pw");
        numid = USER.getLong(ID_TAG);



        // TextView Initializations
        user1Account = (TextView)findViewById(R.id.user1_editText);
        user2Account = (TextView)findViewById(R.id.user2_editText);
        accType1 = (TextView)findViewById(R.id.accType1);
        accType2 = (TextView)findViewById(R.id.accType2);

        pinEnter = (TextView)findViewById(R.id.pinEnter);
        withdrawEnter = (TextView)findViewById(R.id.withdrawEnter);
        optionText = (TextView)findViewById(R.id.withdrawText);

        //Set Page Text
        optionText.setText("Please enter the amount you wish to " + Operation);
        user1Account.setText(Account);


        withdrawButton = (Button) findViewById(R.id.withdrawButton);
        withdrawButton.setText(Operation);
        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Double amount = Double.parseDouble(withdrawEnter.getText().toString());
                Double newBalance = 0.00; // Will hold balance after transaction

                //Open SQLite Database
                openDB();

                // If Debiting or Transferring, invert sign of "amount"
                if (Operation.equals (DEBIT_OP) ||  Operation.equals (TRANSFER_OP))
                {amount = amount * -1;
                }

                // Update User Account Balance if not "Transfer

                if (Operation.equals(TRANSFER_OP))
                {

                    String user1 = user1Account.getText().toString();
                    String user2 = user2Account.getText().toString();
                    String acc1 = accType1.getText().toString();
                    String acc2 = accType2.user2Account.getText().toString();

                    //transferBetween(user1, user2, String user1acc, String user2acc, double amount)

/*
                    if (Account.equals(KEY_CREDIT))
                    {USER.putDouble(CREDIT_DATA, newBalance );}
                    else if (Account.equals(KEY_DEBIT))
                    {USER.putDouble(DEBIT_DATA, newBalance );}
*/
                }
                


                else
                {
                    newBalance = DB.updateAccountValue(numid, Account, amount);

                    if (Account.equals(KEY_CREDIT))
                    {USER.putDouble(CREDIT_DATA, newBalance );}

                    else if (Account.equals(KEY_DEBIT))
                    {USER.putDouble(DEBIT_DATA, newBalance);}
                }






                //pinEnter.setText( "New Balance: " + newBalance + "Acc is " + Account);


                // Apparently intents live as long as main sending activity is alive, \
                // so bundles aren't updated
                Intent manageIntent = new Intent(v.getContext(), HelloActivity.class);
                manageIntent.putExtra("USER", USER); // Pass Bundle


                //pinEnter.setText(newBalance + " Acc is " + Account +  USER.getDouble(CREDIT_DATA));
                startActivity(manageIntent); // Not expecting sth back
                finish();
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
                message += "numid=" + id
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
