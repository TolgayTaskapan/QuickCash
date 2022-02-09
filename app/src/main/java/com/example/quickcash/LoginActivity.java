package com.example.quickcash;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        initializeDatabase();

//        Button registerButton = findViewById(R.id.registerButton);
//        registerButton.setOnClickListener(this);

    }

    /** connect to the firebase realtime database **/
    public void initializeDatabase(){
        database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child("account");
    }

    /** get username **/
    public String getUsername() {
        EditText usernameET = findViewById(R.id.username);
        return usernameET.getText().toString().trim();
    }

    /** get user password **/
    public String getPassword() {
        EditText passwordET = findViewById(R.id.password);
        return passwordET.getText().toString().trim();
    }

    /** check if the user has entered a username **/
    public static boolean isEmptyUsername(String username){
        return username.isEmpty();
    }

    /** check if the user has entered a password **/
    public static boolean isEmptyPassword(String password){
        return password.isEmpty();
    }

    /** record error status for easy testing **/
    public void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }





    @Override
    public void onClick(View view) {
        String username = getUsername();
        String password = getPassword();
        String error = "";

        setStatusMessage(error);

        //if login successful, open post a job activity

    }
}
