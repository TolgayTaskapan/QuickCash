package com.example.quickcash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.quickcash.account.SignupValidator;

import org.junit.Test;

public class UserSignupJUnitTest {

    @Test
    public void checkIfUsernameIsEmpty() {
        assertTrue(SignupValidator.isEmptyUsername(""));
        assertFalse(SignupValidator.isEmptyUsername("abcdef"));
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        assertTrue(SignupValidator.isEmptyPassword(""));
        assertFalse(SignupValidator.isEmptyPassword("A_aaaaaaa"));
    }

    @Test
    public void checkIfPasswordIsValid(){
        assertTrue(SignupValidator.isValidPassword("Aa_1234567"));
        assertFalse(SignupValidator.isValidPassword("aA_"));
        assertFalse(SignupValidator.isValidPassword("12345678A"));
    }

    @Test
    public void checkIfUsernameIsValid(){
        assertTrue(SignupValidator.isValidUsername("asd123456"));
        assertFalse(SignupValidator.isValidUsername("!asd123465"));
        assertFalse(SignupValidator.isValidUsername("as"));
    }
}

