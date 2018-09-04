package com.project.robert.Robert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Robert on 22/01/2017.
 */

public class Logout extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.support.com.project.robert.Robert.R.layout.logout);

        Intent a = new Intent(Logout.this,Login.class);
        finish();
        startActivity(a);

    }

}
