package com.example.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quickcash.account.LoginActivity;
import com.example.quickcash.account.SignupActivity;

public class LandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(view -> jumpToRegistrationPage());

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> jumpToLoginPage());
    }

    protected void jumpToRegistrationPage() {
        Intent intent = new Intent();
        intent.setClass(LandingPageActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    protected void jumpToLoginPage() {
        Intent intent = new Intent();
        intent.setClass(LandingPageActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
