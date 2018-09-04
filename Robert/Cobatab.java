package com.project.robert.Robert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class Cobatab extends AppCompatActivity {
    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.support.com.project.robert.Robert.R.layout.cobatab);
        Intent a = new Intent(this,MainActivity.class);
        Intent b = new Intent(this,Tab2.class);
        Intent c = new Intent(this,Tab3.class);
        Intent d = new Intent(this,Tab4.class);
        TabHost host = (TabHost)findViewById(android.support.com.project.robert.Robert.R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(a);
        spec.setIndicator("Tab One");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(b);
        spec.setIndicator("Tab Two");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(c);
        spec.setIndicator("Tab Three");
        host.addTab(spec);

        spec = host.newTabSpec("Tab Four");
        spec.setContent(d);
        spec.setIndicator("Tab Four");
        host.addTab(spec);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(android.support.com.project.robert.Robert.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.support.com.project.robert.Robert.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}