package com.keystone.demo.wifimac;

import java.io.Serializable;

/**
 * Created by praveen on 04-02-2016.
 */
public class WifiStatusVO implements Serializable
{
    private String status;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

}
