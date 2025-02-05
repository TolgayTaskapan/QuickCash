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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JobApplication implements Serializable {
    public static final String TAG = "JobApplication";

    private String jobPostKey;
    private String employerKey;
    private String employeeKey;

    private int employeeRating;
    private int employerRating;

    public JobApplication(){
        this.jobPostKey = "none";
        this.employerKey = "none";
        this.employeeKey = "none";

    }

    public JobApplication(String jobPostKey, String employerKey, String employeeKey){
        this.jobPostKey = jobPostKey;
        this.employerKey = employerKey;
        this.employeeKey = employeeKey;
    }

    public int getEmployeeRating() {
        return employeeRating;
    }

    public void setEmployeeRating(int employeeRating) {
        this.employeeRating = employeeRating;
    }

    public int getEmployerRating() {
        return employerRating;
    }

    public void setEmployerRating(int employerRating) {
        this.employerRating = employerRating;
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

    public void employeeMarkComplete(){
        DatabaseReference database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child(UserSession.JOB_COLLECTION);
        DatabaseReference jobPostRef = database.child(jobPostKey);

        //update state to pending
        Map<String, Object> jobStateUpdate = new HashMap<>();
        jobStateUpdate.put("jobState", JobPost.JOB_COMPLETE);
        jobPostRef.updateChildren(jobStateUpdate);

    }
}
