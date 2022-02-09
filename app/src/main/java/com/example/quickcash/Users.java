package com.example.quickcash;

public class Users {
    public static final String TAG = "User";
    private String username;
    private String password;
    private boolean loginStatus;

    public Users(){}

    public Users(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Users(String username, String password, boolean loginStatus){
        this.username = username;
        this.password = password;
        this.loginStatus = loginStatus;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getLoginStatus(){ return loginStatus;}

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLoginStatus(boolean loginStatus){this.loginStatus = loginStatus; }
}
