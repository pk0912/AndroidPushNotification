package com.keystone.demo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class EmployeeHome extends AppCompatActivity implements AsyncResponce, View.OnClickListener {
    private static final String TAG = "RecyclerViewExample";

    private List<EmployeeDetailVO> feedItemList = new ArrayList<EmployeeDetailVO>();
    LinearLayout.LayoutParams params;
    LinearLayout lm;
    private Boolean isFabOpen = false;
    private RecyclerView mRecyclerView,mRecyle;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    FloatingActionButton fab, fab1, fab2;
    public static ArrayList<String> statusVO1;
    private MyRecyclerAdapter adapter;
    String emp_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        /* Initialize recyclerview */

        mRecyle = (RecyclerView) findViewById(R.id.recycler_view1);
        mRecyle.setLayoutManager(new LinearLayoutManager(this));
        emp_id= AppPreferences.getUserEmpId(getApplicationContext());
        /*Downloading data from below url*/

        senddata();
       /* Toast.makeText(getApplicationContext(),"employee id="+emp_id,Toast.LENGTH_SHORT).show();
        String uri = Config.APP_SERVER_URL+"EmployeeDetailWS/getid/"+emp_id;
        CheckAsycn obj = new CheckAsycn(EmployeeHome.this);
        obj.deleget = this;
        obj.execute(uri);*/


       /* if(statusVO1==null){
            Toast.makeText(getApplicationContext(),"null value",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(),"aagyi"+statusVO1.getName(),Toast.LENGTH_SHORT).show();*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
    }

    private void senddata() {
        String uri = Config.APP_SERVER_URL+"EmployeeDetailWS/getid/"+emp_id;
        CheckAsycn obj = new CheckAsycn(EmployeeHome.this);
        obj.deleget = this;
        obj.execute(uri);
    }


    @Override
    public void onClick(View v) {
        final View v1=v;
        int id = v.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:
                if (isFabOpen) {

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
                    Log.d("Raj", "open");

                }

                AlertDialog.Builder alert = new AlertDialog.Builder(EmployeeHome.this);
                alert.setTitle("Edit Details");
                final EditText input1 = new EditText(EmployeeHome.this);
                final EditText input2 = new EditText(EmployeeHome.this);

                LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                 LinearLayout l1=new LinearLayout(getApplication());
                l1.setOrientation(LinearLayout.VERTICAL);

                Toast.makeText(getApplicationContext(),statusVO1.get(1)+"  "+statusVO1.get(2),Toast.LENGTH_SHORT).show();
                    input1.setText(statusVO1.get(1));
                input2.setText(statusVO1.get(2));
                    input1.setLayoutParams(ip);
                input2.setLayoutParams(ip);
                    l1.addView(input1);
                l1.addView(input2);

                alert.setView(l1);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email,phone;
                        email=input1.getText().toString().trim();
                        phone=input2.getText().toString().trim();
                        String uri = Config.APP_SERVER_URL+"EmployeeDetailWS/updatedata/"+email+"/"+phone+"/"+emp_id;
                        UpdateDetailAsyn obj = new UpdateDetailAsyn(EmployeeHome.this);
                        obj.execute(uri);

                          //  Toast.makeText(getApplicationContext(),input1.getText()+"  "+input2.getText(),Toast.LENGTH_SHORT).show();


                }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });


                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                break;
            case R.id.fab2:
                if (isFabOpen) {

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
                    Log.d("Raj", "open");

                }
                //Toast.makeTInext(getApplicationContext(), "hii", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(EmployeeHome.this,EmployeeHomeActivity.class);
                startActivity(i);

                break;
        }
    }







      public void animateFAB(){

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
              Log.d("Raj", "open");

          }
      }
    @Override
    public void processFinesh(ArrayList<String> Output) {

           statusVO1=Output;

    }


    private class UpdateDetailAsyn extends AsyncTask<String,Void,Void> {

        Dialog dialog;
        Activity activity;
        Context context;
        String regId;
        ProgressDialog pDialoge;
        EmployeeDetailVO statusVO;
       // public AsyncResponce deleget=null;


        public UpdateDetailAsyn(EmployeeHome employeeHome)
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

                Toast.makeText(getApplicationContext(), ""+statusVO.getMessage(), Toast.LENGTH_SHORT).show();

             senddata();
                /*ArrayList<String> info3=new ArrayList<String>();
                info3.add(statusVO.getEmpname());
                info3.add(statusVO.getEmail());
                info3.add(statusVO.getPhone());
                info3.add(statusVO.getDeptname());
                for(int i=0;i<statusVO.getGrpname().size();i++)
                    info3.add(statusVO.getGrpname().get(i));
                deleget.processFinesh(info3);

                adapter = new MyRecyclerAdapter(EmployeeHome.this, info3);

                mRecyle.setAdapter(adapter);
*/
            }
            else
            {
                Toast.makeText(getApplicationContext(), "nooooo", Toast.LENGTH_SHORT).show();
            }

        }
        private EmployeeDetailVO parseLoginStatus(String result)
        {
            try
            {
                Gson gson=new Gson();
                statusVO=gson.fromJson(result,EmployeeDetailVO.class);


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





    private class CheckAsycn extends AsyncTask<String,Void,Void> {

        Dialog dialog;
        Activity activity;
        Context context;
        String regId;
        ProgressDialog pDialoge;
        EmployeeDetailVO statusVO;
        public AsyncResponce deleget=null;


        public CheckAsycn(EmployeeHome employeeHome)
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
                info2.add(statusVO.getEmpname());
                info2.add(statusVO.getEmail());
                info2.add(statusVO.getPhone());
                info2.add(statusVO.getDeptname());
                for(int i=0;i<statusVO.getGrpname().size();i++)
                    info2.add(statusVO.getGrpname().get(i));
                deleget.processFinesh(info2);

                adapter = new MyRecyclerAdapter(EmployeeHome.this, info2);

                mRecyle.setAdapter(adapter);




            }

        }
        private EmployeeDetailVO parseLoginStatus(String result)
        {
            try
            {
                Gson gson=new Gson();
                statusVO=gson.fromJson(result,EmployeeDetailVO.class);


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
