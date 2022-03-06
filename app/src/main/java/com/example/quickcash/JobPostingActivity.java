package com.example.quickcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickcash.identity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JobPostingActivity extends AppCompatActivity {

    private JobPostingValidator jobPostingValidator;
    DatabaseReference database;
    Context context;

    private Spinner job_type_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting);
        initializeDatabase();

        context = this.getApplicationContext();
        jobPostingValidator = new JobPostingValidator(context, database);

        //fill spinners with values
        job_type_spinner = findViewById(R.id.job_type_list);

        ArrayAdapter<CharSequence> job_type_adapter = ArrayAdapter.createFromResource(this, R.array.job_type_entries,
                android.R.layout.simple_spinner_item);

        job_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        job_type_spinner.setAdapter(job_type_adapter);

        Button PostBTN = findViewById(R.id.button);
        PostBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                displayToast(error);

                if (validJob) {
                    wage = convertWageToDouble(strWage);
                    saveJob(title, wage, type, duration, urgency, latitude, longitude);
                }
            }

        });

    }


    public String getJobTitle() {
        EditText jobTitleET = findViewById(R.id.jobTitle);
        return jobTitleET.getText().toString().trim();
    }

    public String getJobType(){
        return job_type_spinner.getSelectedItem().toString();
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

        String results = latitude + " " + longitude;
        displayToast(results);
        Log.i("results:", results);
//        job.child("title").setValue(title);
//        job.child("jobType").setValue(jobType);
//        job.child("duration").setValue(duration);
//        job.child("wage").setValue(hourlyWage);
//        job.child("urgency").setValue(urgency);
//        job.child("latitude").setValue(latitude);
//        job.child("longitude").setValue(longitude);

    }

//    public void setStatusMessage(String message) {
//        TextView statusLabel = findViewById(R.id.statusLabel);
//        statusLabel.setText(message.trim());
//    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}