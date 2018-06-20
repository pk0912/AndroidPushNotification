package com.keystone.demo.wifimac;

/**
 * Created by praveen on 04-02-2016.
 */
public class SendStatus
{
    public void sendstatusasync(String str)
    {
        String url = "http://pkredroses.tk/Demo/rest/WifiMacChecker/checker/1/"+str;
        StatusAsync statusAsync=new StatusAsync();
        String[] data = new String[1];
        data[0] = url;
        statusAsync.execute(data);
    }
}
