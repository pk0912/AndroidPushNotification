package com.keystone.demo.wifimac;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by praveen on 04-02-2016.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        context.startService(new Intent(context, WifiTrackerService.class));
    }
}
