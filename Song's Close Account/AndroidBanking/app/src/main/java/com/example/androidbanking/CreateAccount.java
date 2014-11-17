package com.example.androidbanking;

import com.example.databasedemo.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.PopupWindow;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;
import android.content.Context;
import android.view.Gravity;


public class CreateAccount extends Activity{
	
	// EditText & Button Fields
	EditText user_txt, password_txt, pin_txt, credit_txt, debit_txt, savings_txt;
    String userLen, pwLen, pinLen;
	Button cButton;
	// Database
	DBAdapter DB;

	 protected void onCreate(Bundle savedInstanceState) {
		 

	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.createaccount);
	     
	        user_txt = (EditText)findViewById(R.id.usertxt);
	        password_txt = (EditText)findViewById(R.id.passwordtxt);
	        pin_txt = (EditText)findViewById(R.id.pintxt);
            //credit_txt = (EditText)findViewById(R.id.editText_Credit);
            //debit_txt = (EditText)findViewById(R.id.editText_Debit);
            //savings_txt = (EditText)findViewById(R.id.editText_Savings);

            openDB();

	        cButton = (Button)findViewById(R.id.createAccountButton);
	        

	        
	        cButton.setOnClickListener(new View.OnClickListener(){

	        	@Override
	        	public void onClick(View v) {

                    boolean thereISaMatch = false;

                    boolean isSecurePassword = true;
                    String passWordEntered = password_txt.getText().toString();

                    isSecurePassword = isLegalPassword(passWordEntered);

                    //int pin = Integer.parseInt(pin_txt.getText().toString());

                    if ( !isSecurePassword ) {

                        Context context = getApplicationContext();
                        CharSequence text = "Password must have one uppercase letter," +
                                "one number, and be at least 8 characters long.";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);

                    } else {
                        //Code For DB
                        int pin = Integer.parseInt(pin_txt.getText().toString());

                        long newID = DB.insertRow(user_txt.getText().toString(),
                                password_txt.getText().toString(),
                                pin,
                                0.00, 0.00, 0.00
                        );
                        Cursor cursor = DB.getRow(newID);
                        displayRecordSet(cursor);
                        cursor.close();

                        Intent mainIntent = new Intent(CreateAccount.this, MainActivity.class);
                        startActivity(mainIntent); // Not expecting sth back
                        finish();
                    }
                        // Use setText()/getText.toString() methods! on EditText Objects

                    }


	        	
	        });
	        
	        
	   }



        private boolean isLegalPassword(String password) {

        if (!password.matches(".*[A-Z].*")) return false;  //

        if (!password.matches(".*[a-z].*")) return false;

        if (!password.matches(".*\\d.*")) return false;

        if ((password.length()) < 8) return false;

        return true;
     }

/*
    // Security Methods
    private boolean securePW(String password) {
        boolean secure = true;

        if (!
                (pwLengthCheck(password) ||
                        (pwUpperCheck(password)) ||
                        (pwNumCheck(password)))) {

        }

        return secure;

    }
    private boolean pwLengthCheck(String password){}
    private boolean pwUpperCheck(String password){}
    private boolean pwNumCheck(String password){}

    static boolean isDigit(char ch) {
    return ch >= '0' && ch <= '9';
    }

    static boolean isLowerCase(char ch) {
    return ch >= 'a' && ch <= 'z';
    }

    static boolean isUpperCase(char ch) {
    return ch >= 'A' && ch <= 'Z';
}
*/


		@Override
        // DB Methods
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
                    String credit = cursor.getString(DBAdapter.COL_CREDIT);
                    String debit = cursor.getString(DBAdapter.COL_DEBIT);
                    String savings = cursor.getString(DBAdapter.COL_SAVINGS);
					
					// Append data to the message:
					message += "id=" + id
							   +", user=" + user
							   +", password=" + password
							   +", pin=" + pin
                            +", c=" + credit
                            +", d=" + debit
                            +", s=" + savings
							   +"\n";
				} while(cursor.moveToNext());
			}
			
			// Close the cursor to avoid a resource leak.
			cursor.close();
			
			displayText(message);
		}
}
