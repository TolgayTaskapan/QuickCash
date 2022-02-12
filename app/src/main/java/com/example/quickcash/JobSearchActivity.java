package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class JobSearchActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search_page);

        initializeAuthentication();

        FloatingActionButton logoutButton = findViewById(R.id.logoutButton);
        FloatingActionButton addButton = findViewById(R.id.addButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               jumpToJobPostingPage();
            }
        });
    }

    private void initializeAuthentication(){
        auth = FirebaseAuth.getInstance();
    }

    private void logout(){
        auth.signOut();
        SharedPresferenceUtil.setLoginStatus(JobSearchActivity.this, false);
        finish();
        System.exit(0);
    }

    protected void jumpToJobPostingPage() {
        Intent intent = new Intent();
        intent.setClass(JobSearchActivity.this, JobPostingActivity.class);
        startActivity(intent);
    }
}