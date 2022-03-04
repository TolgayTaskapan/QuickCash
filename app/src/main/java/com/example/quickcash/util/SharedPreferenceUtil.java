package com.example.quickcash.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceUtil {
    public static final String LOGIN_ATTRIBUTE = "logged_in";


    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setLoginStatus(Context ctx, Boolean isLoggedIn){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(LOGIN_ATTRIBUTE, isLoggedIn);
        editor.apply();
    }

    public static boolean isLoggedIn(Context ctx){
        return getSharedPreferences(ctx).getBoolean(LOGIN_ATTRIBUTE, false);
    }
}
