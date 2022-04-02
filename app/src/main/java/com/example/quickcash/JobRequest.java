package com.example.quickcash;

import java.io.Serializable;

public class JobRequest implements Serializable {

    public static final String TAG = "JobRequest";

    private String jobId;
    private String employerId;
    private String employeeId;
    private String jobTitle;
    private String jobType;
    private double hourlyWage;
    private int status;

    public JobRequest(String jobId, String employerId, String employeeId, String jobTitle, String jobType, double hourlyWage, int status) {
        this.jobId = jobId;
        this.employerId = employerId;
        this.employeeId = employeeId;
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.hourlyWage = hourlyWage;
        this.status = status;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobType() {
        return jobType;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getJobId() {
        return jobId;
    }

    public String getEmployerId() {
        return employerId;
    }

    public int getStatus() {
        return status;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
















