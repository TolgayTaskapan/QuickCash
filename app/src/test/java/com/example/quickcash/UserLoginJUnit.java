package com.example.quickcash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.quickcash.account.LoginValidator;

import org.junit.Test;

public class UserLoginJUnit {

    @Test
    public void checkIfUsernameIsEmpty(){
        assertTrue(LoginValidator.isEmptyUsername(""));
        assertFalse(LoginValidator.isEmptyUsername("taichengzz"));
    }

    @Test
    public void checkIfPasswordIsEmpty(){
        assertTrue(LoginValidator.isEmptyPassword(""));
        assertFalse(LoginValidator.isEmptyPassword("Asd123456"));
    }

}
