package com.example.quickcash.util;

import com.example.quickcash.identity.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class UserSession implements Serializable {

    public static final String FIREBASE_URL = "https://quick-cash-ca106-default-rtdb.firebaseio.com/";
    public static final String EMPLOYEE_COLLECTION = "Employee";
    public static final String EMPLOYER_COLLECTION = "Employer";
    public static final String JOB_COLLECTION = "job";

    private static UserSession instance;
    private DatabaseReference db;
    private DatabaseReference currentUserRef;
    private String usrID;
    private User user;

    public UserSession() {
        db = FirebaseDatabase.getInstance(FIREBASE_URL).getReference();
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void logout(){
        instance = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateStatus() {

    }

    public String getUsrID() {
        return usrID;
    }

    public void setUsrID(String usrID) {
        this.usrID = usrID;
    }

    public DatabaseReference getDb() {
        return db;
    }

    public DatabaseReference getCurrentUserRef() {
        return currentUserRef;
    }

    public void setCurrentUserRef(DatabaseReference currentUser) {
        currentUserRef = currentUser;
    }
}
