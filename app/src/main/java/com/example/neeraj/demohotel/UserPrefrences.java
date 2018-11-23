package com.example.neeraj.demohotel;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author neeraj on 19/11/18.
 */
public class  UserPrefrences {
    public static final String PREFNAME = "demohoel";

    public static void setUserName(Context context, String username) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE).edit();
        editor.putString("name", username);
        editor.commit();
    }

    public static String getUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", "username");
    }

    public static void setEmail(Context context, String email) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE).edit();
        editor.putString("email", email);
        editor.commit();
    }

    public static String getEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }

    public static void setMobile(Context context, String mobile) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE).edit();
        editor.putString("mobile", mobile);
        editor.commit();
    }

    public static String getMobile(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("mobile", "username");
    }

    public static void setAddress(Context context, String address) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE).edit();
        editor.putString("address", address);
        editor.commit();
    }

    public static String getAddress(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("address", "address");
    }

    public static void setPassword(Context context, String password) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE).edit();
        editor.putString("password", password);
        editor.commit();
    }

    public static String getPassword(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("password", "username");
    }

    public static void setLogin(Context context, boolean v) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean("login", v);
        editor.commit();
    }

    public static Boolean getLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("login", false);
    }
    public static void setHotelId(String id,Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE).edit();
        editor.putString("id", id);
        editor.commit();
    }
    public static String getHotelId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", "");
    }
}
