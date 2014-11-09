package com.example.androidbanking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class SelectAccount extends Activity implements OnItemSelectedListener {

    public static final String USER_TAG = "Username";
    public static final String ID_TAG = "Num_ID";
    public static final String OPTION_TAG = "Option";
    public static final String DB1_TAG = "DB1";  //Account Being Operated On
    public static final String DB2_TAG = "DB2";
    public static final String TYPE_TAG = "AcctType"; // User or Teller
    public static final String CREDIT_DATA = "Credit";
    public static final String DEBIT_DATA = "Debit";
    public static final String ACCOUNT_TAG = "Account"; // Might not need
    private Spinner spinner;
    private static final String[]paths = {"Choose account",CREDIT_DATA, DEBIT_DATA};
    private String Operation;
    private String Account;

    // Add account names here
    private String Credit;
    //private String Debit;
    //private String Savings;
    private String user;
    private String pw;

    Bundle USER = new Bundle();


    Button changeCredit;
    Button changeDebit;
    Button changeSavings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectaccount);

        USER = getIntent().getBundleExtra("USER");

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(SelectAccount.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        changeCredit = (Button)findViewById(R.id.changeCredit);
        //TESTING
        //changeCredit.setText(USER.getString(OPTION_TAG));
        //TESTING
        changeCredit.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                Intent selectAcctIntent = new Intent(v.getContext(),ChangeBalance.class);
                Account = "Credit";
                USER.putString(DB1_TAG, Account);
                selectAcctIntent.putExtra("USER", USER);
                startActivity(selectAcctIntent); // Not expecting sth back
                finish(); //Closes this activity so its not taking up space, go to Main Page
            }


        });

        changeDebit = (Button)findViewById(R.id.changeDebit);
        changeDebit.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                Intent selectAcctIntent = new Intent(v.getContext(),ChangeBalance.class);
                Account = "Debit";
                USER.putString(DB1_TAG, Account);
                selectAcctIntent.putExtra("USER", USER);
                startActivity(selectAcctIntent); // Not expecting sth back
                finish(); //Closes this activity so its not taking up space, go to Main Page
            }


        });


        changeSavings = (Button)findViewById(R.id.changeSavings);
        changeSavings.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                Intent selectAcctIntent = new Intent(v.getContext(),ChangeBalance.class);
                Account = "Savings";
                USER.putString(DB1_TAG, Account);
                selectAcctIntent.putExtra("USER", USER);
                startActivity(selectAcctIntent); // Not expecting sth back
                finish(); //Closes this activity so its not taking up space, go to Main Page
            }


        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.change_funds, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                Intent selectAcctIntent = new Intent(this,ChangeBalance.class);
                Account = "Credit";
                USER.putString(DB1_TAG, Account);
                selectAcctIntent.putExtra("USER", USER);
                startActivity(selectAcctIntent); // Not expecting sth back
                finish(); //Closes this activity so its not taking up space, go to Main Page
            case 2:
                Intent selectAcctIntent1 = new Intent(this,ChangeBalance.class);
                Account = "Debit";
                USER.putString(DB1_TAG, Account);
                selectAcctIntent1.putExtra("USER", USER);
                startActivity(selectAcctIntent1); // Not expecting sth back
                finish(); //Closes this activity so its not taking up space, go to Main Page
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }
}
