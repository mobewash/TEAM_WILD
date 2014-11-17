// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package com.example.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

	/////////////////////////////////////////////////////////////////////
	//	Constants & Data
	/////////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";
	
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	/*
	 * CHANGE 1:
	 */
	// TODO: Setup your fields here:
	public static final String KEY_USER = "user";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_PIN = "pin";
    public static final String KEY_CREDIT = "credit";
    public static final String KEY_DEBIT = "debit";
    public static final String KEY_SAVINGS = "savings";
	
	// TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_USER = 1;
	public static final int COL_PASSWORD= 2;
	public static final int COL_PIN = 3;
    public static final int COL_CREDIT = 4;
    public static final int COL_DEBIT = 5;
    public static final int COL_SAVINGS = 6;

	
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_USER, KEY_PASSWORD, KEY_PIN,
            KEY_CREDIT, KEY_DEBIT, KEY_SAVINGS};
	
	// DB info: it's name, and the table we are using (just one).
	public static final String DATABASE_NAME = "MyDb";
	public static final String DATABASE_TABLE = "mainTable";
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 4;
	
	//SQL Language Input
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
			
			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			//	- Key is the column name you created above.
			//	- {type} is one of: text, integer, real, blob
			//		(http://www.sqlite.org/datatype3.html)
			//  - "not null" means it is a required field (must be given a value).
			// NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
			+ KEY_USER + " text not null, "
			+ KEY_PASSWORD + " string not null, "
			+ KEY_PIN + " Integer not null, "
            + KEY_CREDIT + " double not null, "
			+ KEY_DEBIT + " double not null, "
            + KEY_SAVINGS + " double not null "
			// Rest  of creation:
			+ ");";
	
	// Context of application who uses us.
	private final Context context;
	
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	// Add a new set of values to the database.
	public long insertRow(String name, String password, Integer pin, Double credit, Double debit,
                          Double savings)
    {
		/*
		 * CHANGE 3:
		 */		
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USER, name);
		initialValues.put(KEY_PASSWORD, password);
		initialValues.put(KEY_PIN, pin);
        initialValues.put(KEY_CREDIT, credit);
        initialValues.put(KEY_DEBIT, debit);
        initialValues.put(KEY_SAVINGS, savings);
		// Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}
	
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

    /*
    public Cursor queryDouble(long rowID, String Key) // Will make better version soon
    {
        Cursor rowCursor = this.getRow(rowID);
        rowCursor.getDouble()


    }
    */

	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowId, String name, String password, int pin, double credit,
                             double debit, double savings) {
		String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_USER, name);
		newValues.put(KEY_PASSWORD, password);
		newValues.put(KEY_PIN, pin);
        newValues.put(KEY_CREDIT, name);
        newValues.put(KEY_DEBIT, debit);
        newValues.put(KEY_SAVINGS, savings);
		
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}

    public Double updateAccountValue(long rowId, String Account, double amount) {
        String where = KEY_ROWID + "=" + rowId;
		/*
		 * CHANGE 4:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Get current amount, add to operation amount
        Cursor temp = this.queryAll(rowId);
        Double newAmount = 0.0;

        // Create row's data:
// Testing, uncomment other stuff
        ContentValues newValues = new ContentValues();
        if (Account.equals(KEY_CREDIT))
        {
            newAmount = amount + temp.getDouble(COL_CREDIT);
            newValues.put(KEY_CREDIT, newAmount);}
        else if (Account.equals(KEY_DEBIT))
        {
            newAmount = amount + temp.getDouble(COL_DEBIT);
            newValues.put(KEY_DEBIT, newAmount);}
        else if (Account.equals(KEY_SAVINGS))
        {
            newAmount = amount + temp.getDouble(COL_SAVINGS);
            newValues.put(KEY_SAVINGS, newAmount);}
        else
        {

            newValues.put(KEY_CREDIT, amount + amount);}

        // Insert it into the database.
        boolean eventTrue =  db.update(DATABASE_TABLE, newValues, where, null) != 0;
        temp.close(); // Close Cursor

        // Checking if update happened

        if (!(eventTrue))  // If update failed
        {newAmount = -1.0;}

        return newAmount;

    }
	
	public Cursor queryStuff(String username, String password)
	{
		String[] columns = {KEY_USER, KEY_PASSWORD, KEY_PIN, KEY_CREDIT, KEY_DEBIT, KEY_SAVINGS};
		//DATABASE_TABLE + "." + KEY_USER + " = \'" + a + "\' AND " + DATABASE_TABLE + "." + KEY_PASSWORD + " = \'" + b + "\'"
		return db.query(DATABASE_TABLE, 
				columns, 
			    KEY_USER + " = \'" + username + "\' AND " + KEY_PASSWORD + " = \'" + password + "\'"
                ,
				null, null, null, null);

	}

    public Cursor queryAll(String a, String b)
    {
        String[] columns = {KEY_ROWID, KEY_USER, KEY_PASSWORD, KEY_PIN, KEY_CREDIT, KEY_DEBIT, KEY_SAVINGS};
        //DATABASE_TABLE + "." + KEY_USER + " = \'" + a + "\' AND " + DATABASE_TABLE + "." + KEY_PASSWORD + " = \'" + b + "\'"
        return db.query(DATABASE_TABLE,
                columns,
                KEY_USER + " = \'" + a + "\' AND " + KEY_PASSWORD + " = \'" + b + "\'"
                ,
                null, null, null, null, null);

    }

    public Cursor queryAll(long row_id)
    {
        String[] columns = {KEY_ROWID, KEY_USER, KEY_PASSWORD, KEY_PIN, KEY_CREDIT, KEY_DEBIT, KEY_SAVINGS};
        //DATABASE_TABLE + "." + KEY_USER + " = \'" + a + "\' AND " + DATABASE_TABLE + "." + KEY_PASSWORD + " = \'" + b + "\'"
        Cursor temp =  db.query(DATABASE_TABLE,
                columns,
                KEY_ROWID + " = \'" + row_id + "\'"
                ,
                null, null, null, null, null);
        temp.moveToNext();
      //temp.moveToPosition((int)row_id);
        return temp;

    }

    public Cursor queryUser(String user)
    {
        String[] columns = {KEY_ROWID,KEY_USER, KEY_PASSWORD, KEY_PIN, KEY_CREDIT, KEY_DEBIT, KEY_SAVINGS};
        //DATABASE_TABLE + "." + KEY_USER + " = \'" + a + "\' AND " + DATABASE_TABLE + "." + KEY_PASSWORD + " = \'" + b + "\'"
         Cursor temp = db.query(DATABASE_TABLE,
                columns,
                KEY_USER + " = \'" + user + "\'"
                ,
                null, null, null, null);
        temp.moveToNext();
        return temp;

    }

    //transfer the money, return true if success.
    public boolean transferBetween(String user1, String user2, String acctType1, String acctType2, double amount)
    {

        int credit_row = 3;
        int debit_row = 4;
        long rowID1, rowID2;

        //song will add function if users are the same.
        //if they are the same, then give error message.
        Cursor firstCursor = queryUser(user1);
        rowID1 = firstCursor.getLong(0);

        Cursor secondCursor = queryUser(user2);
        rowID2 = secondCursor.getLong(0);
        double balance1 = 0,balance2 = 0, newBalance1 = 0, newBalance2 = 0;

        //retrieve user1 balance
        if (acctType1.equals(KEY_CREDIT))
            balance1 = firstCursor.getDouble(credit_row);
        else if(acctType1.equals(KEY_DEBIT))
            balance1 = firstCursor.getDouble(debit_row);

        if (balance1 < amount) return false;
        //retrieve user2 balance
        if (acctType2.equals(KEY_CREDIT))
            balance2 = firstCursor.getDouble(credit_row);
        else if(acctType2.equals(KEY_DEBIT))
            balance2 = firstCursor.getDouble(debit_row);

        newBalance1 = balance1 - amount;
        newBalance2 = balance2 + amount;
        updateAccountValue(rowID1, acctType1, newBalance1);
        updateAccountValue(rowID2, acctType2, newBalance2);
        return true;
    }







	
	//	Private Helper Classes:
	/////////////////////////////////////////////////////////////////////
	
	/**
	 * Private class which handles database creation and upgrading.
	 * Used to handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}
