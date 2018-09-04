package com.project.robert.Robert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class AddOrder extends AppCompatActivity {
    private String TAG = AddOrder.class.getName();
    private ProgressDialog pDialog;
    private String harga, stok;
    private EditText etId_barang,etJumlah,etTotal,etTanggal;
    private Button btnEdit;
    String mId_barang;
    String mJumlah;
    String mTotal;
    String mTanggal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.support.com.project.robert.Robert.R.layout.form_edit_order);
        etId_barang = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.etId_barang_order);
        etJumlah = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.etJumlahPesan);
        etTotal = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.etTotal_order);
        etTanggal = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.etTanggal_order);
        Intent iEdit = getIntent();
        String mHarga = iEdit.getStringExtra(Static.HARGA);
        mId_barang = iEdit.getStringExtra(Static.ID_BARANG);
        mJumlah = iEdit.getStringExtra(Static.JUMLAH);
        mTotal = iEdit.getStringExtra(Static.NAMA_BARANG);
        mTanggal = iEdit.getStringExtra(Static.TANGGAL);
        etId_barang.setText(mId_barang);
        etJumlah.setText(mJumlah);
        etTotal.setText(mTotal);
        etTanggal.setText(mTanggal);
        setHarga(mHarga);

        setStok(iEdit.getStringExtra(Static.STOK));
        btnEdit = (Button) findViewById(android.support.com.project.robert.Robert.R.id.btnAdd);
        btnEdit.setText("Update");
//
   //     btnEdit.setOnClickListener(new View.OnClickListener() {

     //       @Override
     //       public void onClick(View v) {
     //           new SendBarangs().execute(mId_barang);
     //       }
      //  });
    }
    /**
     * Async task class to send json
     */
    private class SendBarangs extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
// Showing progress dialog
            pDialog = new ProgressDialog(AddOrder.this);
            pDialog.setMessage("Updating your data...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {

            Transaksi transaksi = new Transaksi();
            transaksi.setId_transaksi("");
            int a ,b ;
            a = Integer.parseInt(getHarga());
            b = Integer.parseInt(etJumlah.getText().toString());
            int total = a*b;
            String totalakhir = total+"";
            transaksi.setId_barang(etId_barang.getText().toString());
            transaksi.setJumlah(etJumlah.getText().toString());
            transaksi.setTotal(totalakhir);
            transaksi.setTanggal(etTanggal.getText().toString());

            String result = "";
            try {
                HttpHandlerInsertTransaksi sj = new HttpHandlerInsertTransaksi();
                JSONObject resObj = new JSONObject(sj.sendJson(HttpHandlerInsertTransaksi.URL + HttpHandlerInsertTransaksi.INSERT, transaksi));
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
                Toast.makeText(AddOrder.this, "Transaksi updated", Toast.LENGTH_LONG).show();
                Intent iMain = new Intent(AddOrder.this, Tab.class);
                finish();
                startActivity(iMain);
            } else if (result.equals(Static.FAIL)) {
                Toast.makeText(AddOrder.this, "Fail to update transaksi", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void backHome(View view){
        Intent i = new Intent(AddOrder.this, Tab.class);

        startActivity(i);


    }
    public void setHarga(String harga){
        this.harga = harga;
    }
    public String getHarga(){
        return harga;
    }
    public void setStok(String stok){
        this.stok = stok;
    }
    public String getStok(){
        return stok;
    }
    public void OrderBarang(View v){
        if(!etJumlah.getText().toString().equals("")){

            int banding = Integer.parseInt(getStok());
            int banding2 = Integer.parseInt(etJumlah.getText().toString());
            if(banding>=banding2){
                new SendBarangs().execute(mId_barang);
            }
            else{
                Toast.makeText(getApplicationContext(),"Stok tidak mencukupi !",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Jumlah Pemesanan Harus Diisi !",Toast.LENGTH_SHORT).show();
        }


    }

}
