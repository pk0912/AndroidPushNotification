package com.keystone.demo.wifimac;
import android.os.AsyncTask;

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
 * Created by praveen on 04-02-2016.
 */
public class StatusAsync extends AsyncTask<String,Void,Void>
{
    WifiStatusVO wifistatusVO;
    //Context context;
    public StatusAsync()
    {
        //this.context=context;
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

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        return result;

    }
    private WifiStatusVO parseLoginStatus(String result)
    {
        try
        {
            Gson gson=new Gson();
            wifistatusVO=gson.fromJson(result,WifiStatusVO.class);

        }
        catch (Exception e)
        {

        }
        return wifistatusVO;
    }
}
