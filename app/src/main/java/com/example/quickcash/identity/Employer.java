package com.example.quickcash.identity;

import com.google.firebase.database.DatabaseReference;

public class Employer extends User{
    public Employer() {
    }

    public Employer(String username, String password, boolean status) {
        super(username, password, status);
    }
}
