package com.keystone.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class ShareExternalServer extends AsyncTask<String,Void,Void>
{
	DevRegVO dv=new DevRegVO();
	Activity activity;
	Context context;
	public ShareExternalServer(Activity activity)
	{
		this.activity=activity;context = activity;
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
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
		if(dv!=null)
		{
			Toast.makeText(activity,dv.getMessage(),Toast.LENGTH_LONG).show();


			String usertye=AppPreferences.getUserType(context);
			if(usertye.equalsIgnoreCase("Employee")) {
				activity.startActivity(new Intent(activity, EmployeeHome.class));
			}
			else
			{
				activity.startActivity(new Intent(activity, AdminHomeActivity.class));
			}
		}
	}

	private DevRegVO parseLoginStatus(String result)
	{
		try
		{
			Gson gson=new Gson();
			dv=gson.fromJson(result,DevRegVO.class);

		}
		catch (Exception e)
		{

		}
		return dv;
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
