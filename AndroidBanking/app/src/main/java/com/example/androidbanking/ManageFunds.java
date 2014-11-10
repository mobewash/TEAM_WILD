package com.example.androidbanking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ManageFunds extends Activity {



    //Bundle Fields
    public static final String USER_TAG = "Username";
    public static final String ID_TAG = "Num_ID";
    public static final String OPTION_TAG = "Option";
    public static final String DB1_TAG = "DB1";
    public static final String DB2_TAG = "DB2";
    public static final String TYPE_TAG = "AcctType"; // User or Teller
    public static final String CREDIT_DATA = "CreditAccount";
    public static final String DEBIT_DATA = "DebitAccount";
    // Fields
    //final Bundle USER = this.getIntent().getBundleExtra("USER");
    // These fields are passed in along with onclick to let "select account" know
    // which option is being chosen
    final private String DEBIT_OP = "debit";
    final private String CREDIT_OP = "credit";
    final private String TRANSFER_OP = "transfer";


    private String user;
    private String pw;


    Button debitButton;
    Button creditButton;
    Button transferButton;
    //Button creditButton;
    //Button transferButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_funds);

        final Bundle USER = this.getIntent().getBundleExtra("USER");

            debitButton = (Button)findViewById(R.id.withdrawButton);
            creditButton = (Button)findViewById(R.id.depositButton);
            transferButton = (Button)findViewById(R.id.transferButton);



            debitButton.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    Intent selectAcctIntent = new Intent(v.getContext(), SelectAccount.class);
                    USER.putString(OPTION_TAG, DEBIT_OP); // Replaces current option
                    selectAcctIntent.putExtra("USER", USER);
                    startActivity(selectAcctIntent); // Not expecting sth back
                    finish(); //Closes this activity so its not taking up space, go to Main Page
                }

            });

            creditButton.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    Intent selectAcctIntent = new Intent(v.getContext(), SelectAccount.class);
                    USER.putString(OPTION_TAG, CREDIT_OP); // Replaces current option
                    selectAcctIntent.putExtra("USER", USER);
                    startActivity(selectAcctIntent); // Not expecting sth back
                    finish(); //Closes this activity so its not taking up space, go to Main Page
                }
            });

            transferButton.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                Intent selectAcctIntent = new Intent(v.getContext(), ChangeBalance.class);
                USER.putString(OPTION_TAG, TRANSFER_OP); // Replaces current option
                selectAcctIntent.putExtra("USER",USER);
                startActivity(selectAcctIntent); // Not expecting sth back
                finish(); //Closes this activity so its not taking up space, go to Main Page
                                        }
                       });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_funds, menu);
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
}
