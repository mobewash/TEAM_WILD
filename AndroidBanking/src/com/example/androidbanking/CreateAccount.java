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
import android.widget.TextView;


public class CreateAccount extends Activity{
	
	// EditText & Button Fields
	EditText user_txt, password_txt, pin_txt;
	Button cButton;
	// Database
	DBAdapter DB;
	
	
	 protected void onCreate(Bundle savedInstanceState) {
		 

	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.createpage);
	     
	        user_txt = (EditText)findViewById(R.id.usertxt);
	        password_txt = (EditText)findViewById(R.id.passwordtxt);
	        pin_txt = (EditText)findViewById(R.id.pintxt);
	        
	        cButton = (Button)findViewById(R.id.createAccountButton);
	        
	        openDB();
	        
	        cButton.setOnClickListener(new View.OnClickListener(){
	        	@Override
	        	
	        	public void onClick(View v){

	        				 //Code For DB
	        				 long newID = DB.insertRow(user_txt.getText().toString(),password_txt.getText().toString(),pin_txt.getText().toString());
	        				 Cursor cursor = DB.getRow(newID);
	        				 displayRecordSet(cursor);
	        				 cursor.close();
	        				 
	        		// Use setText()/getText.toString() methods! on EditText Objects
	        				 Intent mainIntent = new Intent(v.getContext(),MainActivity.class);
	        				 startActivity(mainIntent); // Not expecting sth back
	        				 finish(); //Closes this activity so its not taking up space, go to Main Page
	        	}
	       
	        	
	        });
	        
	        
	    }
	 

		@Override
		protected void onDestroy() {
			super.onDestroy();	
			closeDB();
		}


		private void openDB() {
			DB = new DBAdapter(this);
			DB.open();
		}
		private void closeDB() {
			DB.close();
		}

		
		
		private void displayText(String message) {
	        TextView textView = (TextView) findViewById(R.id.passwordtxt);
	        textView.setText(message);
		}
		
		
		
		// Display an entire recordset to the screen.
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
					
					// Append data to the message:
					message += "id=" + id
							   +", user=" + user
							   +", password=" + password
							   +", pin=" + pin
							   +"\n";
				} while(cursor.moveToNext());
			}
			
			// Close the cursor to avoid a resource leak.
			cursor.close();
			
			displayText(message);
		}
}
