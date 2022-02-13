package com.example.quickcash;

import com.example.quickcash.account.SignupValidator;

import org.junit.Assert;
import org.junit.Test;

public class SharedPreferenceUtilTest {
    @Test
    public void checkIfLoginStatusUpdated() {
        Assert.assertTrue(SignupValidator.isEmptyUsername(""));
        Assert.assertFalse(SignupValidator.isEmptyUsername("abcdef"));
    }
}