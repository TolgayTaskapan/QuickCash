package com.example.quickcash;

import java.util.ArrayList;

public class JobHistory {

    private ArrayList<JobPost> completedJobs;
    private ArrayList<Integer> ratings;
    double totalAmountEarned;

    public JobHistory(){
        this.completedJobs = new ArrayList<>();
        this.ratings = new ArrayList<>();
        this.totalAmountEarned = 0.00;
    }

    public double getTotalAmountEarned(){
        return totalAmountEarned;
    }

    public void increaseTotalAmount(double value){
        this.totalAmountEarned += value;
    }




}
