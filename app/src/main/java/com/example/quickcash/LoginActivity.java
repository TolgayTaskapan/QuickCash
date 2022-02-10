package com.example.quickcash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private UserLoginValidator loginValidator;
    DatabaseReference database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        initializeDatabase();

        // Setup user login validator class.
        Context context = this.getApplicationContext();
        loginValidator = new UserLoginValidator(context, database);

        Button employerLoginBtn = findViewById(R.id.employerLoginButton);
        Button employeeLoginBtn = findViewById(R.id.employeeLoginButton);

        employerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getUsername();
                String password = getPassword();

                //boolean validUser = userRegistrationValidator.validateUserDetails(username, password) && !userRegistrationValidator.userExists;

            }
        });

        employeeLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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

    private void initializeAuthentication(){ auth = FirebaseAuth.getInstance(); }



    private void login(){
        auth.signOut();
        SharedPresferenceUtil.setLoginStatus(JobSearchActivity.this, false);
        finish();
        System.exit(0);
    }

    protected void jumpToJobSearchActivity() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, JobSearchActivity.class);
        startActivity(intent);
    }
}
