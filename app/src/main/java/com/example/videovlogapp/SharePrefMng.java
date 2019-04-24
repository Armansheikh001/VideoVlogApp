package com.example.videovlogapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefMng {
    private static SharePrefMng prefMng;
    private static Context mCntx;

    public static final String SHARED_PREF_NAME = "myShare12";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ID = "userid";

    private SharePrefMng(Context context) {

        mCntx = context;

    }

    public static synchronized SharePrefMng getInstance(Context context) {
        if (prefMng == null) {
            prefMng = new SharePrefMng(context);
        }

        return prefMng;
    }

//    Login Function

    public boolean userLogin(int id, String name, String email) {

        SharedPreferences sharedPref = mCntx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(KEY_ID, id);
        editor.putString(KEY_USERNAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
        return true;
    }

    // Login check function
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCntx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.getString(KEY_USERNAME, null) != null) {

            return true;
        }

        return false;
    }

    //    Logout Function
    public boolean logout(){
        SharedPreferences sharedPreferences = mCntx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    //    get username
    public String getUsername(){

        SharedPreferences sharedPreferences = mCntx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getUserEmail(){

        SharedPreferences sharedPreferences = mCntx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL,null);
    }


}