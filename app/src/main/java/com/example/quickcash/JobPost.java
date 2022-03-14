package com.example.quickcash;

public class JobPost {

    private String jobTitle;
    private String jobType;
    private String urgency;

    private double hourlyWage;
    private int duration;

    private double latitude;
    private double longitude;

    private String userID;

    public JobPost(String jobTitle, String jobType, double hourlyWage, int duration, double latitude, double longitude) {
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.hourlyWage = hourlyWage;
        this.duration = duration;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public JobPost(String jobTitle, String jobType, double hourlyWage, int duration, double latitude, double longitude, String usrID){
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.hourlyWage = hourlyWage;
        this.duration = duration;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userID = usrID;
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
