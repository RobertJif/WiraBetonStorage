package com.project.robert.Robert;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tab2 extends Activity {
    private String TAG = Tab2.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv2;
    private TextView tvEmpty2;
    ArrayList<Barang> listBarang = new ArrayList<Barang>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.support.com.project.robert.Robert.R.layout.tab2);
        lv2 = (ListView) findViewById(android.support.com.project.robert.Robert.R.id.list2);
        tvEmpty2 = (TextView) findViewById(android.support.com.project.robert.Robert.R.id.tvEmpty2);
// Add new contact

// Load contactsm
        new GetContacts().execute();
// Show dialog for Update/Delete contact

    }
    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
// Showing progress dialog
            pDialog = new ProgressDialog(Tab2.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            HttpHandlerFavorit sh = new HttpHandlerFavorit();
// Making a request to url and getting response
            String jsonStr = sh.callJson();
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
// Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray(Static.BARANG);
                    if (!contacts.getJSONObject(0).equals(Static.EMPTY)) {
// looping through All Contacts
                        for (int i = 0; i < 5; i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            Barang barang = new Barang();
                            barang.setId_barang(c.getString(Static.ID_BARANG));
                            barang.setNama_barang(c.getString(Static.NAMA_BARANG));
                            barang.setTerjual(c.getString(Static.TERJUAL));

// adding barang to barang list
                            listBarang.add(barang);
                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {

                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(
                                getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
// Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
/**
 * Updating parsed JSON data into ListView
 * */
            if (listBarang.size() > 0) {
                AdapterFavorit adapter = new AdapterFavorit(getApplicationContext(), listBarang);
                lv2.setAdapter(adapter);
            } else {
                tvEmpty2.setText("Table is Empty");
            }
        }
    }
    /**
     * Async task class to send json
     */


}