package com.example.quickcash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationPageActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean userExists;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        initializeDatabase();
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

    /**check if user account exists, if it does call registerUser() **/
    public void checkIfAccountExists(String userName, String password) {
        database.orderByChild("username").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    setStatusMessage(getResources().getString(R.string.USER_ALREADY_EXISTS).trim());
                } else {
                    registerUser(userName, password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                setStatusMessage("Error connecting to database");
            }

        });
    }


    /** save user's username and password to the database **/
    public void registerUser(String username, String password){
        final DatabaseReference account = database.push();
        account.child("username").setValue(username);
        account.child("password").setValue(password);
    }

    /** check if the user has entered a username **/
    public boolean isEmptyUsername(String username){
        return username.isEmpty();
    }

    /** check if the user has entered a password **/
    public boolean isEmptyPassword(String password){
        return password.isEmpty();
    }

    /** check if the user has entered a valid username
     *  the format is 6 to 16 bits in length
     *  and cannot contain special characters **/
    public boolean isValidUsername(String username){
        if(username.length() < 6 || username.length() > 16){
            return false;
        }
        // regular expressions
        String symbol = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";
        return !username.matches(symbol);
    }

    /** check if the user has entered a valid password
     *  the format is 8 to 16 bits in length
     *  and must contain the following three types of characters:
     *  uppercase, lowercase, number, symbol **/
    public boolean isValidPassword(String password){
        if(password.length() > 16 || password.length() < 8){
            return false;
        }
        // regular expressions
        String number = ".*\\d+.*";
        String uppercase = ".*[A-Z]+.*";
        String lowercase = ".*[a-z]+.*";
        String symbol = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";

        int i = 0;
        if (password.matches(number)) i++;
        if (password.matches(uppercase))i++;
        if (password.matches(lowercase)) i++;
        if (password.matches(symbol)) i++;

        if (i >= 3 ){
            return true;
        } else {
            return false;
        }

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
        String error = new String("error");

        if (isEmptyUsername(username) || isEmptyPassword(password)) {
            error = getResources().getString(R.string.EMPTY_USERNAME_OR_PASSWORD).trim();
            setStatusMessage(error);
            System.out.println(error);
        } else if (!isValidUsername(username)){
            error = getResources().getString(R.string.INVALID_USERNAME).trim();
            setStatusMessage(error);
            System.out.println(error);
        } else if (!isValidPassword(password)){
            error = getResources().getString(R.string.INVALID_PASSWORD).trim();
            setStatusMessage(error);
            System.out.println(error);
        } else {
            error = getResources().getString(R.string.EMPTY_STRING).trim();
            setStatusMessage(error);
        }

            /** check if user exists **/
           checkIfAccountExists(username, password);
    }
}