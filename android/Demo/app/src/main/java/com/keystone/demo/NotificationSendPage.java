package com.keystone.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NotificationSendPage extends ActionBarActivity {

    Button sendnotifi;
    public String message;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_send_page);

        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        sendnotifi=(Button)findViewById(R.id.sendnotifi);
        sendnotifi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                message=((EditText)findViewById(R.id.editText)).getText().toString();
                if(message.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter the Message",Toast.LENGTH_LONG).show();
                }
                else {
                    //sky lappy
                    String usermessage = message.replaceAll(" ", "%20");
                    message = usermessage;
                    //String url="http://192.168.56.1:8080/Demo/rest/NotificationWebService/notifi"+"/"+message;
                    // String url="http://pkredroses.tk/Demo/rest/NotificationWebService/notifi"+"/"+message;
                    String url = Config.APP_SERVER_URL + "WifiMsgWebService/notifi" + "/" + message;

                    //String url="http://192.168.137.1:8080/Demo/rest/LoginWebService/login"+"/"+empid+"/"+pass;
                    // String url="http://192.168.42.88:8080/Demo/rest/LoginWebService/login"+"/"+empid+"/"+pass;

                    //pawan lappy
                    //  String url="http://192.168.137.1:8585/Demo/rest/LoginWebService/login"+"/"+empid+"/"+pass;
                    NotificationSendPageAsync notificationasync = new NotificationSendPageAsync(NotificationSendPage.this);
                    String[] data = new String[1];
                    data[0] = url;
                    notificationasync.execute(data);
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
