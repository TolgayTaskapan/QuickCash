package com.example.quickcash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserRegistrationJUnit {

    @Test
    public void checkIfUsernameIsEmpty() {
        assertTrue(UserRegistrationValidator.isEmptyUsername(""));
        assertFalse(UserRegistrationValidator.isEmptyUsername("abcdef"));
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        assertTrue(UserRegistrationValidator.isEmptyPassword(""));
        assertFalse(UserRegistrationValidator.isEmptyPassword("A_aaaaaaa"));
    }

    @Test
    public void checkIfPasswordIsValid(){
        assertTrue(UserRegistrationValidator.isValidPassword("Aa_1234567"));
        assertFalse(UserRegistrationValidator.isValidPassword("aA_"));
        assertFalse(UserRegistrationValidator.isValidPassword("12345678A"));
    }

    @Test
    public void checkIfUsernameIsValid(){
        assertTrue(UserRegistrationValidator.isValidUsername("asd123456"));
        assertFalse(UserRegistrationValidator.isValidUsername("!asd123465"));
        assertFalse(UserRegistrationValidator.isValidUsername("as"));
    }
}

