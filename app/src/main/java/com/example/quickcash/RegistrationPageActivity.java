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

public class RegistrationPageActivity extends AppCompatActivity implements View.OnClickListener {

    //variables
    private UserRegistrationValidator userRegistrationValidator;
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        initializeDatabase();

        // Setup user registration validator class.
        Context context = this.getApplicationContext();
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
    }


    /** save user's username and password to the database **/
    public void registerUser(String username, String password){
        final DatabaseReference account = database.push();
        account.child("username").setValue(username);
        account.child("password").setValue(password);
    }

    /** record error status for easy testing **/
    public void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }

    /** binding click events to registration buttons **/
    @Override
    public void onClick(View view) {
        String username = getUsername();
        String password = getPassword();

        boolean validUser = userRegistrationValidator.validateUserDetails(username, password) && !userRegistrationValidator.userExists;
        String error = userRegistrationValidator.getErrorMsg();

        setStatusMessage(error);

        if (validUser) registerUser(username, password);

    }
}