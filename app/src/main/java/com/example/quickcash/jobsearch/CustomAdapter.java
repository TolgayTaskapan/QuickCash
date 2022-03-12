/* package com.example.quickcash.jobsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quickcash.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> duration;
    ArrayList<Double> hourlyWage;
    ArrayList<String> jobTitle;
    ArrayList<String> jobType;
    ArrayList<Double> distance;
    ArrayList<String> userID;

    LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, ArrayList<Integer> duration, ArrayList<Double> hourlyWage,
                         ArrayList<String> jobTitle, ArrayList<String> jobType, ArrayList<Double> distance) {
        this.context = context;
        this.duration = duration;
        this.hourlyWage = hourlyWage;
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.distance = distance;
        //this.userID = userID;

        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public  int getCount() {
        return jobTitle.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_job_search_results, null);
        TextView jobTitle = (TextView) view.findViewById(R.id.te)
    }
} */