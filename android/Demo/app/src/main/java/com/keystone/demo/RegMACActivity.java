package com.keystone.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegMACActivity extends AppCompatActivity
{
    EditText edMac;
    Button addMac;
    String mac;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_mac);
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        edMac=(EditText)findViewById(R.id.MacId);
        addMac=(Button)findViewById(R.id.addMacId);
        addMac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mac=edMac.getText().toString();
                String url="http://pkredroses.tk/Demo/rest/RegMacWebService/regMAC" + "/" +mac;
                RegMacAsync regMacAsync = new RegMacAsync(RegMACActivity.this);
                String[] data = new String[1];
                data[0] = url;
                regMacAsync.execute(data);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
