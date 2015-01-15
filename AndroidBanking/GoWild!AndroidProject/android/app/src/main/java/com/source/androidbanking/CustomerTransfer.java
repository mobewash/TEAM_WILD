package com.example.androidbanking;
/*
 *
 * This class handles the CustomerTransfer page that
 * allows customers to transfer between their accounts and other accounts, given
 * that they know the account numbers to transfer to.
 * Upon clicking the transfer button, a request to transfer between the
 * two accounts will be sent.
 *
 */
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CustomerTransfer extends Activity {
    private static String[] paths = {"Saving 1", "Checking 2", "Saving 3"};

    private int from_account;
    private int to_account;
    private EditText accountNum;
    private EditText amount;
    private static String Json = null;
    private UserSingleton user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transferfunds);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("");
        //get view id
        Button transfer = (Button) findViewById(R.id.transferButton);
        accountNum = (EditText) findViewById(R.id.accountNum);
        amount = (EditText) findViewById(R.id.amountText);


        user = UserSingleton.getInstance();

        //Json = getIntent().getExtras().getString("Json");
/*
        try {
            user.jsonParser(Json);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
*/
        paths = new String[user.getnumOfAccounts()];

        for (int i = 0; i < user.getnumOfAccounts(); i++) {
            paths[i] = user.getSingleAccountType(i) + " " + (i + 1);
        }

        //spinner setup
        final Spinner selectAcct = (Spinner) findViewById(R.id.selectAccountSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.singleitem, paths);
        adapter.setDropDownViewResource(R.layout.singleitem);
        selectAcct.setAdapter(adapter);

        //set spinner on item click listener
        selectAcct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                from_account = user.getSingleAccountNumber(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //transfer button on click listener setup
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Double transferAmount = Double.parseDouble(amount.getText().toString());
                    String amount = "" + transferAmount;

                    String account_from = from_account + "";
                    String account_to = Integer.parseInt(accountNum.getText().toString()) + "";
                    String request = "transfer";

                    //Connect connection = new Connect();

                    //String email = user.getuserName();
                    //String password = user.getPassword();
                    //String account_number = "1294759818";
                    //System.err.println("transfer amount is: " + transferAmount);


                    //String account_new = "n";
                    //String deposit = "d";
                    //String transfer = "t";
                     // DEPENDENT ON THIS ACTIVITY


                    //String[] params = new String[]{request,email, password, account_number, amount, account_new, deposit, account_from, account_to};
                    //String[] params = new String[]{request, email, password, account_from, account_to, amount, account_number};

                    user.queryRequest(request,account_from, account_to, amount);

                    //connection.execute(params);


                    // if (Correctly went through) change below
                    if (true) {

                        Intent updateIntent = new Intent(CustomerTransfer.this, CustomerMain.class);

                        startActivity(updateIntent);

                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();

                    Toast.makeText(getApplication(), "Please Enter all fields", Toast.LENGTH_LONG);
                }
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_customer_transfer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent i = new Intent(getApplicationContext(), MainActivity.class);

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        super.onOptionsItemSelected(item);

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.logout) {

            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
