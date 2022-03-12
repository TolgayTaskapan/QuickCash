package com.example.quickcash.jobsearch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.quickcash.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
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
    private String hourly_wages;
    private String distance;
    private String job_length;

    ArrayList<Integer> duration;
    ArrayList<Double> jobDistance;
    ArrayList<Double> hourlyWage;
    ArrayList<String> jobTitle;
    ArrayList<String> jobType;
    ArrayList<Double> latitude;
    ArrayList<Double> longitude;

    FusedLocationProviderClient fusedLocationProviderClient;
    ListView searchView;
    int PERMISSION_ID = 44;
    double user_latitude;
    double user_longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search_results);

        searchView = findViewById(R.id.search_view);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            job_type = extras.getString("job_type_key");
            hourly_wages = extras.getString("hourly_wages_key");
            distance = extras.getString("distance_key");
            job_length =extras.getString("job_length_key");
        }
        getJobs();

       // CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), duration, hourlyWage,
             //   jobTitle, jobType, jobDistance);

       // searchView.setAdapter(customAdapter);

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

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.activity_job_search_results,
                new String[]{"job_title_hash", "job_type_hash", "hourly_wage_hash", "job_distance_hash"
        , "job_duration_hash"}, new int[] {R.id.job_title, R.id.job_type, R.id.hourly_wage, R.id.job_distance
        , R.id.job_duration});

        searchView.setAdapter(simpleAdapter);

    }

    public void getJobs(){

        DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("job");
        jobRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                duration = new ArrayList<Integer>();
                hourlyWage = new ArrayList<Double>();
                jobTitle = new ArrayList<String>();
                jobType = new ArrayList<String>();
                latitude = new ArrayList<Double>();
                longitude = new ArrayList<Double>();
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

    @SuppressLint("MissingPermission")
    private void getLocation() {
        if(permissionsValid()) {
            if(isLocationServicesEnabled()) {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @RequiresApi(api = Build.VERSION_CODES.S)
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if(location == null) {
                            updateLocationData();
                        }
                        else {
                            user_longitude = location.getLongitude();
                            user_latitude = location.getLatitude();
                        }
                    }
                });
            }
            else {
                Toast.makeText(this, "Turn on location services.", Toast.LENGTH_LONG);
                Intent settings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(settings);
            }
        }
        else {
            getPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.S)
    private void updateLocationData() {
        long intervalMillis = 100000;
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();
            user_latitude = lastLocation.getLatitude();
            user_longitude = lastLocation.getLongitude();
        }
    };

    private boolean permissionsValid() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;
    }

    private void getPermissions() {
        ActivityCompat.requestPermissions(this, new String[] {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean isLocationServicesEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onRequestPermissionsResult(int getCode, @NonNull String[] permissions, @NonNull int[] results) {
        super.onRequestPermissionsResult(getCode, permissions, results);

        if(getCode == PERMISSION_ID) {
            if(results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(permissionsValid()) {
            getLocation();
        }
    }

}
