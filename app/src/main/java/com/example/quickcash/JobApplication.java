package com.example.quickcash;

import com.example.quickcash.identity.Employee;
import com.example.quickcash.identity.Employer;

public class JobApplication {
    public static final String TAG = "JobApplication";

    private JobPost jobPost;
    private Employer employer;
    private Employee employee;

    public JobApplication(){

    }

    public JobApplication(JobPost jobPost, Employer employer, Employee employee){
        this.jobPost = jobPost;
        this.employer = employer;
        this.employee = employee;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
