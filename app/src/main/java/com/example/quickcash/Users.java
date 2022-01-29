package com.example.quickcash;

public class Users {
    public static final String TAG = "User";
    private String username;
    private String password;

    public Users(){}

    public Users(String username, String password){
        this.username = username;
        this.password = password;
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
}
