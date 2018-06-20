package com.keystone.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;


public class AdminHomeActivity extends ActionBarActivity {
    Button allemp,logout,regGCM,dept,group,regMAC,gnotify;
    String emp_id;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.activity_admin_home);
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        gnotify= (Button) findViewById(R.id.bGNotify);
        gnotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NotificationSendOption.class);
                i.putExtra("notifyto","group");
                startActivity(i);
            }
        });

        regGCM=(Button)findViewById(R.id.regGCM);
        regGCM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        allemp=(Button)findViewById(R.id.allemp);
        allemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),NotificationSendOption.class);
                i.putExtra("notifyto","emp");
                startActivity(i);
            }
        });

        logout=(Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emp_id=AppPreferences.getUserEmpId(context);
                //sky lappy
                //String url="http://pkredroses.tk/Demo/rest/LogoutWebService/logout" + "/" +emp_id;
                //pawan lappy
                // String url="http://192.168.137.1:8585/Demo/rest/LogoutWebService/logout"+"/"+emp_id;
                String url=Config.APP_SERVER_URL+"LogoutWebService/logout" + "/" +emp_id;
                LogoutAsync la=new LogoutAsync(AdminHomeActivity.this);
                String[] data=new String[1];
                data[0]=url;
                la.execute(data);

            }
        });

        group=(Button)findViewById(R.id.bGroup);
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GroupCreation.class);
                startActivity(i);
            }
        });

        dept=(Button)findViewById(R.id.dept);
        dept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String url = "http://192.168.56.1:8080/Demo/rest/DepartmentListWebService/getDeptList";
                //pawan lappy
                // String url="http://192.168.137.1:8585/Demo/rest/LogoutWebService/logout"+"/"+emp_id;
               /*DepartmentListAsync la = new DepartmentListAsync(AdminHomeActivity.this);
                String[] data = new String[1];
                data[0] = url;
                la.execute(data);*/
                Intent i = new Intent(getApplicationContext(), NotificationSendOption.class);
                i.putExtra("notifyto", "dept");
                startActivity(i);
            }
        });

        regMAC=(Button)findViewById(R.id.regMAC);
        regMAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RegMACActivity.class);
                startActivity(i);
            }
        });
      /*  fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);*/
    }


   /* @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:
                if(isFabOpen){

                    fab.startAnimation(rotate_backward);
                    fab1.startAnimation(fab_close);
                    fab2.startAnimation(fab_close);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    isFabOpen = false;
                    Log.d("Raj", "close");

                } else {

                    fab.startAnimation(rotate_forward);
                    fab1.startAnimation(fab_open);
                    fab2.startAnimation(fab_open);
                    fab1.setClickable(true);
                    fab2.setClickable(true);
                    isFabOpen = true;
                    Log.d("Raj","open");

                }

                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab2:
                if(isFabOpen){

                    fab.startAnimation(rotate_backward);
                    fab1.startAnimation(fab_close);
                    fab2.startAnimation(fab_close);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    isFabOpen = false;
                    Log.d("Raj", "close");

                } else {

                    fab.startAnimation(rotate_forward);
                    fab1.startAnimation(fab_open);
                    fab2.startAnimation(fab_open);
                    fab1.setClickable(true);
                    fab2.setClickable(true);
                    isFabOpen = true;
                    Log.d("Raj","open");

                }
                Toast.makeText(getApplicationContext(), "hii", Toast.LENGTH_SHORT).show();
                break;
        }
    }*/

   /* public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }*/

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
