package com.example.quickcash;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quickcash.identity.Employee;
import com.example.quickcash.identity.Employer;
import com.example.quickcash.util.UserSession;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JobApplication {
    public static final String TAG = "JobApplication";

    private String jobPostKey;
    private String employerKey;
    private String employeeKey;

    private Employee employee;
    private Employer employer;

    public JobApplication(){

    }

    public JobApplication(String jobPostKey, String employerKey, String employeeKey){
        this.jobPostKey = jobPostKey;
        this.employerKey = employerKey;
        this.employeeKey = employeeKey;

    }


    public String getJobPostKey() {
        return jobPostKey;
    }

    public void setJobPostKey(String jobPostKey) {
        this.jobPostKey = jobPostKey;
    }

    public String getEmployerKey() {
        return employerKey;
    }

    public void setEmployerKey(String employerKey) {
        this.employerKey = employerKey;
    }

    public String getEmployeeKey() {
        return employeeKey;
    }

    public void setEmployeeKey(String employeeKey) {
        this.employeeKey = employeeKey;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public void applyEmployeeForJob(){
        DatabaseReference database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child(UserSession.JOB_COLLECTION);
        DatabaseReference jobPostRef = database.child(jobPostKey);

        //update state to pending
        Map<String, Object> jobStateUpdate = new HashMap<>();
        jobStateUpdate.put("jobState", JobPost.JOB_PENDING);
        jobPostRef.updateChildren(jobStateUpdate);

    }

    public void employerApproveEmployee(){
        DatabaseReference database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child(UserSession.JOB_COLLECTION);
        DatabaseReference jobPostRef = database.child(jobPostKey);

        //update state to pending
        Map<String, Object> jobStateUpdate = new HashMap<>();
        jobStateUpdate.put("jobState", JobPost.JOB_IN_PROGRESS);
        jobPostRef.updateChildren(jobStateUpdate);

    }
}
