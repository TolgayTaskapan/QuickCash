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




}
