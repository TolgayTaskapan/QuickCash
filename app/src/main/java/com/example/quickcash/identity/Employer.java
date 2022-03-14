package com.example.quickcash.identity;

public class Employer extends User{
    public Employer() {
    }

    public Employer(String username, String password, boolean status) {
        super(username, password, status);
        setIdentity("Employer");
    }
}
