package com.example.quickcash.identity;

import com.example.quickcash.JobApplication;

public abstract class User {
    public static final int IDENTITY_EMPLOYEE = 1;
    public static final int IDENTITY_EMPLOYER = 2;

    private String username;
    private String password;
    private String identity;
    private boolean status;
    public String prefer;
    private JobApplication applications;

    protected User(){}

    protected User(String username, String password, boolean status){
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIdentity() {
        return identity;
    }

    public String getPrefer() {
        return prefer;
    }
    public boolean isEmployee(){
        return this.identity.equals("Employee");
    }

    public boolean isEmployer(){
        return this.identity.equals("Employer");
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setLogged(boolean logged) {
        this.status = logged;
    }

    public void setPrefer(String selectedPrefer) {
        this.prefer = selectedPrefer;
    }

    public boolean isLogged() {
        return status;
    }
}
