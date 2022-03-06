package com.example.quickcash;

import android.location.Location;

import com.example.quickcash.identity.Employer;
import com.example.quickcash.identity.User;

import java.sql.Time;
import java.util.Date;

public class JobPost {

    private String jobTitle;
    private String jobType;
    private String urgency;

    private double hourlyWage;
    private int duration;

    private double latitude;
    private double longitude;

    private String employer_id;

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

    public JobPost(String jobTitle, String jobType, double hourlyWage, int duration, double latitude, double longitude, String employer_id) {
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.hourlyWage = hourlyWage;
        this.duration = duration;
        this.latitude = latitude;
        this.longitude = longitude;
        this.employer_id = employer_id;
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

    public double getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEmployer_id() {
        return employer_id;
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

    public void setEmployer_id(String employer_id) {
        this.employer_id = employer_id;
    }

}
