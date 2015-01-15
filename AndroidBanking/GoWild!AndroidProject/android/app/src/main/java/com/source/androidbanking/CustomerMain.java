
package com.example.androidbanking;

/*
 * This class handles the customer's main page upon login.
 * It immediately displays the customer's existing accounts and balances,
 * and allows for the options to view each account's transactions.
 *
 * A customer can also withdraw or transfer money, leading to three different activities.
 *
 * A Refresh button exists in the top right corner to sends a fresh HTTP post request
 * to fill the Singleton class with updated customer info.
 *
 */

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

//import com.example.model.UserSingleton;


public class CustomerMain extends Activity {

    Intent[] inArray;
    int array[];
    int counter = 0;
    //public int currentPosition = 0;
    static String JsonStatic = null;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customermain);


        // Layout Setup
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("");

        //Layout of the accounts that the customer has
        final LinearLayout Ll_Accounts = (LinearLayout) findViewById(R.id.dynamiclyCreated);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 20);


        // Instantiate UserSingleton
        final UserSingleton Bob = UserSingleton.getInstance();

        //Transfer button setup
        Button transfer = (Button) findViewById(R.id.transferBtn);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userTxt = Bob.getuserName();
                String passTxt = Bob.getPassword();

                //go to transfer page
                Intent mfIntent = new Intent(CustomerMain.this, CustomerTransfer.class);
                /*if (JsonStatic != null) {
                    //put the information needed for transfer page
                    mfIntent.putExtra("Json", JsonStatic);
                }*/
                startActivity(mfIntent);
            }

        });

        //withdraw button setup
        Button bWithdraw = (Button) findViewById(R.id.withdrawButton);
        bWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mfIntent = new Intent(CustomerMain.this, CustomerWithdrawal.class);
                if (JsonStatic != null) {

                    String userTxt = Bob.getuserName();
                    String passTxt = Bob.getPassword();
                }
                startActivity(mfIntent);
            }
        });

        //spinner setup
        Spinner spinner = (Spinner) findViewById(R.id.spinnerDown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.customeroptions_array, R.layout.singleitem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        String Json = null;
        try {
            Json = getIntent().getExtras().getString("Json");
            JsonStatic = Json;
        } catch (NullPointerException e) {
            e.printStackTrace();
            // Should fall through this after login
        }

        try {
            Bob.jsonParser(Json);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        for (int j = 0; j < Bob.getnumOfAccounts(); j++) {
            // Create LinearLayout
            final int currentPosition = j;

            // Create LinearLayout
            LinearLayout ll = new LinearLayout(getApplicationContext());


            //each dynamically created accounts layout setup
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setBackgroundColor(Color.BLACK);
            //layout parameter for single item
            LinearLayout.LayoutParams pram_single = new LinearLayout.LayoutParams(
                    100, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            pram_single.setMargins(10, 10, 10, 10);

            //layout parameter for non-single item
            LinearLayout.LayoutParams pram_notsingle = new LinearLayout.LayoutParams(
                    250, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            pram_notsingle.setMargins(10, 10, 10, 10);


            ll.setLayoutParams(params);


            // Create TextView of Account type
            TextView product = new TextView(getApplicationContext());
            product.setText(Bob.getSingleAccountType(j));

            product.setTextSize(10);
            product.setSingleLine();
            product.setBackgroundResource(R.drawable.bg3);
            product.setLayoutParams(pram_notsingle);
            product.setTextColor(Color.BLACK);

            ll.addView(product);

            //create account number
            TextView accountNum = new TextView(getApplicationContext());
            accountNum.setText("" + Bob.getSingleAccountNumber(j));
            ll.addView(accountNum);
            accountNum.setTextSize(10);
            accountNum.setLayoutParams(pram_notsingle);
            accountNum.setTextColor(Color.GRAY);
            accountNum.setPadding(50, 30, 0, 0);

            // Create balance Text view field
            TextView price = new TextView(getApplicationContext());
            price.setText("  $" + "  " + Bob.getSingleAccountValue(j));
            ll.addView(price);
            price.setTextSize(10);
            price.setLayoutParams(pram_notsingle);
            price.setTextColor(Color.WHITE);
            price.setPadding(50, 30, 0, 0);

            // Create Button fields
            final Button btn = new Button(getApplicationContext());
            // Give button an ID
            btn.setId(j + 1);

            btn.setTextColor(Color.BLACK);
            // set the layoutParams on the button
            btn.setLayoutParams(pram_notsingle);
            btn.setTextSize(10);



            btn.setBackgroundResource(R.drawable.bg1);
            btn.setText(">>>");

            // Set click listener for button
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent viewIntent = new Intent(CustomerMain.this, ViewStatement.class);

                    System.err.println("current position is: " + currentPosition);
                    viewIntent.putExtra("accountPosition", currentPosition);
                    //viewIntent.putExtra("Json", getIntent().getExtras().getString("Json"));

                    startActivity(viewIntent);


                }
            });

            //Add button to LinearLayout
            ll.addView(btn);
            Ll_Accounts.addView(ll);

            //currentPosition = 0;

            counter++;
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {           // Menu
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Intent i = new Intent(getApplicationContext(), MainActivity.class);

        final UserSingleton Bob = UserSingleton.getInstance();

        super.onOptionsItemSelected(item);

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.logout) {

            startActivity(i);
            return true;
        }

        if (id == R.id.refreshButton) {
            try {

                Bob.queryRequest("login");
                Intent refreshIntent = new Intent(CustomerMain.this, CustomerMain.class);
                startActivity(refreshIntent);

            } catch (NullPointerException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }


        return super.onOptionsItemSelected(item);
    }

    public void TransferFundsOption() {
        Intent transferIntent = new Intent(CustomerMain.this, CustomerTransfer.class);
        finish();
    }

    public void LogoutOption() {
        Intent mainIntent = new Intent(CustomerMain.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    public void ViewStatementOption() {
        Intent viewIntent = new Intent(CustomerMain.this, ViewStatement.class);
        startActivity(viewIntent);
        finish();
    }

}
