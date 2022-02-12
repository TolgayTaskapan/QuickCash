package com.example.quickcash.identity;

public abstract class User {
    private String username;
    private String password;
    private boolean status;
    private String userType;

    public User(){}

    public User(String username, String password, String type, boolean status){
        this.username = username;
        this.password = password;
        this.userType = type;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setLogged(boolean logged) {
        this.status = logged;
    }

    public boolean isLogged() {
        return status;
    }
}
