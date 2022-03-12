package com.example.quickcash;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.quickcash.account.LoginActivity;
import com.example.quickcash.identity.Employee;
import com.example.quickcash.jobsearch.JobSearchActivity;
import com.example.quickcash.util.FirebaseUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseReference dbUserRef;
    public static FirebaseUtil userFirebase = new FirebaseUtil();
    private Button searchButton;
    private ListView jobListView;
    private FloatingActionButton addFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        userFirebase.setUsrID(intent.getStringExtra("userID"));
        userFirebase.setCurrentUserRef(intent.getStringExtra("userRef"));

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setActivityView();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_jobs, R.id.navigation_dashboard, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void init() {
        searchButton = findViewById(R.id.JobSearchBtn);
        jobListView = findViewById(R.id.list_jobs);
        addFAB = findViewById(R.id.addButton);
    }

    private void setActivityView() {
        final Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString("userType") != null) {
            final String activityPath = extras.getString("userType");
            if (activityPath.equals("employer")) {
                searchButton.setVisibility(View.GONE);
            } else {
                searchButton.setVisibility(View.VISIBLE);
                jobListView.setVisibility(View.GONE);
                addFAB.setVisibility(View.GONE);
            }
        }
    }

    public void logoutAccount(View view) {
        Intent logoutIntent = new Intent(this, LoginActivity.class);

        userFirebase.getCurrentUserRef().child("logged").setValue(false);
        startActivity(logoutIntent);
        finish();
    }

    public void openPostPage(View view) {
        Intent postIntent = new Intent(this, JobPostingActivity.class);

        startActivity(postIntent);
    }

    public void openSearchPage(View view) {
        Intent postIntent = new Intent(this, JobSearchActivity.class);

        startActivity(postIntent);
    }


}