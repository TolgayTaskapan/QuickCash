package com.example.quickcash.jobsearch;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JobSearchResultsActivity extends AppCompatActivity {

    private String job_type;
    private String hourly_wages;
    private String distance;
    private String job_length;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search_results);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            job_type = extras.getString("job_type_key");
            hourly_wages = extras.getString("hourly_wages_key");
            distance = extras.getString("distance_key");
            job_length =extras.getString("job_length_key");
        }
        getJobs();
    }

    public void getJobs(){

        DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("job");
        jobRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Integer> duration = new ArrayList<Integer>();
                ArrayList<Double> hourlyWage = new ArrayList<Double>();
                ArrayList<String> jobTitle = new ArrayList<String>();
                ArrayList<String> jobType = new ArrayList<String>();
                ArrayList<Double> latitude = new ArrayList<Double>();
                ArrayList<Double> longitude = new ArrayList<Double>();
                ArrayList<String> userID = new ArrayList<String>();

                for(DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.child("duration").getValue(Integer.class) > job_MinLength &  ds.child("duration").getValue(Integer.class) <= job_MaxLength) {
                        duration.add(ds.child("duration").getValue(Integer.class));
                        jobTitle.add(ds.child("jobTitle").getValue(String.class));
                        latitude.add(ds.child("latitude").getValue(Double.class));
                        longitude.add(ds.child("longitude").getValue(Double.class));
                        userID.add(ds.child("userID").getValue(String.class));
                    }
                    if(hourly_wages <= ds.child("hourlyWage").getValue(Double.class)){
                        hourlyWage.add(ds.child("hourlyWage").getValue(Double.class));
                    }
                    if(job_type == ds.child("jobType").getValue(String.class)){
                        jobType.add(ds.child("jobType").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }












}
