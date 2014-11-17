package com.example.databasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.content.ContentValues;



/**
 * Created by admin on 11/6/14.
 */



public class DataBundle {

    private static final String USER_TAG = "Username";
    private static final String ID_TAG = "Num_ID";
    private static final String OPTION_TAG = "Option";
    private static final String DB1_TAG = "DB1";
    private static final String DB2_TAG = "DB2";


    Bundle USERID;
    Bundle OPTION;
    Bundle PROTECTED;


    public DataBundle(){}

    public DataBundle setUserID(String user, long number) {
        this.USERID.putString(USER_TAG, user);
        this.USERID.putLong(ID_TAG, number);
        return this;
    }

    public DataBundle setOption(String option, String database1, String database2) {
        this.OPTION.putString(OPTION_TAG, option);
        this.OPTION.putString(DB1_TAG, database1);
        this.OPTION.putString(DB2_TAG, database2);
        return this;
    }

    public DataBundle setProtected(String Password, String Info) {
        if (!(Password.equals("GOWILD"))) {
            System.out.println("Wrong Password!");
        } else {
            this.PROTECTED.putString("Temp", Info);
        }
        return this;
    }


    /*
    public boolean checkBundleNull()
    {
        Boolean result = false;
        if (this.USERID)


        return result;
    }
    */

    /*
    public DataBundle userBundle(){
    }
    */

    public String getBundledString(String InnerBundle, String TAG) {

        String innerString;

        if (InnerBundle.equalsIgnoreCase("USERID")) {
            innerString = this.USERID.getString(TAG);
        } else if (InnerBundle.equalsIgnoreCase("OPTION")) {
            innerString = this.OPTION.getString(TAG);
        } else if (InnerBundle.equalsIgnoreCase("PROTECTED")) {
            innerString = this.PROTECTED.getString(TAG);
        } else {
            return null;
        }

        return innerString;
    }
}