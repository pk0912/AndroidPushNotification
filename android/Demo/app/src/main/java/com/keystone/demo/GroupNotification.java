package com.keystone.demo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

public class GroupNotification extends AppCompatActivity {

    CustomAdapterRecycler adapterRecycle;
    public DeptListVO deptListVO;
    public ArrayList<DeptVO> deptVO;
    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_list);
        recycler= (RecyclerView)findViewById(R.id.DeptList);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

    private void getData() {
        //String url="http://pkredroses.tk/Demo/rest/DepartmentListWebService/getDeptList";
        String url=Config.APP_SERVER_URL+"GroupNotifyWebService/getGroupList";
        String[] data=new String[1];
        data[0]=url;
        new GetDeptAsync(GroupNotification.this).execute(data);
    }

    private class GetDeptAsync extends AsyncTask<String,Void,ArrayList<DeptVO>> {

        Dialog dialog;
        Activity activity;

        public GetDeptAsync(Activity deptNotificationActivity) {
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
        protected ArrayList<DeptVO> doInBackground(String... params) {
            InputStream inputStream = null;
            String result = "";
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(new HttpGet(params[0]));
                inputStream = httpResponse.getEntity().getContent();
                if (inputStream != null) {
                    System.out.println(inputStream);
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
            //dialog.dismiss();
            if (deptListVO != null) {
                adapterRecycle = new CustomAdapterRecycler(activity,eL);
                recycler.setAdapter(adapterRecycle);
                checkButtonClick();

                Toast.makeText(activity, "list came from server", Toast.LENGTH_LONG).show();
                // return subjectsList;

            } else {
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
    private class CustomAdapterRecycler extends RecyclerView.Adapter<CustomAdapterRecycler.MyViewHolder> {
        private LayoutInflater inflater;
        Context context;
        List<DeptVO> list;

        public CustomAdapterRecycler(Context context,List<DeptVO> list)
        {
            this.context=context;
            inflater=LayoutInflater.from(context);
            this.list=list;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
        {
            View view =inflater.inflate(R.layout.listviewcheckboxlayout,parent,false);
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
                    Toast.makeText(getApplicationContext(),
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

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView code;
            CheckBox name;


            public MyViewHolder(View itemView) {
                super(itemView);
                code = (TextView) itemView.findViewById(R.id.code);
                name = (CheckBox) itemView.findViewById(R.id.checkBox1);

            }


            @Override
            public void onClick(View v) {

            }
        }

    }

    private void checkButtonClick() {
       // Button submit = (Button) findViewById(R.id.send);
        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.send);
        fb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              /*  StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");*/
                List<DeptVO> deptList1 = adapterRecycle.list;
                ArrayList<DeptVO> deptListVO1 = new ArrayList<DeptVO>();
                int siz = deptList1.size();
                System.out.println("Size :" + siz);
                String responseText[] = new String[siz];
                for (int i = 0; i < deptList1.size(); i++) {
                    DeptVO department = deptList1.get(i);
                    System.out.println("groupVo id :" + department.getDept_id());
                    if (department.isSelected()) {
                        System.out.println("GroupVo checked :" + department.getDept_id());
                        deptListVO1.add(department);
                        //Toast.makeText(getApplicationContext(),responseText[i],Toast.LENGTH_SHORT).show();
                    }
                }
                deptListVO.setDeptlist(deptListVO1);
                Gson gson = new Gson();
                String deptList = gson.toJson(deptListVO);
                String message = null;
                EditText et = (EditText) findViewById(R.id.notbox);
                message = et.getText().toString();

                if (message == null) {
                    Toast.makeText(getApplicationContext(), "Please Enter Message", Toast.LENGTH_SHORT).show();
                } else {
                    String usermessage = message.replaceAll(" ", "%20");
                    message = usermessage;
                    String url=Config.APP_SERVER_URL+"GroupNotifyWebService";
                    NotificationPost notificationasync = new NotificationPost(GroupNotification.this);
                    String[] data = new String[3];
                    data[0] = url;
                    data[1] = message;
                    data[2] = deptList;

                    notificationasync.execute(data);
                /*Intent i =new Intent(getApplicationContext(),NotificationSendPage.class);
                i.putExtra("messagetype","dept");
                i.putExtra("dept",responseText);
                startActivity(i);*/
                }
            }
        });
    }
}
