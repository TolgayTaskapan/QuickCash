package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quickcash.account.LoginActivity;
import com.example.quickcash.identity.Employee;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUpdateJobPostActivity extends AppCompatActivity {

    public static final String TAG = "AddUpdateJobPostActivity";
    public static final String UPDATE_JOB_KEY = "UPDATE_JOB_KEY";
    public static final String UPDATE_JOB = "UPDATE_JOB";

    private EditText titleET, locationET, wageET, durationET, urgencyET;
    private Spinner job_type_spinner;
    private Button addBTN, updateBTN;
    private JobPost jobPost;
    private String jobKey;

    private JobPostingValidator jobPostingValidator;
    DatabaseReference database;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_jobpost);
        initializeDatabase();
        init();
        setActivityView();

        context = this.getApplicationContext();
        jobPostingValidator = new JobPostingValidator(context, database);

        //fill spinners with values
        job_type_spinner = findViewById(R.id.job_type_list);

        ArrayAdapter<CharSequence> job_type_adapter = ArrayAdapter.createFromResource(this, R.array.job_type_entries,
                android.R.layout.simple_spinner_item);

        job_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        job_type_spinner.setAdapter(job_type_adapter);

        Button addJobButton = findViewById(R.id.addJobButton);
        addJobButton.setOnClickListener(new View.OnClickListener() {
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

                if (!error.equals("")) {
                    displayToast(error);
                } else {
                    displayToast(context.getResources().getString(R.string.JOB_POST_SUCCESS).trim());
                }

                if (validJob) {
                    wage = convertWageToDouble(strWage);
                    saveJob(title, wage, type, duration, urgency, location, latitude, longitude);
                }
            }

        });

    }

    private void init() {
        titleET = findViewById(R.id.jobTitle);
        job_type_spinner = findViewById(R.id.job_type_list);
        locationET = findViewById(R.id.location);
        wageET = findViewById(R.id.hourlyWage);
        durationET = findViewById(R.id.duration);
        urgencyET = findViewById(R.id.urgency);

        addBTN = findViewById(R.id.addJobButton);
        updateBTN = findViewById(R.id.updateJobButton);
    }

    private void setActivityView() {
        final Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString(TAG) != null) {
            final String activityPath = extras.getString(TAG);
            if (activityPath.equals(UPDATE_JOB)) {
                addBTN.setVisibility(View.GONE);
                updateBTN.setVisibility(View.VISIBLE);
                jobPost = (JobPost) getIntent().getSerializableExtra(JobPost.TAG);
                jobKey = extras.getString(UPDATE_JOB_KEY);
                titleET.setText(jobPost.getJobTitle());
                String compareValue = jobPost.getJobType();
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.job_type_entries, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                job_type_spinner.setAdapter(adapter);
                if (compareValue != null) {
                    int spinnerPosition = adapter.getPosition(compareValue);
                    job_type_spinner.setSelection(spinnerPosition);
                }
                locationET.setText(jobPost.getLocation());
                wageET.setText(String.valueOf(jobPost.getHourlyWage()));
                durationET.setText(String.valueOf(jobPost.getDuration()));
                urgencyET.setText(jobPost.getUrgency());
            } else {
                updateBTN.setVisibility(View.GONE);
                addBTN.setVisibility(View.VISIBLE);
            }
        } else {
            updateBTN.setVisibility(View.GONE);
            addBTN.setVisibility(View.VISIBLE);
        }
    }


    public String getJobTitle() {
        titleET = findViewById(R.id.jobTitle);
        return titleET.getText().toString().trim();
    }

    public String getJobType(){
        return job_type_spinner.getSelectedItem().toString();
    }

    public String getHourlyWage() {
        wageET = findViewById(R.id.hourlyWage);
        return wageET.getText().toString().trim();
    }

    public double convertWageToDouble(String wage) {
        return Double.parseDouble(wage);
    }

    public String getUrgency() {
        urgencyET = findViewById(R.id.urgency);
        return urgencyET.getText().toString().trim();
    }


    public String getLocation() {
        locationET = findViewById(R.id.location);
        return locationET.getText().toString().trim();
    }

    public int getDuration() {
        durationET = findViewById(R.id.duration);
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


    public void saveJob(String title,  double hourlyWage, String jobType, int duration,  String urgency, String location, double latitude, double longitude){
        final DatabaseReference job = database.push();

        JobPost jobPost = new JobPost(title, jobType, hourlyWage, duration, location,  latitude, longitude, MainActivity.userFirebase.getUsrID());
        job.setValue(jobPost);
        jumpToJobDashboard();
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    protected void jumpToJobDashboard() {
        Intent intent = new Intent();
        intent.setClass(AddUpdateJobPostActivity.this, MainActivity.class);

        startActivity(intent);
        finish();
    }

}