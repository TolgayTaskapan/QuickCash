package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class JobSearchActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search_page);

        initializeAuthentication();

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
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

}