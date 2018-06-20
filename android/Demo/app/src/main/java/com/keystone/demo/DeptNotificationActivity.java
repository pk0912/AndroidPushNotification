package com.keystone.demo;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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


public class DeptNotificationActivity extends ActionBarActivity {

    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_notification);
        recycler= (RecyclerView)findViewById(R.id.DeptList);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

    private void getData() {
        //String url="http://pkredroses.tk/Demo/rest/EmployeeListWebService/getEmpList";
        String url=Config.APP_SERVER_URL+"EmployeeListWebService/getEmpList";

        String[] data=new String[1];
        data[0]=url;
        new GetEmpAsync(DeptNotificationActivity.this).execute(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dept_notification, menu);
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

    private class GetEmpAsync extends AsyncTask<String,Void,ArrayList<EmpVO>> {
        private CustomAdapterRecycler adapterRecycle;
        Dialog dialog;
        Activity activity;
        EmpListVO empList;
        public GetEmpAsync(Activity deptNotificationActivity) {
            this.activity=deptNotificationActivity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // dialog = new Dialog(activity, R.style.Theme_Dialog_Custom_My);
           // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
           // dialog.setContentView(R.layout.custom_progress_bar);
            //dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
           // dialog.show();
        }


        @Override
        protected ArrayList<EmpVO> doInBackground(String... params) {
            InputStream inputStream = null;
            String result = "";
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(new HttpGet(params[0]));
                inputStream = httpResponse.getEntity().getContent();
                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                    System.out.println("param 0 " + params[0]);
                    parseLoginStatus(result);
                }

            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e) {
                    }
                }
            }



            return empList.getEmplist();
        }


        @Override
        protected void onPostExecute(ArrayList<EmpVO> eL) {
            super.onPostExecute(eL);
            //dialog.dismiss();
            if (empList != null) {
                adapterRecycle = new CustomAdapterRecycler(activity,empList.getEmplist());
                recycler.setAdapter(adapterRecycle);
                //adapterRecycle.upDateEntries(sub);
                //adapterRecycle.notifyDataSetChanged();

                Toast.makeText(activity, "list came from server", Toast.LENGTH_LONG).show();
                // return subjectsList;

            } else {
                Toast.makeText(activity, "oops!!! Something went wrong at server side", Toast.LENGTH_LONG).show();
                // return subjectsList;
            }
        }


        private EmpListVO parseLoginStatus(String result) {
            try {
                Gson gson = new Gson();
                empList = gson.fromJson(result, EmpListVO.class);

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("helllllllllllllloooooooooooooooooooooooooooo");
            return empList;
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            return result;

        }

    }
}
