package com.keystone.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Yogender Kumar on 15-01-2016.
 */
public class NotificationSendPageAsync extends AsyncTask<String,Void,Void> {

    StatusVO statusVO;
    Activity activity;
    GoogleCloudMessaging gcm;
    Context context;
    String regId;

    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";

    static final String TAG = "Register Activity";

    public NotificationSendPageAsync(Activity activity)
    {
        this.activity=activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
        if (statusVO!=null)
        {
            if(statusVO.isStatus()==true) {
                Toast.makeText(activity, "Message Send Successfull", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(activity, statusVO.getMessage(), Toast.LENGTH_LONG).show();
            }
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
