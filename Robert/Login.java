package com.project.robert.Robert;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Robert on 17/11/2016.
 */

public class Login extends AppCompatActivity {

    private String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.support.com.project.robert.Robert.R.layout.login);
        TextView forgot = (TextView) findViewById(android.support.com.project.robert.Robert.R.id.judul);
        forgot.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(Login.this)
                        .setMessage(
                                getString(android.support.com.project.robert.Robert.R.string.forgot))
                        .setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                return false;
            }
        });
    }

    public void Login(View view){
        EditText aa = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.masuk_username);
        EditText bb = (EditText) findViewById(android.support.com.project.robert.Robert.R.id.masuk_password);
        String a = aa.getText().toString();
        String b = bb.getText().toString();
        username = "wirabetonuser";
        password = "wirabetonpass";

        if(a.equals(username)&&b.equals(password)){
            Intent i = new Intent(Login.this , Tab.class);
            finish();
            startActivity(i);
        }
        else{
            Toast.makeText(getApplicationContext(),"Username atau Password salah!",Toast.LENGTH_LONG).show();
        }

    }
}
