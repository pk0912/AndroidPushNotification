package com.keystone.demo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Yogender Kumar on 08-02-2016.
 */
public class StatusAsync extends AsyncTask<String,Void,Void> {
    Dialog dialog;
    StatusVO statusVO;
    Activity activity;
    GoogleCloudMessaging gcm;
    Context context;
    String regId;
    public static final int POST_TASK = 1;
    public static final int GET_TASK = 2;
    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";
    static final String TAG = "Register Activity";
    private int taskType = GET_TASK;
    private String processMessage = "Processing...";
    private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    private ProgressDialog pDlg = null;

    public StatusAsync(int taskType, Activity activity, String processMessage) {
        this.taskType = taskType;
        this.activity = activity;
        context=activity;
        this.processMessage = processMessage;
    }

    public void addNameValuePair(String name, String value) {

        params.add(new BasicNameValuePair(name, value));
    }

   /* private void hideKeyboard() {

        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(
                activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }*/

    private void showProgressDialog() {
        pDlg = new ProgressDialog(context);
        pDlg.setMessage(processMessage);
        pDlg.setProgressDrawable(context.getWallpaper());
        pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDlg.setCancelable(false);
        pDlg.show();

    }
    @Override
    protected void onPreExecute() {
        //hideKeyboard();
        showProgressDialog();
    }

    protected Void doInBackground(String... urls) {

        String url = urls[0];
        InputStream inputStream = null;
        String result = "";
        HttpClient httpclient=new DefaultHttpClient();
        HttpResponse response=null;
        try
        {
            switch (taskType) {

                case POST_TASK:
                    HttpPost httppost = new HttpPost(url);
                    // Add parameters
                    httppost.setEntity(new UrlEncodedFormEntity(params));

                    response = httpclient.execute(httppost);
                    break;
                case GET_TASK:
                    HttpGet httpget = new HttpGet(url);
                    response = httpclient.execute(httpget);
                    break;
            }
            inputStream=response.getEntity().getContent();
            if (inputStream!=null)
            {
                result = convertInputStreamToString(inputStream);
                parseLoginStatus(result);
                System.out.println("Result:"+result);
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

    // Establish connection and socket (data retrieval) timeouts
    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
        pDlg.dismiss();
        System.out.println("Status:"+statusVO);
        if (statusVO!=null)
        {
            Toast.makeText(activity, statusVO.getMessage(), Toast.LENGTH_LONG).show();
            Intent intent=new Intent();
            intent.setClass(activity, activity.getClass());
            intent.putExtra("grpname",processMessage);
            activity.startActivity(intent);
        }
        else
        {
            Toast.makeText(activity, "oops!!! Something went wrong at server side", Toast.LENGTH_LONG).show();
        }
    }

    private StatusVO parseLoginStatus(String result)
    {
        try
        {
            Gson gson=new Gson();
            statusVO=gson.fromJson(result,StatusVO.class);

        }
        catch (Exception e)
        {

        }
        return statusVO;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        return result;
    }
}
