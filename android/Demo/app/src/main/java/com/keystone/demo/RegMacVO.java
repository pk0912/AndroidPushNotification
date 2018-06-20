package com.keystone.demo;

import java.io.Serializable;

/**
 * Created by praveen on 21-02-2016.
 */
public class RegMacVO implements Serializable
{
    private boolean status;
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
