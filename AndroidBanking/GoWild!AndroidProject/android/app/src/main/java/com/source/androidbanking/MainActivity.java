package com.example.androidbanking;
/**
 * This Main class handles Login Page activities,
 * setting an error toast if an incorrect Username/ Password
 * combination is sent.  Otherwise, it fires a successful
 * query for the user's account information and leads
 * to the customer's main page.
 *
 */
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    //Bundle Fields
    private static final String USER_TAG = "Username";
    private static final String ID_TAG = "Num_ID";
    private static final String OPTION_TAG = "Option";
    private static final String DB1_TAG = "DB1";
    private static final String DB2_TAG = "DB2";
    private static final String TYPE_TAG = "AcctType"; // User or Teller
    private static final String CREDIT_DATA = "Credit";
    private static final String DEBIT_DATA = "Debit";


    UserSingleton testuser = UserSingleton.getInstance();
    String userTxt;
    String passTxt;

    // Declare Views
    TextView CA_button;
    Button login_button;
    EditText mp_usertxt, mp_passwordtxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("");

        //get id of each view on the xml
        login_button = (Button) findViewById(R.id.homepagelogin_button);
        mp_usertxt = (EditText) findViewById(R.id.mainpage_user_txt);
        mp_passwordtxt = (EditText) findViewById(R.id.mainpage_password_txt);
        login_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                userTxt = mp_usertxt.getText().toString();
                passTxt = mp_passwordtxt.getText().toString();


                testuser.userInit(userTxt, passTxt);
                if (testuser.getSuccess())
                {
                    Intent customerIntent = new Intent(MainActivity.this, CustomerMain.class);
                    startActivity(customerIntent);
                }
                else  {
                    Toast.makeText(getApplicationContext(), "Incorrect Email or Password", Toast.LENGTH_LONG).show();
                }
                System.err.println("Number of accounts is " + testuser.getnumOfAccounts());
                // Cursor temp = DB.queryAll(userTxt, passTxt); // Cursor points @ Key_ID

                // Run populate method!
                // testuser.QueryPopulate // Query and populate ---> at the same time, just throws exception else
/*

                String email = userTxt;
                String password = passTxt;


                String account_number = "1294759818";
                String amount = "0";
                String account_new = "n";
                String deposit = "d";
                String account_from = "1";
                String account_to = "1";
                String request = "login";

                String[] params = new String[]{request, email, password, account_from, account_to, amount, account_number};


                //sendConnection
                Connect test = new Connect();
                test.execute(params);


                String temp1 = null;
                try {
                    temp1 = test.get();
                    if (temp1 != null) {
                        testuser.jsonParser(temp1);
                        testuser.setSuccess(temp1);
                        testuser.setPassword(password);
                        testuser.setuserName(email);
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
                    System.out.println("YOLO!!!" + testuser.getnumOfAccounts());
                    System.out.println("Get Baited!!!!" + testuser.getSingleAccountType(0) + testuser.getSingleAccountType(1));
                    System.out.println("WHOOT" + testuser.getSuccess());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast t = Toast.makeText(getApplicationContext(), "Incorrect Username or Password", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 100);
                    t.show();


                }


                if (testuser.getSuccess()) {
                    // Check if parse for Teller

                    // Send to Customer page
                    Intent customerIntent = new Intent(MainActivity.this, CustomerMain.class);

                    try {
                        testuser.jsonParser(temp1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //customerIntent.putExtra("Json", temp1);
                    //customerIntent.putExtra("user", userTxt);
                    //customerIntent.putExtra("password", passTxt);
                    startActivity(customerIntent);


                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Email or Password", Toast.LENGTH_LONG).show();
                }

*/
            }


        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
