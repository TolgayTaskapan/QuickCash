package com.example.quickcash.account;

import android.content.Context;

import com.example.quickcash.R;
import com.google.firebase.database.DatabaseReference;

public class LoginValidator {

    private final Context context;
    private String errorMsg = "";
    DatabaseReference database;

    public LoginValidator(Context context, DatabaseReference database) {
        this.context = context.getApplicationContext();
        this.database = database;
    }

    /**
     * get error message
     **/
    public String getErrorMsg() {
        return this.errorMsg;
    }

    /**
     * set error message
     **/
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * check if the user has entered a username
     **/
    public static boolean isEmptyUsername(String username) {
        return username.isEmpty();
    }

    /**
     * check if the user has entered a password
     **/
    public static boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }


    public boolean authenticateUserCredentials(String username, String password){

        boolean validDetails = false;

        if (isEmptyUsername(username) || isEmptyPassword(password)) {
            setErrorMsg(this.context.getResources().getString(R.string.EMPTY_USERNAME_OR_PASSWORD).trim());
        } else {
            validDetails = true;
            setErrorMsg(context.getResources().getString(R.string.EMPTY_STRING).trim());
        }


        return validDetails;
    }

}
