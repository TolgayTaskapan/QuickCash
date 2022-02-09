package com.example.quickcash;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class UserLoginValidator {

    private final Context context;
    private String errorMsg = "";
    DatabaseReference database;
    public boolean VALID_USER = false;

    public UserLoginValidator(Context context, DatabaseReference database){
        this.context = context.getApplicationContext();
        this.database = database;
    }

    /** set if user exists **/
    public void setVALID_USER(boolean VALID_USER) {
        this.VALID_USER = VALID_USER;
    }

    /** get error message **/
    public String getErrorMsg(){
        return this.errorMsg;
    }

    /** check if the user has entered a username **/
    public static boolean isEmptyUsername(String username){
        return username.isEmpty();
    }

    /** check if the user has entered a password **/
    public static boolean isEmptyPassword(String password){
        return password.isEmpty();
    }

    /**checking entered credentials **/
    public boolean checkIfCredentialsAreValid(String userName, String password) {
        database.orderByChild("username").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot user : snapshot.getChildren()) {



//                        if (usersBean.password.equals(txvPassword.getText().toString().trim())) {
//                            Intent intent = new Intent(context, MainActivity.class);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(context, "Password is wrong", Toast.LENGTH_LONG).show();
//                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error is connecting to database");
            }

        });
        return VALID_USER;
    }
}
