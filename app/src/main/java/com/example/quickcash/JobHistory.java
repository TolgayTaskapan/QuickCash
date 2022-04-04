package com.example.quickcash;

import android.provider.ContactsContract;

import com.example.quickcash.identity.User;
import com.example.quickcash.util.UserSession;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobHistory {

    private ArrayList<JobPost> completedJobs;
    private ArrayList<Integer> ratings;
    private double totalAmountEarned;

    public JobHistory(){
        this.completedJobs = new ArrayList<>();
        this.ratings = new ArrayList<>();
        this.totalAmountEarned = 0.00;
    }

    public ArrayList<JobPost> getCompletedJobs(){
        return this.completedJobs;
    }

    public ArrayList<Integer> getRatings(){
        return this.ratings;
    }

    public int getNumberOfJobs(){
        return this.completedJobs.size();
    }

    public int getNumberOfRatings(){
        return this.ratings.size();
    }

    public double getTotalAmountEarned(){
        return totalAmountEarned;
    }

    public void addRate(Integer rate){
        ratings.add(rate);
    }

    public void addCompletedJob(JobPost jobPost){
        completedJobs.add(jobPost);
    }

    public void increaseTotalAmount(double value){
        this.totalAmountEarned += value;
    }


    public double calculateRating(){
        double sum = 0;

        for (int i = 0; i < ratings.size(); i++){
            sum += ratings.get(i);
        }
        return sum/ratings.size();
    }

    /*Updates this user's database job history values whenever called */
    public void updateDBHistory(){
        User user = UserSession.getInstance().getUser();
        DatabaseReference userRef = UserSession.getInstance().getCurrentUserRef();
        DatabaseReference userJobHistory = userRef.child("jobHistory");
        DatabaseReference userCompletedJobs = userJobHistory.child("completedJobs");
        DatabaseReference userRatings = userJobHistory.child("ratings");

        //update jobHistory arrays
        userCompletedJobs.setValue(user.jobHistory.getCompletedJobs());
        userRatings.setValue(user.jobHistory.getRatings());

        //update user in db
        Map<String, Object> userHistoryUpdate = new HashMap<>();
        userHistoryUpdate.put("jobHistory", user.jobHistory);
        userHistoryUpdate.put("rating", user.getRating());
        userRef.updateChildren(userHistoryUpdate);
    }




}