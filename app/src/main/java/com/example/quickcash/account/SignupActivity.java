package com.example.quickcash.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.example.quickcash.identity.Employee;
import com.example.quickcash.identity.Employer;
import com.example.quickcash.identity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //variables
    private SignupValidator signupValidator;
    DatabaseReference database;
    DatabaseReference dbUser;
    DatabaseReference dbEmployee;
    DatabaseReference dbEmployer;
    Context context;

    private String username;
    private String password;
    private String userType;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        initializeDatabase();

        // Setup user registration validator class.
        context = this.getApplicationContext();
        signupValidator = new SignupValidator(context, database);

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        setupIdentitySpinner();
    }

    public String getUsername() {
        EditText usernameET = findViewById(R.id.username);
        return usernameET.getText().toString().trim();
    }

    public String getPassword() {
        EditText passwordET = findViewById(R.id.password);
        return passwordET.getText().toString().trim();
    }

    public void backToLoginPage() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
        finish();
    }

    /**
     * binding click events to registration buttons
     **/
    @Override
    public void onClick(View view) {
        writeDataToFirebase();
    }

    /**
     * connect to the firebase realtime database
     **/
    public void initializeDatabase() {
        database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child("Account");
        dbEmployee = database.child("Employee").push();
        dbEmployer = database.child("Employer").push();
    }

    /**
     * save user's username and password to the database
     **/
    public void registerUser(String username, String password) {
        // User info attributes
        User user;
        if (userType.equals("Employee")) {
            user = new Employee(username, password, false);
            dbEmployee.setValue(user);
            userID = dbEmployee.getKey();
        } else {
            user = new Employer(username, password, false);
            dbEmployer.setValue(user);
            userID = dbEmployer.getKey();
        }
    }

    /**
     * write the user signup info into the Firebase
     **/
    public void writeDataToFirebase() {
        username = getUsername();
        password = getPassword();

        boolean validUser = signupValidator.validateUserDetails(username, password);
        String error = signupValidator.getErrorMsg();

        if (validUser) {
            dbUser = database.child(userType);

            dbUser.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        displayToast(context.getResources().getString(R.string.USER_ALREADY_EXISTS).trim());
                    } else {
                        registerUser(username, password);
                        backToLoginPage();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    displayToast(context.getResources().getString(R.string.DATABASE_ERROR).trim());

                }
            });
        } else {
            displayToast(error);
        }
    }

    /**
     * setting up the spinner component for this page
     **/
    private void setupIdentitySpinner() {
        // Create the spinner.
        Spinner spinner = findViewById(R.id.identitySpinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        // Create ArrayAdapter using the string array and default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.identity_labels, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        userType = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        userType = adapterView.getSelectedItem().toString();
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}