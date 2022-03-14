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

    public static boolean isEmptyJobname(String jobName){
        return jobName.isEmpty();
    }

    public static boolean isEmptyJobtype(String jobType){
        return jobType.isEmpty();
    }

    public static boolean isEmptyWage(String wage){
        return wage.isEmpty();
    }

    public static boolean isEmptyLocation(String location){
        return location.isEmpty();
    }

    public static boolean isValidJobname(String jobName){
        if(jobName.length() > 20){
            return false;
        }
        // regular expressions
        String symbol = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";
        return !jobName.matches(symbol);
    }

    public static boolean isValidWage(double wage){
        return wage > 0.00;
    }

    public static boolean isValidCoordinates(double latitude, double longitude){
        return latitude != 0.00 || longitude != 0.00;
    }

    public boolean validateJobDetails(String title, String type, String wage, String location, double latitude, double longitude){

        double dWage;
        errorMsg = context.getResources().getString(R.string.EMPTY_STRING).trim();
        if (isEmptyJobname(title) || isEmptyJobtype(type)) {
            errorMsg = this.context.getResources().getString(R.string.EMPTY_JOBNAME_OR_DESCRIPTION).trim();
            return false;
        } else if (isEmptyWage(wage)){
            errorMsg = this.context.getResources().getString(R.string.EMPTY_WAGE).trim();
            return false;
        } else if (isEmptyLocation(location)){
            errorMsg = this.context.getResources().getString(R.string.EMPTY_LOCATION).trim();
            return false;
        } else if (!isValidJobname(title)) {
            errorMsg = this.context.getResources().getString(R.string.INVALID_JOBNAME).trim();
            return false;
        } else {
            dWage = Double.parseDouble(wage);
        }

        if (!isValidWage(dWage)) {
            errorMsg = this.context.getResources().getString(R.string.INVALID_WAGE).trim();
            return false;
        } else if (!isValidCoordinates(latitude, longitude)){
            errorMsg = this.context.getResources().getString(R.string.INVALID_LOCATION).trim();
            return false;
        } else {
            return true;
        }

    }
}
