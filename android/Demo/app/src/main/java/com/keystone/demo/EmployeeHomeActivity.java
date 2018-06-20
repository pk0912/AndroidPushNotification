package com.keystone.demo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.keystone.demo.wifimac.AlarmReceiver;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class EmployeeHomeActivity extends ActionBarActivity {

    //variable declaration
    TextView tv;
    Button logout,wifitracker;
    String emp_id;
    String msg;
    private AlarmManager alarmManager;
    private Intent WifiTrackerIntent;
    private PendingIntent pendingIntent;
    private RecyclerView mRecyclerView,mRecyle;
    private MyRecyclerAdapter adapter;
    public static final String REG_ID = "regId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //layout setting
        setContentView(R.layout.activity_employee_home);


        mRecyle = (RecyclerView) findViewById(R.id.messageList);
        mRecyle.setLayoutManager(new LinearLayoutManager(this));

        senddata();
        //initialization of button
        wifitracker=(Button)findViewById(R.id.WifiTracker);
        logout=(Button)findViewById(R.id.logout);
        final Context context = this;
        msg=getIntent().getStringExtra("msg");


        wifitracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarmManager();
            }
        });
        //method calling for log out button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences prefs = getSharedPreferences(
                        MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(REG_ID, null);
                editor.commit();
                emp_id = AppPreferences.getUserEmpId(context);
                //sky lappy
                //String url = "http://pkredroses.tk/Demo/rest/LogoutWebService/logout" + "/" + emp_id;
                String url = Config.APP_SERVER_URL+"LogoutWebService/logout" + "/" + emp_id;

                //pawan lappy
                // String url="http://192.168.137.1:8585/Demo/rest/LogoutWebService/logout"+"/"+emp_id;
                LogoutAsync la = new LogoutAsync(EmployeeHomeActivity.this);
                String[] data = new String[1];
                data[0] = url;
                la.execute(data);

            }
        });
    }

    private void senddata() {
        String uri = Config.APP_SERVER_URL+"MessageDetailWS/getmsg/"+1;
        CheckAsycn obj = new CheckAsycn(EmployeeHomeActivity.this);
        obj.execute(uri);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_employee_home, menu);
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
    public void onBackPressed() {
        finish();
    }
    private void startAlarmManager()
    {
        Context context = getBaseContext();
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        WifiTrackerIntent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, WifiTrackerIntent, 0);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                2 * 60000, // 60000 = 1 minute
                pendingIntent);
    }

    private class CheckAsycn extends AsyncTask<String,Void,Void> {

        Dialog dialog;
        Activity activity;
        Context context;
        String regId;
        ProgressDialog pDialoge;
        MessageVO statusVO;
        public AsyncResponce deleget=null;


        public CheckAsycn(EmployeeHomeActivity employeeHome)
        {
            this.activity= employeeHome;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialoge= new ProgressDialog(activity) ;

            pDialoge.setMessage("Please wait...");
            pDialoge.setIndeterminate(false);
            pDialoge.setCancelable(true);
            pDialoge.show();

        }

        @Override
        protected Void doInBackground(String... params) {
            InputStream inputStream = null;
            String result = "";
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpResponse httpResponse=httpClient.execute(new HttpGet(params[0]));
                inputStream=httpResponse.getEntity().getContent();
                if (inputStream!=null)
                {
                    result = convertInputStreamToString(inputStream);
                    parseLoginStatus(result);
                }

            }
            catch(Exception e)
            {
                System.out.println(e.getLocalizedMessage());
            }
            finally {
                if(inputStream != null)  {
                    try {
                        inputStream.close();
                    } catch(Exception e) {
                    }
                }
            }

            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialoge.dismiss();

            if (statusVO != null) {

                ArrayList<String> info2=new ArrayList<String>();
                for(int i=0;i<statusVO.getMessage().size();i++)
                    info2.add(statusVO.getMessage().get(i));


                adapter = new MyRecyclerAdapter(EmployeeHomeActivity.this, info2);

                mRecyle.setAdapter(adapter);

            }
            else Toast.makeText(EmployeeHomeActivity.this, "noooo", Toast.LENGTH_SHORT).show();

        }
        private MessageVO parseLoginStatus(String result)
        {
            try
            {
                Gson gson=new Gson();
                statusVO=gson.fromJson(result,MessageVO.class);


            }
            catch (Exception e)
            {

            }
            return statusVO;
        }
        private  String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            return result;

        }
    }


}
