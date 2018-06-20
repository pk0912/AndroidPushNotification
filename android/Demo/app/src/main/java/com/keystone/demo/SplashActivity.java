package com.keystone.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class SplashActivity extends Activity {

    Context context=SplashActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread th=new Thread() {
            public void run()
            {
                try{
                    sleep(5000);
                }
                catch(Exception e)
                {

                }
                finally
                {
                    if(!AppPreferences.getUserLoggedInStatus(context)) {
                        context.startActivity(new Intent(context, LoginActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                    else
                    {
                        System.out.println(AppPreferences.getUserType(context)+"--------------------------------------------------");

                        if (AppPreferences.getUserType(SplashActivity.this).equalsIgnoreCase("Employee"))
                        {

                                // Toast.makeText(context, "already logged in", Toast.LENGTH_LONG).show();
                                context.startActivity(new Intent(context, EmployeeHomeActivity.class));
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                        }
                        else
                        {
                          //  Toast.makeText(context, "already logged in", Toast.LENGTH_LONG).show();
                            context.startActivity(new Intent(context, AdminHomeActivity.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }

                    }

                }

            }
        };
        th.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
