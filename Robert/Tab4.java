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

public class Tab4 extends Activity {
    private String TAG = Tab4.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private TextView tvEmpty;

    ArrayList<History> listHistory = new ArrayList<History>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.support.com.project.robert.Robert.R.layout.tab4);
        lv = (ListView) findViewById(android.support.com.project.robert.Robert.R.id.list4);
        tvEmpty = (TextView) findViewById(android.support.com.project.robert.Robert.R.id.tvEmpty4);
// Load contactsm;
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
            pDialog = new ProgressDialog(Tab4.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            HttpHandlerHistory sh = new HttpHandlerHistory();
// Making a request to url and getting response
            String jsonStr = sh.callJson();
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
// Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray(Static.HISTORY);
                    if (!contacts.getJSONObject(0).equals(Static.EMPTY)) {
// looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            History history = new History();
                            history.setNama_barang(c.getString(Static.NAMA_BARANG));
                            history.setJumlah(c.getString(Static.JUMLAH));
                            history.setTotal(c.getString(Static.TOTAL));
                            history.setTanggal(c.getString(Static.TANGGAL));

// adding history to history list
                            listHistory.add(history);
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
            if (listHistory.size() > 0) {
                AdapterHistory adapter = new AdapterHistory(getApplicationContext(), listHistory);
                lv.setAdapter(adapter);
            } else {
                tvEmpty.setText("Table is Empty");
            }
        }
    }
    /**
     * Async task class to send json
     */


}