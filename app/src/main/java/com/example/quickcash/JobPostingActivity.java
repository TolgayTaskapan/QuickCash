package com.example.quickcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting);
        initializeDatabase();

        context = this.getApplicationContext();
        jobPostingValidator = new JobPostingValidator(context, database);

        Button PostBTN = findViewById(R.id.postJobButton);
        PostBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = getJobTitle();
                String type = getJobType();
                double wage = getHourlyWage();
                try {
                    Date startDate = getStartDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String location = getLocation();

                try {
                    Long duration = getDuration();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String employer = getEmployer();
                try {
                    Date start_date = getStartDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boolean validJob = jobPostingValidator.validateJobDetails(title, type);
                String error = jobPostingValidator.getErrorMsg();

                setStatusMessage(error);

                database.orderByChild("jobname").equalTo(title).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            setStatusMessage(context.getResources().getString(R.string.JOB_NAME_REPEATED).trim());
                        } else{
                            if (validJob)
                                saveJob(title, wage, type, employer, location,duration , getUrgency());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Error is connecting to database");
                    }

                });
            }
        });
    }


    public String getJobTitle() {
        EditText jobTitleET = findViewById(R.id.jobTitle);
        return jobTitleET.getText().toString().trim();
    }

    public String getJobType() {
        EditText jobTypeET = findViewById(R.id.jobType2);
        return jobTypeET.getText().toString().trim();
    }

    public double getHourlyWage() {
        EditText hourlyWage = findViewById(R.id.hourlyWage2);
        return Double.parseDouble(hourlyWage.getText().toString().trim());
    }

    public int getUrgency() {
        EditText urgency = findViewById(R.id.urgency);
        return Integer.parseInt(urgency.getText().toString().trim());
    }

    public Date getStartDate() throws ParseException {
        EditText startDateET =  findViewById(R.id.startDate2);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return df.parse(startDateET.getText().toString().trim());
    }

    public String getEmployer() {
        EditText employerET = findViewById(R.id.employer2);
        return employerET.getText().toString().trim();
    }

    public String getLocation() {
        EditText locationET = findViewById(R.id.location2);
        return locationET.getText().toString().trim();
    }

    public Long getDuration() throws ParseException {
        EditText durationET = findViewById(R.id.duration);
        DateFormat df = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
        return df.parse(durationET.getText().toString().trim()).getTime();
    }

    public void initializeDatabase(){
        database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child("job");
    }


    public void saveJob(String title, double hourlyWage, String jobType, User employer, Date startDate, Location location, String duration,  int urgency){
        final DatabaseReference job = database.push();
        job.child("title").setValue(title);
        job.child("jobType").setValue(jobType);
        job.child("startDate").setValue(startDate);
        job.child("location").setValue(location);
        job.child("duration").setValue(duration);
        job.child("wage").setValue(hourlyWage);
        job.child("urgency").setValue(urgency);
        job.child("employer").setValue(employer);

    }

    public void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }

}