package com.example.quickcash.jobsearch;

import junit.framework.TestCase;

public class JobSearchActivityTest extends TestCase {

    public void testAssignMinWage() {
        assertEquals(12.95, JobSearchActivity.assignMinWage("$12.95 and up"));
    }

    public void testAssignPreferredDistance() {
        double temp = 1000;
        assertEquals(temp, JobSearchActivity.assignPreferredDistance("1 KM"));
    }
}