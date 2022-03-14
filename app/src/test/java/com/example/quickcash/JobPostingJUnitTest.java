package com.example.quickcash;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.quickcash.account.SignupValidator;

import org.junit.Test;

public class JobPostingJUnitTest {

    @Test
    public void checkIfJobTitleIsEmpty() {
        assertTrue(JobPostingValidator.isEmptyJobname(""));
        assertFalse(JobPostingValidator.isEmptyJobname("Lawn Mowing"));
    }

    @Test
    public void checkIfJobTypeIsEmpty() {
        assertTrue(JobPostingValidator.isEmptyJobtype(""));
        assertFalse(JobPostingValidator.isEmptyJobtype("Labour"));
    }

    @Test
    public void checkIfJobWageIsEmpty() {
        assertTrue(JobPostingValidator.isEmptyWage(""));
        assertFalse(JobPostingValidator.isEmptyWage("15.00"));
    }

    @Test
    public void checkIfJobLocationIsEmpty() {
        assertTrue(JobPostingValidator.isEmptyLocation(""));
        assertFalse(JobPostingValidator.isEmptyLocation("Halifax"));
    }

    @Test
    public void checkIfJobTitleIsValid(){
        assertTrue(JobPostingValidator.isValidJobname("Lawn Mowing"));
        assertFalse(JobPostingValidator.isValidJobname("diuhweuhieuhiwehviuhweviu"));
        assertFalse(JobPostingValidator.isValidJobname("Cutting Grass?!"));
    }

    @Test
    public void checkIfJobWageIsValid(){
        assertTrue(JobPostingValidator.isValidWage(15.00));
        assertFalse(JobPostingValidator.isValidWage(-15.00));
    }

    @Test
    public void checkIfJobLocationIsValid(){
        assertTrue(JobPostingValidator.isValidCoordinates(44.636520, -63.584770));
        assertFalse(JobPostingValidator.isValidCoordinates(0.00, 0.00));
    }


}
