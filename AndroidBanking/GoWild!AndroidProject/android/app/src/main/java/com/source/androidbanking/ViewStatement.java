package com.example.androidbanking;

/**
 * ViewStatement.java
 * Purpose: This class handles the displaying of a specific User account's transactions
 *          Headings include amount, username,
 *
 */
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;


public class ViewStatement extends Activity {

    //private String[] date_and_time= {"1/1", "1/2","1/3","1/4","1/5"};
    //private int[] amount = {-330, 73, -25, -21, 9};
    //private int[] balance = {1670, 1743, 1718, 1697, 1706 };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String spacing = "        ";
        String[] date_and_time = {"1/1", "1/2", "1/3", "1/4", "1/5"};
        int[] amount = {-330, 73, -25, -21, 9};
        int[] balance = {1670, 1743, 1718, 1697, 1706};

        UserSingleton user = UserSingleton.getInstance();
        /*String Json = getIntent().getExtras().getString("Json");
        try {
            user.jsonParser(Json);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        int i = getIntent().getExtras().getInt("accountPosition");

        String testString = " ";
        Toast.makeText(getApplicationContext(), testString, Toast.LENGTH_LONG);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstatement);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("");


        //int i = 0;

        final LinearLayout L_DateColumn = (LinearLayout) findViewById(R.id.DateColumn);
        final LinearLayout L_AmountColumn = (LinearLayout) findViewById(R.id.AmountColumn);
        final LinearLayout L_BalanceColumn = (LinearLayout) findViewById(R.id.BalanceColumn);


        //customized layout parameters
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                100, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 20);

        final LinearLayout.LayoutParams params_date = new LinearLayout.LayoutParams(
                300, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_date.setMargins(0, 0, 0, 20);

        final LinearLayout.LayoutParams params_balance = new LinearLayout.LayoutParams(
                100, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_balance.setMargins(60, 0, 0, 20);

        L_DateColumn.setLayoutParams(params_date);

        L_AmountColumn.setLayoutParams(params);
        L_BalanceColumn.setLayoutParams(params_balance);

        //final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        //        ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);


        int[] temp = user.getnumOfTransactions();

        for (int j = 0; j < temp[i]; j++) {
            //LinearLayout ll = new LinearLayout(getApplicationContext());
            //ll.setOrientation(LinearLayout.HORIZONTAL);

            // Create TextView
            TextView date = new TextView(getApplicationContext());
            date.setText(user.getTransactionDate(i, j));
            date.setSingleLine();
            L_DateColumn.addView(date);

            // Create Amount
            TextView myamount = new TextView(getApplicationContext());
            String Amount = String.format("$ %.2f", user.getTransactionAmount(i, j));
            myamount.setText("  " + Amount);
            L_AmountColumn.addView(myamount);

            // Create Balance

            TextView mybalance = new TextView(getApplicationContext());
            String Balance = String.format("%.2f", user.getTransactionBalance(i, j));
            mybalance.setText(" " + Balance);
            L_BalanceColumn.addView(mybalance);
            // Create TextView

            // Create TextView
            /*
            TextView tired = new TextView(getApplicationContext());
            tired.setText("  $"+ balance[j]);
            L_BalanceColumn.addView(tired);
            */

            /*
            TextView by = new TextView(getApplicationContext());
            by.setText(" " +user.getTransactionWho(i,j));
            L_BalanceColumn.addView(by);

            */
            //Create Master TextView
                    /*
                    + user.getTransactionId(i,j) +
                    user.getTransactionWho(i,j) + user.getTransactionAmount(i,j) +
                    user.getTransactionTransaction(i,j) + user.getTransactionBalance(i,j));
            L_sodonewiththis.addView(Master);

            */
            //Ll_Accounts.addView(ll);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_statement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        Intent i = new Intent(getApplicationContext(), MainActivity.class);

        super.onOptionsItemSelected(item);


        if (id == R.id.logout) {

            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
