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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private UserLoginValidator loginValidator;
    DatabaseReference database;
    DatabaseReference account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        initializeDatabase();

        // Setup user login validator class.
        Context context = this.getApplicationContext();
        loginValidator = new UserLoginValidator(context, database);

        //initialize login status
        SharedPresferenceUtil.setLoginStatus(LoginActivity.this, false);

        Button employerLoginBtn = findViewById(R.id.employerLoginButton);
        Button employeeLoginBtn = findViewById(R.id.employeeLoginButton);

        employerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getUsername();
                String password = getPassword();

                boolean validDetails = loginValidator.authenticateUserCredentials(username, password);

                /**
                 * This code is here due to the nature of asynchronous code. The implementation would not work properly
                 * if code is put into a function.
                  **/

                if (validDetails) {
                    database.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot user : snapshot.getChildren()) {
                                    if (password.equals(user.child("password").getValue())) {
                                        account.child("loginStatus").setValue("1");
                                        loginAsEmployer();
                                        if (SharedPresferenceUtil.isLoggedIn(LoginActivity.this)) {
                                            jumpToJobSearchActivity();
                                        }
                                    } else {
                                        setStatusMessage(context.getResources().getString(R.string.INCORRECT_PASSWORD).trim());
                                    }
                                }

                            } else {
                                setStatusMessage(context.getResources().getString(R.string.USER_DOES_NOT_EXIST).trim());
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println("Error is connecting to database");
                        }

                    });
                } else {
                    setStatusMessage(context.getResources().getString(R.string.EMPTY_USERNAME_OR_PASSWORD).trim());
                }
            }
        });


        employeeLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = getUsername();
                String password = getPassword();

                boolean validDetails = loginValidator.authenticateUserCredentials(username, password);

                /**
                 * This code is here due to the nature of asynchronous code. The implementation would not work properly
                 * if code is put into a function.
                 **/

                if (validDetails) {
                    database.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot user : snapshot.getChildren()) {
                                    if (password.equals(user.child("password").getValue())) {
                                        loginAsEmployee();
                                        if (SharedPresferenceUtil.isLoggedIn(LoginActivity.this)) {
                                            jumpToJobSearchActivity();
                                        }
                                    } else {
                                        setStatusMessage(context.getResources().getString(R.string.INCORRECT_PASSWORD).trim());
                                    }
                                }

                            } else {
                                setStatusMessage(context.getResources().getString(R.string.USER_DOES_NOT_EXIST).trim());
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println("Error is connecting to database");
                        }

                    });
                } else {
                    setStatusMessage(context.getResources().getString(R.string.EMPTY_USERNAME_OR_PASSWORD).trim());
                }

            }
        });

    }

    /** connect to the firebase realtime database **/
    public void initializeDatabase(){
        database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child("Account");
        //account = database.push();
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

    /** record error status for easy testing **/
    public void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }


    public void loginAsEmployer(){
        SharedPresferenceUtil.setLoginStatus(LoginActivity.this, true);
    }

    public void loginAsEmployee(){
        SharedPresferenceUtil.setLoginStatus(LoginActivity.this, true);
    }


    protected void jumpToJobSearchActivity() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, JobSearchActivity.class);
        startActivity(intent);
    }
}
