package com.example.quickcash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RegistrationJUnit {
    static RegistrationPageActivity registrationPageActivity;

    @BeforeClass
    public static void setup() {
        registrationPageActivity = new RegistrationPageActivity();
    }

    @AfterClass
    public static void tearDown(){
        System.gc();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkIfUsernameIsEmpty() {
        assertTrue(registrationPageActivity.isEmptyUsername(""));
        assertFalse(registrationPageActivity.isEmptyUsername("abcdef"));
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        assertTrue(registrationPageActivity.isEmptyPassword(""));
        assertFalse(registrationPageActivity.isEmptyPassword("A_aaaaaaa"));
    }

    @Test
    public void checkIfPasswordIsValid(){
        assertTrue(registrationPageActivity.isValidPassword("Aa_1234567"));
        assertFalse(registrationPageActivity.isValidPassword("aA_"));
        assertFalse(registrationPageActivity.isValidPassword("12345678A"));
    }

    @Test
    public void checkIfUsernameIsValid(){
        assertTrue(registrationPageActivity.isValidUsername("asd123456"));
        assertFalse(registrationPageActivity.isValidUsername("!asd123465"));
        assertFalse(registrationPageActivity.isValidUsername("as"));
    }
}