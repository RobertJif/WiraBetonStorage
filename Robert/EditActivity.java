package com.project.robert.Robert;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Robert on 01/11/2016.
 */

public class EditActivity extends Activity {
    private String TAG = EditActivity.class.getName();
    private ProgressDialog pDialog;

    private EditText etNama_barang,etHarga,etStok,etTerjual;
    private Button btnEdit;
    String mId_barang;
    String mNama_barang;
    String mHarga;
    String mStok;
    String mTerjual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.support.com.project.robert.Robert.R.layout.form_edit);
        etNama_barang = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.etNama_barang);
        etHarga = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.etHarga);
        etStok = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.etStok);
        etTerjual = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.etTerjual);
        Intent iEdit = getIntent();
        final String a = etNama_barang.toString();
        final String b = etHarga.toString();
        final String c = etStok.toString();
        final String d = etTerjual.toString();
        final String check = "";

        mId_barang = iEdit.getStringExtra(Static.ID_BARANG);
        mNama_barang = iEdit.getStringExtra(Static.NAMA_BARANG);
        mHarga = iEdit.getStringExtra(Static.HARGA);
        mStok = iEdit.getStringExtra(Static.STOK);
        mTerjual = iEdit.getStringExtra(Static.TERJUAL);

        etNama_barang.setText(mNama_barang);
        etHarga.setText(mHarga);
        etStok.setText(mStok);
        etTerjual.setText(mTerjual);

        btnEdit = (Button) findViewById(android.support.com.project.robert.Robert.R.id.btnAdd);
        btnEdit.setText("Update");
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a.equals(check)){Toast.makeText(getApplicationContext(),"Nama Barang Harus Diisi",Toast.LENGTH_SHORT);}
                else{
                    if(b.equals(check)){Toast.makeText(getApplicationContext(),"Harga Harus Diisi",Toast.LENGTH_SHORT);}
                    else{
                        if(c.equals(check)){Toast.makeText(getApplicationContext(),"Stok Harus Diisi",Toast.LENGTH_SHORT);}
                        else{
                            if(d.equals(check)){Toast.makeText(getApplicationContext(),"Terjual Harus Diisi",Toast.LENGTH_SHORT);}
                            else{
                                new SendBarangs().execute(mId_barang);
                            }
                        }
                    }
                }

            }
        });
    }
    /**
     * Async task class to send json
     */
    private class SendBarangs extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
// Showing progress dialog
            pDialog = new ProgressDialog(EditActivity.this);
            pDialog.setMessage("Updating your data...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            Barang barang = new Barang();
            barang.setId_barang(mId_barang);
            barang.setNama_barang(etNama_barang.getText().toString());
            barang.setHarga(etHarga.getText().toString());
            barang.setStok(etStok.getText().toString());
            barang.setTerjual(etTerjual.getText().toString());
            String result = "";
            try {
                HttpHandler sj = new HttpHandler();
                JSONObject resObj = new JSONObject(sj.sendJson(HttpHandler.URL + HttpHandler.UPDATE, barang));
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
                Toast.makeText(EditActivity.this, "Barang updated", Toast.LENGTH_LONG).show();
                Intent iMain = new Intent(EditActivity.this, Tab.class);
                finish();
                startActivity(iMain);
            } else if (result.equals(Static.FAIL)) {
                Toast.makeText(EditActivity.this, "Fail to update barang", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void backHome(View view){
        Intent i = new Intent(EditActivity.this, Tab.class);

        startActivity(i);


    }
}
