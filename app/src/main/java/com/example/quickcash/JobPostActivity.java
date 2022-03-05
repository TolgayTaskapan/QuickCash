package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class JobPostActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseReference job_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job_post);

        initializeDatabase();

        Button postJobButton = findViewById(R.id.postJobButton);
        postJobButton.setOnClickListener(this);
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
        job_database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child("job");
    }

    public void registerJob(String title, String salary, String startDate, String location, String duration, String description){
        final DatabaseReference job = job_database.push();
        job.child("title").setValue(title);
        job.child("salary").setValue(salary);
        job.child("startDate").setValue(startDate);
        job.child("location").setValue(location);
        job.child("duration").setValue(duration);
        job.child("description").setValue(description);

    }

    public void onClick(View view) {
        String title = getJobTitle();
        String salary = getSalaryRange();
        String startDate = getStartDate();
        String location = getLocation();
        String duration = getDuration();
        String description = getJobDescription();

        registerJob(title, salary, startDate, location, duration, description);

    }
}
