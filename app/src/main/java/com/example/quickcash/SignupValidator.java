package com.example.quickcash;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;


public class SignupValidator {

    private final Context context;
    private String errorMsg = "";
    private DatabaseReference database;

    public SignupValidator(Context context, DatabaseReference database){
        this.context = context.getApplicationContext();
        this.database = database;
    }

    /** get error message **/
    public String getErrorMsg(){
        return this.errorMsg;
    }

    /** check if the user has entered a username **/
    public static boolean isEmptyUsername(String username){
        return username.isEmpty();
    }

    /** check if the user has entered a password **/
    public static boolean isEmptyPassword(String password){
        return password.isEmpty();
    }

    /** check if the user has entered a valid username
     *  the format is 6 to 16 bits in length
     *  and cannot contain special characters **/
    public static boolean isValidUsername(String username){
        if(username.length() < 6 || username.length() > 16){
            return false;
        }
        // regular expressions
        String symbol = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";
        return !username.matches(symbol);
    }

    /** check if the user has entered a valid password
     *  the format is 8 to 16 bits in length
     *  and must contain the following three types of characters:
     *  uppercase, lowercase, number, symbol **/
    public static boolean isValidPassword(String password){
        if(password.length() > 16 || password.length() < 8){
            return false;
        }
        // regular expressions
        String number = ".*\\d+.*";
        String uppercase = ".*[A-Z]+.*";
        String lowercase = ".*[a-z]+.*";
        String symbol = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";

        int i = 0;
        if (password.matches(number)) i++;
        if (password.matches(uppercase))i++;
        if (password.matches(lowercase)) i++;
        if (password.matches(symbol)) i++;

        return i >= 3;
    }

    public boolean validateUserDetails(String username, String password) {

        boolean validUser = false;

        if (isEmptyUsername(username) || isEmptyPassword(password)) {
            errorMsg = this.context.getResources().getString(R.string.EMPTY_USERNAME_OR_PASSWORD).trim();
        } else if (!isValidUsername(username)) {
            errorMsg = this.context.getResources().getString(R.string.INVALID_USERNAME).trim();
        } else if (!isValidPassword(password)) {
            errorMsg = this.context.getResources().getString(R.string.INVALID_PASSWORD).trim();
        } else {
            validUser = true;
            errorMsg = context.getResources().getString(R.string.EMPTY_STRING).trim();
        }

        return validUser;
    }
}
