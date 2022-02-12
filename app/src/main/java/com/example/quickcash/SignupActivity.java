package com.example.quickcash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationPageActivity extends AppCompatActivity implements View.OnClickListener {

    //variables
    private UserRegistrationValidator userRegistrationValidator;
    DatabaseReference database;
    DatabaseReference account;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        initializeDatabase();

        // Setup user registration validator class.
        context = this.getApplicationContext();
        userRegistrationValidator = new UserRegistrationValidator(context, database);

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

    }

    public String getUsername() {
        EditText usernameET = findViewById(R.id.username);
        return usernameET.getText().toString().trim();
    }

    public String getPassword() {
        EditText passwordET = findViewById(R.id.password);
        return passwordET.getText().toString().trim();
    }
    /** connect to the firebase realtime database **/
    public void initializeDatabase(){
        database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child("account");
        account = database.push();
    }


    /** save user's username and password to the database **/
    public void registerUser(String username, String password){
        account.child("username").setValue(username);
        account.child("password").setValue(password);
        account.child("loginStatus").setValue("0");
        account.child("userType").setValue("null");
    }

    /** record error status for easy testing **/
    public void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }

    public void backToHome(){
        Intent intent = new Intent();
        intent.setClass(RegistrationPageActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    /** binding click events to registration buttons **/
    @Override
    public void onClick(View view) {
        String username = getUsername();
        String password = getPassword();

        boolean validUser = userRegistrationValidator.validateUserDetails(username, password);
        String error = userRegistrationValidator.getErrorMsg();

        setStatusMessage(error);

        database.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    setStatusMessage(context.getResources().getString(R.string.USER_ALREADY_EXISTS).trim());
                } else{
                    if (validUser) registerUser(username, password);
                    backToHome();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error is connecting to database");
            }

        });

    }
}