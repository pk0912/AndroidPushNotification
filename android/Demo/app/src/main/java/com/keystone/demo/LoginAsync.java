package com.keystone.demo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Window;
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
 * Created by sky on 25-Oct-15.
 */
public class LoginAsync extends AsyncTask<String,Void,Void>
{
    Dialog dialog;
    StatusVO statusVO;
    Activity activity;
    GoogleCloudMessaging gcm;
    Context context;
    String regId;

    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";

    static final String TAG = "Register Activity";

    public LoginAsync(Activity activity)
    {
        this.activity=activity;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        dialog = new Dialog(activity, R.style.Theme_Dialog_Custom_My);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_bar);
        //dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        dialog.setCancelable(false);
    }

    @Override
    protected Void doInBackground(String... params)
    {
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
        dialog.dismiss();
        super.onPostExecute(aVoid);
        if (statusVO!=null)
        {
            if(statusVO.isStatus()==true) {
                Toast.makeText(activity, "LoginSuccessful", Toast.LENGTH_LONG).show();
                AppPreferences.setUserLoggedInStatus(activity, true);
                AppPreferences.setUserEmpId(activity, ((EditText) activity.findViewById(R.id.emp_id)).getText().toString());
                AppPreferences.setUserType(activity, statusVO.getUsertype());
                System.out.println(AppPreferences.getUserLoggedInStatus(activity));
                System.out.println(AppPreferences.getUserType(activity)+"<<<<<<<<<<<<<<<<<<<<<----------------------------");
                if (statusVO.getUsertype().equalsIgnoreCase("Employee"))
                {
                    activity.startActivity(new Intent(activity, Register.class));
                    //code for gcm starts here

                }
                else
                {
                    activity.startActivity(new Intent(activity, AdminHomeActivity.class));
                }
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

    //functions for gcm registration






}
