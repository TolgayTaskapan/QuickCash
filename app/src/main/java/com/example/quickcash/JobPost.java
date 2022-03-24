package com.example.quickcash;

import android.location.Location;

import com.example.quickcash.identity.Employer;
import com.example.quickcash.identity.User;

import java.io.Serializable;


public class JobPost implements Serializable {
    public static final String TAG = "JobPost";

    private String jobTitle;
    private String jobType;
    private String urgency;
    private String location;

    private double hourlyWage;
    private int duration;

    private double latitude;
    private double longitude;

    private String userID;

    public JobPost(){

    }

    public JobPost(String jobTitle, String jobType, double hourlyWage, int duration, double latitude, double longitude) {
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.hourlyWage = hourlyWage;
        this.duration = duration;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public JobPost(String jobTitle, String jobType, double hourlyWage, int duration, String location, double latitude, double longitude, String usrID){
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.hourlyWage = hourlyWage;
        this.duration = duration;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userID = usrID;
    }

    public JobPost(String title, String type, Double wage, Integer duration, Double latitude, Double longitude, String userID) {
        this.jobTitle = title;
        this.jobType = type;
        this.hourlyWage = wage;
        this.duration = duration;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userID = userID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public double getDuration() { return duration; }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
