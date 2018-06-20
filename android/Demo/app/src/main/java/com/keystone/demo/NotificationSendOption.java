package com.keystone.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by Yogender Kumar on 03-02-2016.
 */
public class NotificationSendOption extends ActionBarActivity {
    Button wifi,loc;
    String option;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.notificationsendoption);
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        wifi=(Button)findViewById(R.id.bWR);
        loc=(Button)findViewById(R.id.bLW);
        Intent i=getIntent();
        option=i.getStringExtra("notifyto");

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option.equalsIgnoreCase("emp"))
                {
                    Intent i = new Intent(getApplicationContext(), NotificationSendPage.class);
                    i.putExtra("notifyoption", "wifi");
                    startActivity(i);
                }
                else if(option.equalsIgnoreCase("dept"))
                {
                    Intent i = new Intent(getApplicationContext(), DepartmentList.class);
                    i.putExtra("notifyoption", "wifi");
                    startActivity(i);
                }
                else if(option.equalsIgnoreCase("group"))
                {
                    Intent i = new Intent(getApplicationContext(), GroupNotification.class);
                    i.putExtra("notifyoption", "wifi");
                    startActivity(i);
                }
            }
        });
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (option.equalsIgnoreCase("emp")) {
                    /*Intent i = new Intent(getApplicationContext(), NotificationSendPage.class);
                    i.putExtra("notifyoption", "loc");
                    startActivity(i);*/
                } else if (option.equalsIgnoreCase("dept")) {
                    /*Intent i = new Intent(getApplicationContext(), DepartmentList.class);
                    i.putExtra("notifyoption", "loc");
                    startActivity(i);*/
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);

    }
}
