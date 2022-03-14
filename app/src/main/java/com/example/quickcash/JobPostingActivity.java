package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JobPostingActivity extends AppCompatActivity {

    DatabaseReference database;
    Context context;

    private Spinner jobTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting);
        initializeDatabase();


        context = this.getApplicationContext();
        JobPostingValidator jobPostingValidator;
        jobPostingValidator = new JobPostingValidator(context, database);

        //fill spinners with values
        jobTypeSpinner = findViewById(R.id.job_type_list);

        ArrayAdapter<CharSequence> jobTypeAdapter = ArrayAdapter.createFromResource(this, R.array.job_type_entries,
                android.R.layout.simple_spinner_item);

        jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        jobTypeSpinner.setAdapter(jobTypeAdapter);

        Button postBTN = findViewById(R.id.button);
        postBTN.setOnClickListener(view -> {
            String title = getJobTitle();
            String type = getJobType();

            String strWage = getHourlyWage();
            double wage = 0.00;

            String urgency = getUrgency();
            int duration = getDuration();

            String location = getLocation();
            double latitude = getLatFromLocation(location);
            double longitude = getLongFromLocation(location);

            boolean validJob = jobPostingValidator.validateJobDetails(title, type, strWage, location, latitude, longitude);
            String error = jobPostingValidator.getErrorMsg();

            if (!error.equals("")) {
                displayToast(error);
            } else {
                displayToast(context.getResources().getString(R.string.JOB_POST_SUCCESS).trim());
            }

            if (validJob) {
                wage = convertWageToDouble(strWage);
                saveJob(title, wage, type, duration, urgency, latitude, longitude);
            }
        });

    }


    public String getJobTitle() {
        EditText jobTitleET = findViewById(R.id.jobTitle);
        return jobTitleET.getText().toString().trim();
    }

    public String getJobType(){
        return jobTypeSpinner.getSelectedItem().toString();
    }

    public String getHourlyWage() {
        EditText hourlyWage = findViewById(R.id.hourlyWage);
        return hourlyWage.getText().toString().trim();
    }

    public double convertWageToDouble(String wage) {
        return Double.parseDouble(wage);
    }

    public String getUrgency() {
        EditText urgency = findViewById(R.id.urgency);
        return urgency.getText().toString().trim();
    }


    public String getLocation() {
        EditText locationET = findViewById(R.id.location);
        return locationET.getText().toString().trim();
    }

    public int getDuration() {
        EditText durationET = findViewById(R.id.duration);
        String durationString = durationET.getText().toString().trim();
        return Integer.parseInt(durationString);
    }

    private double getLatFromLocation(String location){
        GeoCodeLocation locationAddress = new GeoCodeLocation();
         return locationAddress.getLongOrLat(location, getApplicationContext(), "latitude");
    }

    private double getLongFromLocation(String location){
        GeoCodeLocation locationAddress = new GeoCodeLocation();
        return locationAddress.getLongOrLat(location, getApplicationContext(), "longitude");
    }

    public void initializeDatabase(){
        database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child("job");
    }


    public void saveJob(String title, double hourlyWage, String jobType, int duration,  String urgency, double latitude, double longitude){
        final DatabaseReference job = database.push();

        JobPost jobPost = new JobPost(title, jobType, hourlyWage, duration, latitude, longitude, MainActivity.userFirebase.getUsrID());
        job.setValue(jobPost);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}