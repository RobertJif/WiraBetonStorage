package com.project.robert.Robert;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private TextView tvEmpty;
    private Button btnNew;
    private String [] arrayItem = {Static.UPDATE, Static.DELETE,"Request"};
    ArrayList<Barang> listBarang = new ArrayList<Barang>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.support.com.project.robert.Robert.R.layout.activity_main);
        lv = (ListView) findViewById(android.support.com.project.robert.Robert.R.id.list);
        tvEmpty = (TextView) findViewById(android.support.com.project.robert.Robert.R.id.tvEmpty);
        btnNew = (Button) findViewById(android.support.com.project.robert.Robert.R.id.btnNew);
// Add new contact
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iNew = new Intent(MainActivity.this, AddActivity.class);
                finish();
                startActivity(iNew);
            }
        });
// Load barangsm
        new GetBarang().execute();
// Show dialog for Update/Delete contact
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

                AlertDialog.Builder dialogItem = new AlertDialog.Builder(MainActivity.this);
                final Barang barang = listBarang.get(position);
                dialogItem.setTitle(barang.getNama_barang());
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayItem);
                dialogItem.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int positionDialog) {
                        if (adapter.getItem(positionDialog).equals(Static.UPDATE)) {
                            Intent iEdit = new Intent(MainActivity.this, EditActivity.class);
                            iEdit.putExtra(Static.ID_BARANG, barang.getId_barang());
                            iEdit.putExtra(Static.NAMA_BARANG, barang.getNama_barang());
                            iEdit.putExtra(Static.HARGA, barang.getHarga());
                            iEdit.putExtra(Static.STOK, barang.getStok());
                            iEdit.putExtra(Static.TERJUAL, barang.getTerjual());

                            MainActivity.this.finish();
                            startActivity(iEdit);
                        }
                        else if(adapter.getItem(positionDialog).equals("Request")){
                            Log.i("Send email", "");

                            String[] TO = {"roberttjjif@gmail.com"};
                            String[] CC = {"iqbal14ti@mahasiswa.pcr.ac.id"};
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setData(Uri.parse("mailto:"));
                            emailIntent.setType("text/plain");

                            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                            emailIntent.putExtra(Intent.EXTRA_CC, CC);
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Stok Barang");
                            emailIntent.putExtra(Intent.EXTRA_TEXT, barang.getNama_barang() +" Sisa "+barang.getStok());

                            try {
                                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                                finish();
                                Log.i("Finished sending email.", "");
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(MainActivity.this,
                                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            new DeleteBarang().execute(barang);
                        }
                    }
                }).show();
                return false;
            }
        });
    }



    /**
     * Async task class to get json by making HTTP call
     */
    private class GetBarang extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
// Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
// Making a request to url and getting response
            String jsonStr = sh.callJson();
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
// Getting JSON Array node
                    JSONArray barangs = jsonObj.getJSONArray(Static.BARANG);
                    if (!barangs.getJSONObject(0).equals(Static.EMPTY)) {
// looping through All Contacts
                        for (int i = 0; i < barangs.length(); i++) {
                            JSONObject c = barangs.getJSONObject(i);
                            Barang barang = new Barang();
                            barang.setId_barang(c.getString(Static.ID_BARANG));
                            barang.setNama_barang(c.getString(Static.NAMA_BARANG));
                            barang.setHarga(c.getString(Static.HARGA));
                            barang.setStok(c.getString(Static.STOK));
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
                Adapter adapter = new Adapter(getApplicationContext(), listBarang);
                lv.setAdapter(adapter);
            } else {
                tvEmpty.setText("Table is Empty");
            }
        }
    }
    /**
     * Async task class to send json
     */
    private class DeleteBarang extends AsyncTask<Barang, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

// Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Deleting a data...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(Barang... barangs) {
            Barang barang = barangs[0];
            String result = "";
            try {
                HttpHandler sj = new HttpHandler();
                JSONObject resObj = new JSONObject(sj.sendJson(HttpHandler.URL + HttpHandler.DELETE, barang));
                JSONArray resArr = resObj.getJSONArray(Static.POSTS);
                result = resArr.getString(0);
            } catch (JSONException e) {
                Log.i(TAG, "JSON parse error " + e.getMessage());
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
// Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            Log.d(TAG, result);
/**
 * Show insert information
 * */
            if (result.equals(Static.SUCCESS)) {
                Toast.makeText(MainActivity.this, "Barang deleted", Toast.LENGTH_LONG).show();
                listBarang.clear();
                new GetBarang().execute();
            } else if (result.equals(Static.FAIL)) {

                Toast.makeText(MainActivity.this, "Fail to delete barang", Toast.LENGTH_LONG).show();
            }
        }
    }

}