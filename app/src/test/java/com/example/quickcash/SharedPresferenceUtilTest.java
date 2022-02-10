package com.example.quickcash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import junit.framework.TestCase;

import org.junit.Test;

public class SharedPresferenceUtilTest extends TestCase {
    @Test
    public void checkIfLoginStatusUpdated() {
        assertTrue(UserRegistrationValidator.isEmptyUsername(""));
        assertFalse(UserRegistrationValidator.isEmptyUsername("abcdef"));
    }
}