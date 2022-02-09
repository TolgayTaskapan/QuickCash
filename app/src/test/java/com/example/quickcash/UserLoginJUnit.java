package com.example.quickcash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserLoginJUnit {

    @Test
    public void checkIfUsernameIsEmpty(){
        assertTrue(UserLoginValidator.isEmptyUsername(""));
        assertFalse(UserLoginValidator.isEmptyUsername("taichengzz"));
    }

    @Test
    public void checkIfPasswordIsEmpty(){
        assertTrue(UserLoginValidator.isEmptyPassword(""));
        assertFalse(UserLoginValidator.isEmptyPassword("Asd123456"));
    }

}
