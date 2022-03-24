package com.example.quickcash;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.quickcash.account.LoginActivity;
import com.example.quickcash.identity.Employee;
import com.example.quickcash.jobsearch.JobSearchActivity;


import com.example.quickcash.account.LoginActivity;
import com.example.quickcash.identity.User;
import com.example.quickcash.ui.jobs.JobAdapter;
import com.example.quickcash.util.UserSession;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private Button searchButton;
    private ListView jobListView;
    private FloatingActionButton addFAB;
    public DatabaseReference userRef;
    private DatabaseReference dbUserRef;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        init();
//        setActivityView();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_jobs, R.id.navigation_dashboard, R.id.navigation_profile).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }


    public void openPostPage() {
        Intent postIntent = new Intent(this, AddUpdateJobPostActivity.class);
        startActivity(postIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Update the account status when the MainActivity is quited
        //logoutAccount();
    }

    public void logoutAccount() {
        Intent logoutIntent = new Intent(this, LoginActivity.class);
        UserSession.getInstance().logout();
        startActivity(logoutIntent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}