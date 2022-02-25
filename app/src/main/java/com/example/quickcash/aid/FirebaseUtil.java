package com.example.quickcash.aid;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class FirebaseUtil implements Serializable {

    public static final String FIREBASE_URL = "https://quick-cash-ca106-default-rtdb.firebaseio.com/";
    public static final String EMPLOYEE_COLLECTION = "Employee";
    public static final String EMPLOYER_COLLECTION = "Employer";

    private DatabaseReference db;
    private DatabaseReference currentUserRef;
    private String usrID;

    public FirebaseUtil() {
        db = FirebaseDatabase.getInstance(FIREBASE_URL).getReference();
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

    public void setCurrentUserRef(String currentUser) {
        currentUser = currentUser.substring(currentUser.indexOf("Account"));
        System.out.println(currentUser);
        currentUserRef = db.child(currentUser).getRef();
    }
}
