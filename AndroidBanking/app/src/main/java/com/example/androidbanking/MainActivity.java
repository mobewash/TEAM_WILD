package com.example.androidbanking;

import com.example.databasedemo.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.databasedemo.DataBundle;

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


    Button CA_button;
	ImageButton login_button;
	EditText mp_usertxt, mp_passwordtxt;

	// Database
	DBAdapter DB;
    Bundle USER = new Bundle(); //Must instantiate or func. don't work


	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        CA_button = (Button) findViewById(R.id.button3);
        login_button = (ImageButton) findViewById(R.id.homepagelogin_button);
        mp_usertxt = (EditText) findViewById(R.id.mainpage_user_txt);
        mp_passwordtxt = (EditText) findViewById(R.id.mainpage_password_txt);


        CA_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateAccount.class);
                startActivityForResult(intent, 0);
                finish(); //Closes this activity
            }

        });


            openDB();

            login_button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    String userTxt = mp_usertxt.getText().toString();
                    String passTxt = mp_passwordtxt.getText().toString();
                    Cursor temp = DB.queryAll(userTxt, passTxt); // Cursor points @ Key_ID

                    try {
                        Intent intent = new Intent(MainActivity.this, HelloActivity.class);

                        temp.moveToNext();
                        USER.putString(USER_TAG, temp.getString(1));
                        USER.putDouble(CREDIT_DATA, temp.getDouble(4));
                        USER.putDouble(DEBIT_DATA, temp.getDouble(5));
                        USER.putLong(ID_TAG, temp.getLong(0));


                        intent.putExtra("USER", USER); //Bundle
                        startActivity(intent);
                        temp.close();
                        finish();


                    } catch (android.database.CursorIndexOutOfBoundsException e) {
                        Intent intent = new Intent(MainActivity.this, LoginErrorActivity.class);
                        startActivity(intent);
                        temp.close();
                        finish();

                    }
                }


            });


        }


	private void openDB() {
		DB = new DBAdapter(this);
		DB.open();
	}
	private void closeDB() {
		DB.close();
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
