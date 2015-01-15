package com.example.androidbanking;

/**
 * Connect.java
 * Purpose: Enables HTTP Post request to be run on Async Thread.
 *          Contains the Website API access URL, that allows multiple
 *          different requests depending on the string tacked on to the end.
 */


import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class Connect extends AsyncTask<String, Void, String> {
    public volatile String JsonString;
    private static String GOWILD_URL = "http://54.68.34.231/api/";
    Connect() {}
    @Override
    protected String doInBackground(String... params) {
        // email and password are obtained from UI in actual app
        /*String email = params[0];
        String password = params[1];
        int account_number = Integer.parseInt(params[2]);
        double amount = Double.parseDouble(params[3]);
        String account_new = params[4];
        String deposit = params[5];
        int account_from = Integer.parseInt(params[6]);
        int account_to = Integer.parseInt(params[7]);*/
        String request = params[0];
        String email = params[1];
        String password = params[2];

        try {
            Log.d("Calling ", "Calling Result WHAT!!! NUM1");
            HttpClient client = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(GOWILD_URL);
            String queryData = "";


            if (request == "login") {

                queryData = "{\"request\": \"" + request + "\"," +
                        "\"user\": {\"email\": \"" + email + "\", \"password\": \"" + password + "\"}" +
                        "}";
            }

            if (request == "transfer") {
                int from_account = Integer.parseInt(params[3]);
                int to_account = Integer.parseInt(params[4]);
                Double amount = Double.parseDouble(params[5]);


                System.err.println("Request: " + request);
                System.err.println("Email: " + email);
                System.err.println("Password: " + password);

                System.err.println("From account: " + from_account);
                System.err.println("To account: " + to_account);
                System.err.println("Amount: " + amount);

                System.err.println("" + "{\"request\": \"" + request + "\"," +
                        "\"user\": {\"email\": \"" + email + "\", \"password\": \"" + password + "\"}, " +
                        "\"from_account\": " + from_account + ", " +
                        "\"to_account\": " + to_account + "," +
                        "\"amount\": " + amount + "" +
                        "}");

                queryData = "{\"request\": \"" + request + "\"," +
                        "\"user\": {\"email\": \"" + email + "\", \"password\": \"" + password + "\"}," +
                        "\"from_account\": " + from_account + ", " +
                        "\"to_account\":" + to_account + "," +
                        "\"amount\":" + amount + "" +
                        "}";
            }

            if (request == "withdraw") {
                Double amount = Double.parseDouble(params[5]);
                int accountnumber = Integer.parseInt(params[6]);

                System.err.println("{\"request\": \"" + request + "\"," +
                        "\"user\": {\"email\": \"" + email + "\", \"password\": \"" + password + "\"}," +
                        "\"accountnumber\": " + accountnumber + ", " +
                        "\"amount\":" + amount + "" +
                        "}");

                queryData = "{\"request\": \"" + request + "\"," +
                        "\"user\": {\"email\": \"" + email + "\", \"password\": \"" + password + "\"}," +
                        "\"account_number\": " + accountnumber + ", " +
                        "\"amount\":" + amount + "" +
                        "}";
            }

            postRequest.setEntity(new StringEntity(queryData));
            postRequest.setHeader("Accept", "application/json");
            postRequest.setHeader("Content-Type", "application/json");
            Log.d("Calling ", "Calling Result WHAT!!! Num2");
            //System.out.println(queryData);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            //Log.d("Calling ","Calling Result WHAT!!!");
            String result = client.execute(postRequest, responseHandler);

            System.out.println("\nResult: " + result + "\n");
            JsonString = result;
            System.out.println("\nResultkhkj: " + JsonString);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Async. Thread");
        }


        return JsonString;
        // TODO: register the new account here.
    }

    public void testing() {

    }

    protected void onPostExecute(Void... params) {

    }
}