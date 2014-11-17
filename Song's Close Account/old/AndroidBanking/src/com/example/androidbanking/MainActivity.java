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

public class MainActivity extends Activity {

	Button CA_button;
	ImageButton login_button;
	EditText mp_usertxt, mp_passwordtxt;
	// Database
	DBAdapter DB;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        CA_button = (Button) findViewById(R.id.button3);
        login_button = (ImageButton)findViewById(R.id.homepagelogin_button);
        mp_usertxt = (EditText)findViewById(R.id.mainpage_user_txt);
        mp_passwordtxt = (EditText)findViewById(R.id.mainpage_password_txt);
        
        
        CA_button.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v)
        	{
        		Intent intent = new Intent(v.getContext(),CreateAccount.class);
        		startActivityForResult(intent,0);
        		finish(); //Closes this activity
        	}
        	
        });
        // Verify Login
 
     //   try{

        openDB();

       login_button.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v)
        	{
        		String userTxt = mp_usertxt.getText().toString();
        		String passTxt = mp_passwordtxt.getText().toString();

        		// while (sql database == still has entries, loop)
        		//if ((userTxt.equals)||())// user Txt and passtxt match
        			//Then just set content view?  or start new activity
        		
        		Cursor temp = DB.queryStuff(userTxt, passTxt);
        		
        		temp.moveToNext();
        	
        		try
        		{
        			Intent intent = new Intent(MainActivity.this, HelloActivity.class);
        			intent.putExtra("name", temp.getString(0));
        			startActivity(intent);
        		}
        		catch (android.database.CursorIndexOutOfBoundsException e)
        		{
        			Intent intent = new Intent(MainActivity.this, LoginErrorActivity.class);
        			startActivity(intent);

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
