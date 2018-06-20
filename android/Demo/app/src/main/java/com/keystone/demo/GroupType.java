package com.keystone.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

public class GroupType extends AppCompatActivity {

    GroupTypeCustomAdapter adapterRecycle;
    public DeptListVO deptListVO=null;
    public ArrayList<DeptVO> deptVO;
    EditText name;
    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_type);
        name = (EditText) findViewById(R.id.eGType);
        recycler= (RecyclerView)findViewById(R.id.GroupList);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

    public void getData() {
        String url=Config.APP_SERVER_URL+"DepartmentListWebService/getDeptList";
        String[] data=new String[1];
        data[0]=url;
        new AdminConfigAsync(GroupType.this).execute(data);
    }

    public class AdminConfigAsync extends AsyncTask<String,Void,ArrayList<DeptVO>> {
        Activity activity;

        public AdminConfigAsync(Activity deptNotificationActivity) {
            this.activity=deptNotificationActivity;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<DeptVO> doInBackground(String... params) {
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
            return deptListVO.getDeptlist();
        }

        @Override
        protected void onPostExecute(ArrayList<DeptVO> eL) {
            super.onPostExecute(eL);


            int f=0;
            if (deptListVO != null) {
                f = 1;
                adapterRecycle = new GroupTypeCustomAdapter(activity, eL, R.layout.listviewcheckboxlayout);
                recycler.setAdapter(adapterRecycle);
                checkButtonClick();
                Toast.makeText(activity, "list came from server", Toast.LENGTH_LONG).show();
                // return subjectsList;
            }
            if (f==0) {
                Toast.makeText(activity, "oops!!! Something went wrong at server side", Toast.LENGTH_LONG).show();
                // return subjectsList;
            }
        }
        private DeptListVO parseLoginStatus(String result) {
            try {
                Gson gson = new Gson();
                deptListVO = gson.fromJson(result, DeptListVO.class);

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("helllllllllllllloooooooooooooooooooooooooooo");
            return deptListVO;
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

    private class GroupTypeCustomAdapter extends RecyclerView.Adapter<GroupTypeCustomAdapter.MyViewHolder>  {
        private LayoutInflater inflater;
        Context context;
        List<DeptVO> list;
        public GroupTypeCustomAdapter(Context context,List<DeptVO> list,int textViewResourceId )
        {
            this.context=context;
            inflater=LayoutInflater.from(context);
            this.list=list;
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            public MyViewHolder(View itemView) {
                super(itemView);
                name = (TextView)itemView.findViewById(R.id.name);
            }
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view =inflater.inflate(R.layout.listviewlayout, parent, false);
            MyViewHolder holder=new MyViewHolder(view);
            return holder;
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            DeptVO group = list.get(position);
            System.out.println("Name:" + group.getDept_name());
            holder.name.setText(group.getDept_name());
        }
        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private void checkButtonClick() {
       // Button submit = (Button) findViewById(R.id.send);
        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.send);
        fb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String groupname = name.getText().toString();
                String url = Config.APP_SERVER_URL + "AdminConfigWebService/saveGroupType" + "/" + groupname;
                StatusAsync statusasync = new StatusAsync(StatusAsync.GET_TASK,GroupType.this,"Creating Group Type......");
                String[] data = new String[1];
                data[0] = url;
                statusasync.execute(data);
               // getApplicationContext().startActivity(new Intent(getApplicationContext(), GroupType.class));
            }
        });
    }

}
