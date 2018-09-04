package com.project.robert.Robert;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


/**
 * Created by Robert on 01/11/2016.
 */

public class HttpHandlerFavorit {
    private static final String TAG = HttpHandlerFavorit.class.getSimpleName();
    public static final String URL = IP.IP;
    public static final String VIEWFAVORIT = "viewfavorit.php";

    public HttpHandlerFavorit() {
    }

    public String callJson() {
        String response = null;
        try {
            URL url = new URL(URL + VIEWFAVORIT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
// read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }
    public String sendJson(String url, Barang barang) {
        InputStream inputStream = null;
        String result = "";
        try {
// create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
// make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
            String json = "";
// build jsonObject
            JSONObject jsonObj = new JSONObject();
            jsonObj.accumulate(Static.ID_BARANG, barang.getId_barang());
            jsonObj.accumulate(Static.NAMA_BARANG, barang.getNama_barang());
            jsonObj.accumulate(Static.TERJUAL, barang.getTerjual());

// convert JSONObject to JSON to String

            json = jsonObj.toString();
// set json to StringEntity
            StringEntity se = new StringEntity(json);
// set httpPost Entity
            httpPost.setEntity(se);
// Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
// Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
// receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
// convert inputstream to string
            if (inputStream != null)
                result = convertStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}