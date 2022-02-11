package com.example.quickcash;

public class Users {
    public static final String TAG = "User";
    private String username;
    private String password;
    private boolean isLogged;
    private String userType;

    public Users(){}

    public Users(String username, String password, String type, boolean status){
        this.username = username;
        this.password = password;
        this.userType = type;
        this.isLogged = status;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setLogged(boolean logged) {
        this.isLogged = logged;
    }

    public boolean isLogged() {
        return isLogged;
    }
}
