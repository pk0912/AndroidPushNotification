package com.keystone.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
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

/**
 * Created by sky on 29-Oct-15.
 */
public class LogoutAsync extends AsyncTask<String,Void,Void>
{
    LogOutStatusVO lo;
    Activity activity;

    public LogoutAsync(Activity activity)
    {
        this.activity=activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (lo!=null)
        {
            if(lo.isStatus()==true) {
                Toast.makeText(activity, ""+lo.getMessage(), Toast.LENGTH_LONG).show();
                AppPreferences.setUserLoggedInStatus(activity, false);
                AppPreferences.setUserEmpId(activity, "");
                AppPreferences.setUserType(activity, "");
                AppPreferences.setUserRegId(activity, "");
                activity.startActivity(new Intent(activity, LoginActivity.class));
                //System.out.println(AppPreferences.getUserLoggedInStatus(activity));
             //   System.out.println(AppPreferences.getUserType(activity)+"<<<<<<<<<<<<<<<<<<<<<----------------------------");

            }
            else
            {
                Toast.makeText(activity, lo.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(activity, "oops!!! Something went wrong at server side", Toast.LENGTH_LONG).show();
        }
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

    private LogOutStatusVO parseLoginStatus(String result)
    {
        try
        {
            Gson gson=new Gson();
            lo=gson.fromJson(result,LogOutStatusVO.class);

        }
        catch (Exception e)
        {

        }
        return lo;
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
