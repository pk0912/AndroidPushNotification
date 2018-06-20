package com.keystone.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity
{
    //Animation zoomin,zoomout;
    Boolean isInternetPresent = false;
    Button loginBtn;
    AppCompatImageView img;
    public String empid,pass;
    private Toolbar toolbar;

    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //toolbar=(Toolbar)findViewById(R.id.app_bar);
        //setSupportActionBar(toolbar);
        //img=(AppCompatImageView)findViewById(R.id.imgView);
        //zoomin= AnimationUtils.loadAnimation(this,R.anim.zoom_in);
      //  zoomout= AnimationUtils.loadAnimation(this,R.anim.zoom_out);
        //img.setAnimation(zoomin);
       // img.setAnimation(zoomout);
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            // Internet Connection is Present
            // make HTTP requests
            //showAlertDialog(LoginActivity.this, "Internet Connection",
              //      "You have internet connection", true);
            loginBtn=(Button)findViewById(R.id.login);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    empid = ((EditText) findViewById(R.id.emp_id)).getText().toString();
                    pass = ((EditText) findViewById(R.id.pwd)).getText().toString();
                    //sky lappy
                    //String url="http://192.168.56.1:8080/Demo/rest/LoginWebService/login"+"/"+empid+"/"+pass;
                    //String url = "http://pkredroses.tk/Demo/rest/LoginWebService/login" + "/" + empid + "/" + pass;
                    if(empid.equals("") || pass.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_LONG).show();
                    }
                    else {
                        String url = Config.APP_SERVER_URL + "LoginWebService/login" + "/" + empid + "/" + pass;

                        // String url="http://192.168.42.88:8080/Demo/rest/LoginWebService/login"+"/"+empid+"/"+pass;

                        //pawan lappy
                        //  String url="http://192.168.137.1:8585/Demo/rest/LoginWebService/login"+"/"+empid+"/"+pass;
                        LoginAsync loginasync = new LoginAsync(LoginActivity.this);
                        String[] data = new String[1];
                        data[0] = url;
                        loginasync.execute(data);
                    }
                }
            });
        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(LoginActivity.this, "No Internet Connection",
                    "You don't have internet connection.", false);

        }

    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
            Toast.makeText(this,"Login to receive Push Notifications",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });

        // Showing Alert Message
        alertDialog.show();
    }
}
