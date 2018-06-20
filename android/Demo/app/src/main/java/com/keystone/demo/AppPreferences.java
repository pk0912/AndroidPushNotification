package com.keystone.demo;

/**
 * Created by sky on 25-Oct-15.
 */
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sky on 03-Jul-15.
 */
public class AppPreferences
{


    public static void setUserLoggedInStatus(Context context , boolean isLoggedStatus)
    {
        SharedPreferences preferences = context.getSharedPreferences("demoPrefee", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("logged_in_status", isLoggedStatus);
        editor.commit();
    }


    public static boolean getUserLoggedInStatus(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("demoPrefee", 0);
        return preferences.getBoolean("logged_in_status",false);
    }
    public static void setUserEmpId(Context context,String empId)
    {
        SharedPreferences preferences=context.getSharedPreferences("demoPrefee2", 0);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("empId",empId);
        editor.commit();


    }
    public static String getUserEmpId(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("demoPrefee2", 0);
        return preferences.getString("empId","");

    }
    public static void setUserType(Context context,String usertype)
    {
        System.out.println("user**************************************** is" + usertype);
        SharedPreferences preferences=context.getSharedPreferences("demoPrefee1", 0);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("usertype",usertype);
        editor.commit();
    }
    public static String getUserType(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("demoPrefee1", 0);
        //System.out.println(preferences.getString("usertype",":P"));
        return preferences.getString("usertype","");
    }
    public static void setUserRegId(Context context,String RegId)
    {
        SharedPreferences preferences=context.getSharedPreferences("demoPrefee3", 0);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("RegId",RegId);
        editor.commit();
    }
    public static String getUserRegId(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("demoPrefee3", 0);
        return preferences.getString("RegId","");
    }
}

