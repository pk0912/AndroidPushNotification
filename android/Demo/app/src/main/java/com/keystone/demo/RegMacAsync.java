package com.keystone.demo;

import android.app.Activity;
import android.os.AsyncTask;
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
 * Created by praveen on 21-02-2016.
 */
public class RegMacAsync extends AsyncTask<String,Void,Void>
{

    RegMacVO regMacVO;
    Activity activity;

    public RegMacAsync(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute()
    {
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
    protected void onPostExecute(Void aVoid) {
        if (regMacVO!=null)
        {
            if(regMacVO.isStatus()==true) {
                Toast.makeText(activity, "" + regMacVO.getMessage(), Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(activity, regMacVO.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(activity, "oops!!! Something went wrong at server side", Toast.LENGTH_LONG).show();
        }
    }

    private RegMacVO parseLoginStatus(String result)
    {
        try
        {
            Gson gson=new Gson();
            regMacVO = gson.fromJson(result,RegMacVO.class);

        }
        catch (Exception e)
        {

        }
        return regMacVO;
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
