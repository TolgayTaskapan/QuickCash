package com.example.quickcash;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

public class JobPostingValidator {

    private final Context context;
    private String errorMsg = "";
    DatabaseReference database;

    public JobPostingValidator(Context context, DatabaseReference database){
        this.context = context.getApplicationContext();
        this.database = database;
    }

    public String getErrorMsg(){
        return this.errorMsg;
    }

    public static boolean isEmptyJobname(String Jobname){
        return Jobname.isEmpty();
    }

    public static boolean isEmptyDescription(String description){
        return description.isEmpty();
    }

    public static boolean isValidJobname(String Jobname){
        if(Jobname.length() > 20){
            return false;
        }
        // regular expressions
        String symbol = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";
        return !Jobname.matches(symbol);
    }

    public static boolean isValidDescription(String description){
        return description.length() <= 50;
    }

    public boolean validateJobDetails(String Jobname, String description) {

        boolean validJob = false;
        if (isEmptyJobname(Jobname) || isEmptyDescription(description)) {
            errorMsg = this.context.getResources().getString(R.string.EMPTY_JOBNAME_OR_DESCRIPTION).trim();
        } else if (!isValidJobname(Jobname)) {
            errorMsg = this.context.getResources().getString(R.string.INVALID_JOBNAME).trim();
        } else if (!isValidDescription(description)) {
            errorMsg = this.context.getResources().getString(R.string.INVALID_DESCRIPTION).trim();
        } else {
            validJob = true;
            errorMsg = context.getResources().getString(R.string.EMPTY_STRING).trim();
        }

        return validJob;
    }
}
