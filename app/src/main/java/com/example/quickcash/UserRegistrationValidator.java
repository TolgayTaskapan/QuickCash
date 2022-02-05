package com.example.quickcash;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class UserRegistrationValidator {

    private final Context context;
    public boolean userExists = false;
    private String errorMsg = "";
    DatabaseReference database;

    public UserRegistrationValidator(Context context, DatabaseReference database){
        this.context = context.getApplicationContext();
        this.database = database;
    }

    /** get error message **/
    public String getErrorMsg(){
        return this.errorMsg;
    }

    /** set if user exists **/
    public void setUserExists(boolean userExists) {
        this.userExists = userExists;
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

        if (i >= 3 ){
            return true;
        } else {
            return false;
        }

    }

    /**check if user account already exists **/
    public boolean checkIfAccountExists(String userName) {
        database.orderByChild("username").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    setUserExists(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error is connecting to database");
            }

        });
        return userExists;
    }

    public boolean validateUserDetails(String username, String password) {

        boolean validUser = false;

        if (isEmptyUsername(username) || isEmptyPassword(password)) {
            errorMsg = this.context.getResources().getString(R.string.EMPTY_USERNAME_OR_PASSWORD).trim();
        } else if (!isValidUsername(username)) {
            errorMsg = this.context.getResources().getString(R.string.INVALID_USERNAME).trim();
        } else if (!isValidPassword(password)) {
            errorMsg = this.context.getResources().getString(R.string.INVALID_PASSWORD).trim();
        } else if ( checkIfAccountExists(username)) {
            errorMsg = this.context.getResources().getString(R.string.USER_ALREADY_EXISTS).trim();
        } else {
            validUser = true;
            errorMsg = context.getResources().getString(R.string.EMPTY_STRING).trim();
        }

        return validUser;
    }
}
