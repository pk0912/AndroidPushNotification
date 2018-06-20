package com.keystone.demo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by Yogender Kumar on 22-01-2016.
 */
public class NotificationPost extends AsyncTask<String,Void,Void>{

        StatusVO statusVO;
        Activity activity;
        GoogleCloudMessaging gcm;
        Context context;
        String regId;

        public static final String REG_ID = "regId";
        private static final String APP_VERSION = "appVersion";

        static final String TAG = "Register Activity";
        private ArrayList<NameValuePair> params1 = new ArrayList<NameValuePair>();
        public NotificationPost(Activity activity)
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
                HttpClient httpclient=new DefaultHttpClient();
                HttpPost httppost = new HttpPost(params[0]);
                // Add parameters
                params1.add(new BasicNameValuePair("message", params[1]));
                params1.add(new BasicNameValuePair("dept", params[2]));
                HttpResponse response=null;
                httppost.setEntity(new UrlEncodedFormEntity(params1));

                response = httpclient.execute(httppost);
                //HttpClient httpClient=new DefaultHttpClient();
                //HttpResponse httpResponse=httpClient.execute(new HttpPost(params[0]));
                inputStream=response.getEntity().getContent();
                if (inputStream!=null)
                {
                    result = convertInputStreamToString(inputStream);
                    parseLoginStatus(result);
                }
                System.out.println("IN the Do in Bagrournd process "+result);

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
                System.out.println("IN the Do in Bagrournd process parse");
            }
            catch (Exception e)
            {
                e.printStackTrace();
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
