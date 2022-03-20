package com.example.quickcash;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quickcash.account.LoginActivity;
import com.example.quickcash.identity.User;
import com.example.quickcash.ui.jobs.JobAdapter;
import com.example.quickcash.util.UserSession;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
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
    private DatabaseReference dbUserRef;
    //public static UserSession userFirebase;

    private Button searchButton;
    private RecyclerView recyclerView;
    private FloatingActionButton addFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

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
        recyclerView = findViewById(R.id.jobsRecyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        addFAB = findViewById(R.id.addButton);
    }

    private void setActivityView() {
       String userType = UserSession.getInstance().getUser().getIdentity();
        if (userType != null) {
            if (userType.equals("Employer")) {
                //searchButton.setVisibility(View.GONE);
            } else {
                //searchButton.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                addFAB.setVisibility(View.GONE);
            }
        } else {
            System.out.println("nothing found");
        }
    }

    public void logoutAccount(View view) {
        Intent logoutIntent = new Intent(this, LoginActivity.class);

        UserSession.getInstance().logout();
        startActivity(logoutIntent);
        finish();
    }

    public void openPostPage(View view) {
        Intent postIntent = new Intent(this, AddUpdateJobPostActivity.class);

        startActivity(postIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        setActivityView();
    }
}