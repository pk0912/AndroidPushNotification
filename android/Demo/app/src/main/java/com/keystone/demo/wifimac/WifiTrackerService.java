package com.keystone.demo.wifimac;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by praveen on 04-02-2016.
 */
public class WifiTrackerService extends Service
{
    private String str="";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        startTracking();
        return START_STICKY;
    }

    public void startTracking()
    {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        str=wifiInfo.getBSSID();
       /* if(str.equals(null))
        {
            str="str";
        }
*/
        SendStatus sendStatus=new SendStatus();
        sendStatus.sendstatusasync(str);
      /*  mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent i = new Intent(getApplicationContext(),
                DisplayMac.class);
        i.putExtra("msg", str);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                i, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.gcmnew)
                .setContentTitle("GCM Notification")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(str))
                .setContentText(str);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());*/
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

