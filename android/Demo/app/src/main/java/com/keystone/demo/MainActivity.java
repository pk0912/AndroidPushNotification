package com.keystone.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	ShareExternalServer appUtil;
	String regId,emp_id;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		regId = getIntent().getStringExtra("regId");
		Log.d("MainActivity", "regId: " + regId);

		final Context context = this;
		emp_id=AppPreferences.getUserEmpId(context);

		System.out.println("-----------------------" + emp_id + "--------------------------------");
		//sky lappy
		//String url="http://pkredroses.tk/Demo/rest/DevRegWebService/deviceRegister"+"/"+emp_id+"/"+regId;
		String url=Config.APP_SERVER_URL+"DevRegWebService/deviceRegister"+"/"+emp_id+"/"+regId;

		//pawan lappy
		//String url="http://192.168.137.1:8585/Demo/rest/DevRegWebService/deviceRegister"+"/"+emp_id+"/"+regId;
		appUtil=new ShareExternalServer(MainActivity.this);
		String[] data=new String[1];
		data[0]=url;
		appUtil.execute(data);
	}
}
