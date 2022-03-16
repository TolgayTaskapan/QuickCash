package com.example.quickcash.jobsearch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;

import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class JobSearchResultsActivity extends AppCompatActivity {

    private String job_type;
    private Double min_wage;
    private Double user_distancePref;
    private Integer min_job_length;
    private Integer max_job_length;
    private Boolean jobCheck;

    Context mContext;
    ListView searchView;
    double user_latitude;
    double user_longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search_results);

        searchView = findViewById(R.id.search_view);

        mContext = this;

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(JobSearchResultsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            //Toast.makeText(mContext,"You need have granted permission",Toast.LENGTH_SHORT).show();
            GPSTracker gps = new GPSTracker(mContext, JobSearchResultsActivity.this);

            // Check if GPS enabled
            if (gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                user_longitude = longitude;
                user_latitude = latitude;
                // \n is for new line
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            } else {
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }
        }


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            job_type = extras.getString("job_type_key");
            min_wage = extras.getDouble("hourly_wages_key");
            user_distancePref = extras.getDouble("distance_key");
            min_job_length = extras.getInt("min_job_length");
            max_job_length = extras.getInt("max_job_length_key");
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
                ArrayList<Float> jobDistance = new ArrayList<>();

                Location userLocation = new Location("User");
                userLocation.setLatitude(user_latitude);
                userLocation.setLongitude(user_longitude);
                Location jobLocation = new Location("location");
                ArrayList<String> userID = new ArrayList<String>();

                for(DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.child("duration").getValue(Integer.class) >  min_job_length &  ds.child("duration").getValue(Integer.class) <= max_job_length) {
                        if (min_wage <= ds.child("hourlyWage").getValue(Double.class)) {
                            if (job_type.equals(ds.child("jobType").getValue(String.class))) {
                                jobLocation.setLongitude(ds.child("longitude").getValue(Double.class));
                                jobLocation.setLatitude(ds.child("latitude").getValue(Double.class));
                                Toast.makeText(getApplicationContext(),"Distance" + String.valueOf(userLocation.distanceTo(jobLocation)),Toast.LENGTH_LONG).show();
                                if (userLocation.distanceTo(jobLocation) <= user_distancePref) {
                                    Toast.makeText(getApplicationContext(),String.valueOf(userLocation.distanceTo(jobLocation)),Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(),ds.child("jobTitle").getValue(String.class),Toast.LENGTH_LONG).show();
                                    jobDistance.add(userLocation.distanceTo(jobLocation));
                                    jobType.add(ds.child("jobType").getValue(String.class));
                                    hourlyWage.add(ds.child("hourlyWage").getValue(Double.class));
                                    duration.add(ds.child("duration").getValue(Integer.class));
                                    jobTitle.add(ds.child("jobTitle").getValue(String.class));
                                    userID.add(ds.child("userID").getValue(String.class));
                                }
                            }
                        }
                    }
                }
                createJobPostView(jobType,jobTitle,hourlyWage,jobDistance,duration);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    GPSTracker gps = new GPSTracker(mContext, JobSearchResultsActivity.this);

                    // Check if GPS enabled
                    if (gps.canGetLocation()) {

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    } else {
                        // Ask user to enable GPS/network in settings.
                        gps.showSettingsAlert();
                    }

                } else {
                    //Toast.makeText(mContext, "You need to grant permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void createJobPostView(ArrayList<String> jobTitle,ArrayList<String> jobType,ArrayList<Double> hourlyWage,ArrayList<Float> jobDistance, ArrayList<Integer> duration){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for(int i = 0; i < jobTitle.size(); i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put("job_title_hash", jobTitle.get(i));
            item.put("job_type_hash", jobType.get(i));
            String hourlyWageString = hourlyWage.get(i).toString();
            item.put("hourly_wage_hash", hourlyWageString);
            String jobDistanceString = jobDistance.get(i).toString();
            item.put("job_distance_hash", jobDistanceString);
            String jobDurationString = duration.get(i).toString();
            item.put("job_duration_hash", jobDurationString);
            list.add(item);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), list, R.layout.job_search_listview,
                new String[]{"job_title_hash", "job_type_hash", "hourly_wage_hash", "job_distance_hash"
                        , "job_duration_hash"}, new int[] {R.id.job_title, R.id.job_type, R.id.hourly_wage, R.id.job_distance
                , R.id.job_duration});
        searchView.setAdapter(simpleAdapter);
    }



}
