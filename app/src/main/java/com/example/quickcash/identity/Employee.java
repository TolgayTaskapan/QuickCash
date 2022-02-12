package com.example.quickcash.identity;

public class Employee extends User{
    public Employee() {

    }

    public Employee(String username, String password, boolean status) {
        super(username, password, status);
    }
}
