package com.example.quickcash.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.identity.User;
import com.example.quickcash.util.FirebaseUtil;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileUpdateActivity extends AppCompatActivity {
    private Spinner prefer_spinner;
    private Button finishButton;
    public String preferType;
    public DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        userRef = ProfileFragment.userRef;
        prefer_spinner = (Spinner) findViewById(R.id.prefer_spinner);
        prefer_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                preferType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        finishButton = (Button) findViewById(R.id.updateFinishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferType != null) {
                    Map<String, Object> preferUpdate = new HashMap<>();
                    preferUpdate.put("prefer", preferType);
                    userRef.updateChildren(preferUpdate);
                    Toast.makeText(getApplicationContext(), "Save successfully!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Some fields are empty", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}