package com.keystone.demo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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

public class CreateGroup extends AppCompatActivity {

    AdminConfigRecycler adapterRecycle,empAdptRecycler;
    AdminConfigRecycler1 adapterRecycle1;
    public DeptListVO deptListVO=null;
    public GroupListVO groupListVO=null;
    public ArrayList<DeptVO> deptVO;
    RecyclerView recycler,recycler1,empRecycler;
    public EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        recycler= (RecyclerView)findViewById(R.id.groupList);
        empRecycler=(RecyclerView)findViewById(R.id.empType);
        recycler1=(RecyclerView)findViewById(R.id.groupType);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler1.setLayoutManager(new LinearLayoutManager(this));
        empRecycler.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

    private void getData() {
        //String url="http://pkredroses.tk/Demo/rest/DepartmentListWebService/getDeptList";
        String url=Config.APP_SERVER_URL+"AdminConfigWebService/getGroup";
        String[] data=new String[1];
        data[0]=url;
        new AdminConfigAsync1(CreateGroup.this).execute(data);
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
                adapterRecycle = new AdminConfigRecycler(activity, eL, R.layout.listviewcheckboxlayout);
                empAdptRecycler=new AdminConfigRecycler(activity,deptListVO.getEmplist(), R.layout.listviewcheckboxlayout);
                recycler1.setAdapter(adapterRecycle);
                empRecycler.setAdapter(empAdptRecycler);
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

    public class AdminConfigAsync1 extends AsyncTask<String,Void,ArrayList<GroupVO>> {
        Activity activity;
        public AdminConfigAsync1(Activity deptNotificationActivity) {
            this.activity=deptNotificationActivity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<GroupVO> doInBackground(String... params) {
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

            return groupListVO.getGrouplist();
        }

        @Override
        protected void onPostExecute(ArrayList<GroupVO> eL) {
            super.onPostExecute(eL);

            int f=0;
            if(groupListVO!=null)
            {
                System.out.println("Group Value:"+eL+" group:"+groupListVO.getGrouplist());
                f=1;
                adapterRecycle1 = new AdminConfigRecycler1(activity,eL,R.layout.listviewlayout);
                recycler.setAdapter(adapterRecycle1);
                String url=Config.APP_SERVER_URL+"AdminConfigWebService/getDeptList";
                String[] data=new String[1];
                data[0]=url;
                new AdminConfigAsync(CreateGroup.this).execute(data);
            }
            if (f==0) {
                Toast.makeText(activity, "oops!!! Something went wrong at server side", Toast.LENGTH_LONG).show();
                // return subjectsList;
            }
        }
        private GroupListVO parseLoginStatus(String result) {
            try {
                Gson gson = new Gson();
                groupListVO = gson.fromJson(result, GroupListVO.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("helllllllllllllloooooooooooooooooooooooooooo");
            return groupListVO;
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

    private class AdminConfigRecycler1 extends RecyclerView.Adapter<AdminConfigRecycler1.MyViewHolder1>  {
        private LayoutInflater inflater;
        Context context;
        List<GroupVO> list;

        public AdminConfigRecycler1(Context context,List<GroupVO> list,int textViewResourceId )
        {
            this.context=context;
            inflater=LayoutInflater.from(context);
            this.list=list;
        }
        class MyViewHolder1 extends RecyclerView.ViewHolder {
            TextView name;
            public MyViewHolder1(View itemView) {
                super(itemView);
                name = (TextView)itemView.findViewById(R.id.name);
            }
        }

        @Override
        public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {

            View view =inflater.inflate(R.layout.listviewlayout, parent, false);
            MyViewHolder1 holder1=new MyViewHolder1(view);
            return holder1;

        }

        @Override
        public void onBindViewHolder(MyViewHolder1 holder1, int position) {
            GroupVO group = list.get(position);
            System.out.println("Name:"+group.getGname());
            holder1.name.setText(group.getGname());
        }
        @Override
        public int getItemCount() {
            return list.size();
        }


    }

    private class AdminConfigRecycler extends RecyclerView.Adapter<AdminConfigRecycler.MyViewHolder>  {
        private LayoutInflater inflater;
        Context context;
        List<DeptVO> list;

        public AdminConfigRecycler(Context context,List<DeptVO> list,int textViewResourceId )
        {
            this.context=context;
            inflater=LayoutInflater.from(context);
            this.list=list;
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView code;
            CheckBox name;


            public MyViewHolder(View itemView) {
                super(itemView);
                code = (TextView) itemView.findViewById(R.id.code);
                name = (CheckBox) itemView.findViewById(R.id.checkBox1);

            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view =inflater.inflate(R.layout.listviewcheckboxlayout, parent, false);
            MyViewHolder holder=new MyViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            DeptVO department = list.get(position);
            holder.code.setText(" (" + department.getDept_id() + ")");
            holder.name.setText(department.getDept_name());
            holder.name.setChecked(department.isSelected());
            holder.name.setTag(department);
            holder.name.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    DeptVO department = (DeptVO) cb.getTag();
                    Toast.makeText(context,
                            "Clicked on Checkbox: " + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();
                    System.out.print("To check Checkbox: " + cb.isChecked());
                    department.setSelected(cb.isChecked());
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }

    public void checkButtonClick() {
        FloatingActionButton fb= (FloatingActionButton) findViewById(R.id.send);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DeptVO> deptList1 = adapterRecycle.list;
                List<DeptVO> empList=empAdptRecycler.list;
                ArrayList<DeptVO> deptListVO1 = new ArrayList<DeptVO>();
                ArrayList<DeptVO> empListVO = new ArrayList<DeptVO>();
                int siz = deptList1.size();
                System.out.println("Size :" + siz);
                for (int i = 0; i < deptList1.size(); i++) {
                    DeptVO department = deptList1.get(i);
                    System.out.println("DeptVo id :" + department.getDept_id());
                    if (department.isSelected()) {
                        System.out.println("DeptVo checked :" + department.getDept_id());
                        deptListVO1.add(department);
                        //Toast.makeText(getApplicationContext(),responseText[i],Toast.LENGTH_SHORT).show();
                    }
                }
                for (int i = 0; i < empList.size(); i++) {
                    DeptVO department = empList.get(i);
                    System.out.println("DeptVo id :" + department.getDept_id());
                    if (department.isSelected()) {
                        System.out.println("DeptVo checked :" + department.getDept_id());
                        empListVO.add(department);
                        //Toast.makeText(getApplicationContext(),responseText[i],Toast.LENGTH_SHORT).show();
                    }
                }
                deptListVO.setDeptlist(deptListVO1);
                deptListVO.setEmplist(empListVO);
                Gson gson = new Gson();
                String deptList = gson.toJson(deptListVO);

                name=(EditText)findViewById(R.id.eGName);
                String groupname=name.getText().toString();

                String url = Config.APP_SERVER_URL+"AdminConfigWebService/savegroup" ;
                StatusAsync st = new StatusAsync(StatusAsync.POST_TASK,CreateGroup.this,"Saving Group.......");
                st.addNameValuePair("groupname", groupname);
                st.addNameValuePair("deptList", deptList);
                // the passed String is the URL we will POST to
                st.execute(new String[]{url});

            }
        });
       /* Button submit = (Button) findViewById(R.id.send);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              *//*  StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");*//*
                List<DeptVO> deptList1 = adapterRecycle.list;
                List<DeptVO> empList=empAdptRecycler.list;
                ArrayList<DeptVO> deptListVO1 = new ArrayList<DeptVO>();
                ArrayList<DeptVO> empListVO = new ArrayList<DeptVO>();
                int siz = deptList1.size();
                System.out.println("Size :" + siz);
                for (int i = 0; i < deptList1.size(); i++) {
                    DeptVO department = deptList1.get(i);
                    System.out.println("DeptVo id :" + department.getDept_id());
                    if (department.isSelected()) {
                        System.out.println("DeptVo checked :" + department.getDept_id());
                        deptListVO1.add(department);
                        //Toast.makeText(getApplicationContext(),responseText[i],Toast.LENGTH_SHORT).show();
                    }
                }
                for (int i = 0; i < empList.size(); i++) {
                    DeptVO department = empList.get(i);
                    System.out.println("DeptVo id :" + department.getDept_id());
                    if (department.isSelected()) {
                        System.out.println("DeptVo checked :" + department.getDept_id());
                        empListVO.add(department);
                        //Toast.makeText(getApplicationContext(),responseText[i],Toast.LENGTH_SHORT).show();
                    }
                }
                deptListVO.setDeptlist(deptListVO1);
                deptListVO.setEmplist(empListVO);
                Gson gson = new Gson();
                String deptList = gson.toJson(deptListVO);

                name=(EditText)findViewById(R.id.eGName);
                String groupname=name.getText().toString();

                String url = Config.APP_SERVER_URL+"AdminConfigWebService/savegroup" ;
                StatusAsync st = new StatusAsync(StatusAsync.POST_TASK,CreateGroup.this,"Saving Group.......");
                st.addNameValuePair("groupname", groupname);
                st.addNameValuePair("deptList", deptList);
                // the passed String is the URL we will POST to
                st.execute(new String[]{url});
            }

        });*/
    }
}