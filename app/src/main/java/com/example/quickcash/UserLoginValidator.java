package com.example.quickcash;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class UserLoginValidator {

    private final Context context;
    private String errorMsg = "";
    DatabaseReference database;
    public boolean INVALID_USER = true;

    public UserLoginValidator(Context context, DatabaseReference database) {
        this.context = context.getApplicationContext();
        this.database = database;
    }

    /**
     * get invalid user variable
     **/
    public boolean getINVALID_USER() {
        return this.INVALID_USER;
    }

    /**
     * set if user is valid or not
     **/
    public void setINVALID_USER(boolean INVALID_USER) {
        this.INVALID_USER = INVALID_USER;
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
