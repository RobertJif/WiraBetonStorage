package com.project.robert.Robert;

/**
 * Created by Robert on 13/11/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.app.TabActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.TabHost;

public class Tab extends TabActivity {
        TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.maintab);
       // TabHost tabHost = (TabHost) findViewById(R.id.tabHost1);
       // tabHost.setup();
        tabHost = getTabHost();
        Intent a = new Intent(this,MainActivity.class);
        Intent b = new Intent(this,Tab2.class);
        Intent c = new Intent(this,Tab3.class);
        Intent d = new Intent(this,Tab4.class);
        Intent e = new Intent(this,Logout.class);

        LayoutInflater.from(this).inflate(android.support.com.project.robert.Robert.R.layout.maintab, tabHost.getTabContentView(),true);
        tabHost.addTab(tabHost.newTabSpec("Barang").setIndicator("Barang").setContent(a));
        tabHost.addTab(tabHost.newTabSpec("Favorit").setIndicator("Favorit").setContent(b));
        tabHost.addTab(tabHost.newTabSpec("Logout").setIndicator("Logout").setContent(e));
  //      tabHost.addTab(tabHost.newTabSpec("Order").setIndicator("Order").setContent(c));
    //    tabHost.addTab(tabHost.newTabSpec("History").setIndicator("History").setContent(d));
      //  tabHost.setCurrentTab(2);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(android.support.com.project.robert.Robert.R.menu.mod3_l1, menu);
        return true;
    }

}
