package com.example.quickcash;

import android.location.Location;

import com.example.quickcash.identity.Employer;
import com.example.quickcash.identity.User;
import com.example.quickcash.util.UserSession;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class JobPost implements Serializable {
    public static final String TAG = "JobPost";
    public static final String JOB_OPEN = "open";
    public static final String JOB_PENDING = "pending";
    public static final String JOB_IN_PROGRESS = "in-progress";
    public static final String JOB_COMPLETE = "complete";

    private DatabaseReference jobRef;

    private String jobTitle;
    private String jobType;
    private String urgency;
    private String location;

    private double hourlyWage;
    private int duration;

    private double latitude;
    private double longitude;

    private String userID;
    private String jobState;
    private User applicant;
    private JobApplication application;

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

    public JobPost(String jobTitle, String jobType, double hourlyWage, int duration, String location, double latitude, double longitude, String usrID, String jobState){
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.hourlyWage = hourlyWage;
        this.duration = duration;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userID = usrID;
        this.jobState = jobState;
        this.applicant = null;
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

    public JobPost(String title, String jobType, double hourlyWage, int duration, String location, double latitude, double longitude, String usrID) {
        this.jobTitle = title;
        this.jobType = jobType;
        this.hourlyWage = hourlyWage;
        this.duration = duration;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userID = usrID;
        this.jobState = JOB_OPEN;
        this.applicant = null;
    }

    public JobPost(String title, String jobType, double hourlyWage, int duration, String location, double latitude, double longitude, String usrID, String jobState, DatabaseReference jobRef) {
        this.jobRef = jobRef;
        this.jobTitle = title;
        this.jobType = jobType;
        this.hourlyWage = hourlyWage;
        this.duration = duration;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userID = usrID;
        this.jobState = jobState;
        this.applicant = null;
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

    public String getJobState(){ return jobState;}

    public void setJobState(String state){this.jobState = state;}

    public DatabaseReference getJobRef() {
        return jobRef;
    }

    public void setJobRef(DatabaseReference jobRef) {
        this.jobRef = jobRef;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    /*Updates this user's database job history values whenever called */
    public void updateDBHistory(){
        User user = UserSession.getInstance().getUser();
        DatabaseReference userRef = UserSession.getInstance().getCurrentUserRef();
        DatabaseReference userJobHistory = userRef.child("jobHistory");
        DatabaseReference userCompletedJobs = userJobHistory.child("completedJobs");
        DatabaseReference userRatings = userJobHistory.child("ratings");

        //update jobHistory arrays

        //update user in db
        Map<String, Object> jobStateUpdate = new HashMap<>();
        jobStateUpdate.put("jobState", this.jobState);
        userRef.updateChildren(jobStateUpdate);
    }
}
