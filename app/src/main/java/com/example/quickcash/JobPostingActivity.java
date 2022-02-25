package com.example.quickcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        Button PostBTN = findViewById(R.id.PostButton);
        PostBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jobname = getJobName();
                String description = getDescription();

                boolean validJob = jobPostingValidator.validateJobDetails(jobname, description);
                String error = jobPostingValidator.getErrorMsg();

                setStatusMessage(error);

                database.orderByChild("jobname").equalTo(jobname).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            setStatusMessage(context.getResources().getString(R.string.JOB_NAME_REPEATED).trim());
                        } else{
                            if (validJob) saveJobs(jobname, description);
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

    public String getJobName() {
        EditText jobNameET = findViewById(R.id.jobName);
        return jobNameET.getText().toString().trim();
    }

    public String getDescription() {
        EditText descriptionET = findViewById(R.id.description);
        return descriptionET.getText().toString().trim();
    }

    public void initializeDatabase(){
        database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child("job");
    }

    public void saveJobs(String jobName, String description){
        final DatabaseReference account = database.push();
        account.child("jobname").setValue(jobName);
        account.child("description").setValue(description);
    }

    public void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }

}