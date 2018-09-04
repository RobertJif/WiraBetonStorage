package com.project.robert.Robert;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Robert on 01/11/2016.
 */

public class AddActivity extends Activity {
    private String TAG = AddActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private EditText etNama_barang,etHarga,etStok,etTerjual;
    private Button btnAdd;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.support.com.project.robert.Robert.R.layout.form_input);
        etNama_barang = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.etNama_barang);
        etHarga = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.etHarga);
        etStok = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.etStok);
        etTerjual = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.etTerjual);

   //     btnAdd = (Button) findViewById(R.id.btnAdd);
   //     btnAdd.setOnClickListener(new View.OnClickListener() {
    //        @Override
     //       public void onClick(View v) {
     //           new SendBarangs().execute();
     //       }
     //   });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onBackClick(View view){
        Intent a = new Intent(this,Tab.class);
        startActivity(a);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Add Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /**
     * Async task class to send json
     */
    private class SendBarangs extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
// Showing progress dialog
            pDialog = new ProgressDialog(AddActivity.this);
            pDialog.setMessage("Adding your data...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected String doInBackground(Void... params) {
            Barang barang = new Barang();
            barang.setId_barang("");
            barang.setNama_barang(etNama_barang.getText().toString());
            barang.setHarga(etHarga.getText().toString());
            barang.setStok(etStok.getText().toString());
            barang.setTerjual(etTerjual.getText().toString());
            String result = "";
            try {
                HttpHandler sj = new HttpHandler();
                JSONObject resObj = new JSONObject(sj.sendJson(HttpHandler.URL + HttpHandler.INSERT, barang));
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
                Toast.makeText(AddActivity.this, "Barang added", Toast.LENGTH_LONG).show();
                Intent iMain = new Intent(AddActivity.this, Tab.class);
                finish();
                startActivity(iMain);
            } else if (result.equals(Static.FAIL)) {
                Toast.makeText(AddActivity.this, "Fail to add barang", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void PeriksaFormBarang(View view){
        String f1,f2,f3,f4;
        f1 = etNama_barang.getText().toString();
        f2 = etHarga.getText().toString();
        f3 = etStok.getText().toString();
        f4 = etTerjual.getText().toString();
        if(f1.equals("")){
            Toast.makeText(getApplicationContext(),"Nama Barang harus diisi!",Toast.LENGTH_SHORT).show();
        }
        if(f2.equals("")){
            Toast.makeText(getApplicationContext(),"Harga harus diisi!",Toast.LENGTH_SHORT).show();
        }
        if(f3.equals("")){
            Toast.makeText(getApplicationContext(),"Stok harus diisi!",Toast.LENGTH_SHORT).show();
        }
        if(f4.equals("")){
            Toast.makeText(getApplicationContext(),"Jumlah Terjual harus diisi!",Toast.LENGTH_SHORT).show();
        }
        else {
            new SendBarangs().execute();
        }

    }
}