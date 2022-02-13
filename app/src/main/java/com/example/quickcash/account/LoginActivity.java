package com.example.quickcash.account;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.aid.FirebaseUtil;
import com.example.quickcash.identity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    //variables
    private LoginValidator loginValidator;
    DatabaseReference database;
    DatabaseReference dbUser;

    // User info attributes
    private String userID;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        initializeDatabase();

        // If from signup page get the userID directly
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        // Setup user login validator class.
        Context context = this.getApplicationContext();
        loginValidator = new LoginValidator(context, database);

        //initialize login status
        SharedPreferenceUtil.setLoginStatus(LoginActivity.this, false);

        Button employerLoginBtn = findViewById(R.id.employerLoginButton);
        Button employeeLoginBtn = findViewById(R.id.employeeLoginButton);

        employerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getUsername();
                String password = getPassword();
                userType = "Employer";

                boolean validDetails = loginValidator.authenticateUserCredentials(username, password);

                /**
                 * This code is here due to the nature of asynchronous code. The implementation would not work properly
                 * if code is put into a function.
                  **/

                if (validDetails) {
                    dbUser = database.child(userType);
                    dbUser.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot user : snapshot.getChildren()) {
                                    if (password.equals(user.child("password").getValue())) {
                                        dbUser = user.getRef();
                                        userID = user.getKey();
                                        loginAsEmployer(dbUser);
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
                userType = "Employee";

                boolean validDetails = loginValidator.authenticateUserCredentials(username, password);

                /**
                 * This code is here due to the nature of asynchronous code. The implementation would not work properly
                 * if code is put into a function.
                 **/

                if (validDetails) {
                    dbUser = database.child(userType);
                    dbUser.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot user : snapshot.getChildren()) {
                                    if (password.equals(user.child("password").getValue())) {
                                        dbUser = user.getRef();
                                        userID = user.getKey();
                                        loginAsEmployee(dbUser);
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


    public void loginAsEmployer(DatabaseReference dbUser){
        Map<String, Object> userLoginUpdate = new HashMap<>();
        userLoginUpdate.put("logged", true);
        dbUser.updateChildren(userLoginUpdate);
        System.out.println(dbUser.toString());

        jumpToMainActivity(dbUser);
    }

    public void loginAsEmployee(DatabaseReference dbUser){
        Map<String, Object> userLoginUpdate = new HashMap<>();
        userLoginUpdate.put("logged", true);
        dbUser.updateChildren(userLoginUpdate);

        jumpToMainActivity(dbUser);
    }

    protected void jumpToJobSearchActivity( DatabaseReference dbUser) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);

        // Delivery the userID to MainActivity
        intent.putExtra("userID", userID);
        intent.putExtra("userRef", dbUser.toString());

        startActivity(intent);
        finish();
    }
}
