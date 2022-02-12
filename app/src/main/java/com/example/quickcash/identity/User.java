package com.example.quickcash.identity;

public abstract class User {
    public static final int IDENTITY_EMPLOYEE = 1;
    public static final int IDENTITY_EMPLOYER = 2;

    private String username;
    private String password;
    private boolean status;

    public User(){}

    public User(String username, String password, boolean status){
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLogged(boolean logged) {
        this.status = logged;
    }

    public boolean isLogged() {
        return status;
    }
}
