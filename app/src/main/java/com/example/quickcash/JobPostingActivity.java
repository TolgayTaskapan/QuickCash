package com.example.quickcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                String salary = getSalaryRange();
                String startDate = getStartDate();
                boolean partTime = isPartTime();
                boolean fullTime = isFullTime();
                String location = getLocation();
                String duration = getDuration();
                String description = getJobDescription();


                boolean validJob = jobPostingValidator.validateJobDetails(title, salary, startDate, location, duration, description);
                String error = jobPostingValidator.getErrorMsg();

                setStatusMessage(error);

                database.orderByChild("jobname").equalTo(title).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            setStatusMessage(context.getResources().getString(R.string.JOB_NAME_REPEATED).trim());
                        } else{
                            if (validJob) saveJob(title, salary, startDate, fullTime, partTime, location, duration, description);
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

    public String getSalaryRange() {
        EditText salaryRangedET = findViewById(R.id.salaryRange);
        return salaryRangedET.getText().toString().trim();
    }

    public String getStartDate() {
        EditText startDateET = findViewById(R.id.startDate);
        return startDateET.getText().toString().trim();
    }

    public boolean isFullTime(){
        CheckBox fulltime = findViewById(R.id.fullTime);
        return fulltime.isChecked();
    }

    public boolean isPartTime(){
        CheckBox parttime = findViewById(R.id.partTime);
        return parttime.isChecked();
    }

    public String getLocation() {
        EditText locationET = findViewById(R.id.location);
        return locationET.getText().toString().trim();
    }

    public String getDuration() {
        EditText durationET = findViewById(R.id.duration);
        return durationET.getText().toString().trim();
    }

    public String getJobDescription() {
        EditText jobDescriptionET = findViewById(R.id.jobDescription);
        return jobDescriptionET.getText().toString().trim();
    }
    public void initializeDatabase(){
        database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child("job");
    }


    public void saveJob(String title, String salary, String startDate, boolean fulltime, boolean parttime, String location, String duration, String description){
        final DatabaseReference job = database.push();
        job.child("title").setValue(title);
        job.child("salary").setValue(salary);
        job.child("startDate").setValue(startDate);
        job.child("fulltime").setValue(fulltime);
        job.child("parttime").setValue(parttime);
        job.child("location").setValue(location);
        job.child("duration").setValue(duration);
        job.child("description").setValue(description);

    }

    public void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }

}