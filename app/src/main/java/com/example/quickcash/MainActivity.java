package com.example.quickcash;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import com.example.quickcash.account.LoginActivity;
import com.example.quickcash.util.FirebaseUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.quickcash.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;


public class MainActivity extends AppCompatActivity {

    public static final FirebaseUtil userFirebase = new FirebaseUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        userFirebase.setUsrID(intent.getStringExtra("userID"));
        userFirebase.setCurrentUserRef(intent.getStringExtra("userRef"));

        com.example.quickcash.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_jobs, R.id.navigation_dashboard, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public void logoutAccount() {
        Intent logoutIntent = new Intent(this, LoginActivity.class);

        userFirebase.getCurrentUserRef().child("logged").setValue(false);
        startActivity(logoutIntent);
        finish();
    }

    public void openPostPage() {
        Intent postIntent = new Intent(this, JobPostingActivity.class);

        startActivity(postIntent);
    }
}