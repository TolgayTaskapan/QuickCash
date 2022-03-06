package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class JobPostDashboardActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    DatabaseReference dbUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_dashboard);

//        //accessing user database reference from string passed from login
//        Intent intent = getIntent();
//        String userRefURL = intent.getStringExtra("userRef");
//        dbUserRef = FirebaseDatabase.getInstance().getReferenceFromUrl(userRefURL);

        //FloatingActionButton logoutButton = findViewById(R.id.logoutButton);
        FloatingActionButton addButton = findViewById(R.id.addButton);

        /*logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(dbUserRef);
            }
        });*/

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               jumpToJobPostingPage();
            }
        });
    }

    private void logout(DatabaseReference dbUser){
        Map<String, Object> userLoginUpdate = new HashMap<>();
        userLoginUpdate.put("logged", "false");
        dbUser.updateChildren(userLoginUpdate);

        finish();
        System.exit(0);
    }

    protected void jumpToJobPostingPage() {
        Intent intent = new Intent();
        intent.setClass(JobPostDashboardActivity.this, JobPostingActivity.class);
        startActivity(intent);
    }
}