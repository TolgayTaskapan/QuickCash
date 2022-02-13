package com.example.quickcash;

import android.content.Intent;
<<<<<<< HEAD
=======
import android.net.Uri;
>>>>>>> ba6c5222d554ae6c0a17cc9fa8c86eb9e78d24b3
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.quickcash.account.LoginActivity;
import com.example.quickcash.aid.FirebaseUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.quickcash.databinding.ActivityMainBinding;
<<<<<<< HEAD
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
=======
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
>>>>>>> ba6c5222d554ae6c0a17cc9fa8c86eb9e78d24b3

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
<<<<<<< HEAD
    private DatabaseReference dbUserRef;
=======
    private FirebaseUtil userFirebase = new FirebaseUtil();
>>>>>>> ba6c5222d554ae6c0a17cc9fa8c86eb9e78d24b3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

<<<<<<< HEAD
        //accessing user database reference from string passed from login
        Intent intent = getIntent();
        String userRefURL = intent.getStringExtra("userRef");
        dbUserRef = FirebaseDatabase.getInstance().getReferenceFromUrl(userRefURL);
=======
        Intent intent = getIntent();
        userFirebase.setUsrID(intent.getStringExtra("userID"));
        userFirebase.setCurrentUserRef(intent.getStringExtra("userRef"));
>>>>>>> ba6c5222d554ae6c0a17cc9fa8c86eb9e78d24b3

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

    public void logoutAccount(View view) {
        Intent logoutIntent = new Intent(this, LoginActivity.class);

        System.out.println(userFirebase.getCurrentUserRef());
        userFirebase.getCurrentUserRef().child("logged").setValue(false);
        startActivity(logoutIntent);
        finish();
    }
}