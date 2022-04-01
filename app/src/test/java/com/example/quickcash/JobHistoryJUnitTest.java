package com.example.quickcash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.quickcash.identity.Employee;
import com.example.quickcash.identity.User;

import org.junit.Test;

public class JobHistoryJUnitTest {

    User user = new Employee("TylerJ", "Johnson2021!", false);
    JobPost post = new JobPost("Dog Walker", "Pet Care", 12.95, 1, 44.6489627, -63.5753839, "-My5Hr1e-FEJhRs45Kvz");

    @Test
    public void isJobListIncreasing() {
        user.jobHistory.addCompletedJob(post);
        assertEquals(1, user.jobHistory.getNumberOfJobs());
        user.jobHistory.addCompletedJob(post);
        user.jobHistory.addCompletedJob(post);
        assertEquals(3, user.jobHistory.getNumberOfJobs());
    }

    @Test
    public void isRatingsListIncreasing() {
        user.jobHistory.addRate(3);
        assertEquals(1, user.jobHistory.getNumberOfRatings());
        user.jobHistory.addRate(4);
        user.jobHistory.addRate(5);
        assertEquals(3, user.jobHistory.getNumberOfRatings());
    }

    @Test
    public void addingTotalAmountEarned() {
        user.jobHistory.increaseTotalAmount(12.95);
        assertEquals(12.95, user.jobHistory.getTotalAmountEarned(), 0.1);
        user.jobHistory.increaseTotalAmount(15.00);
        assertEquals(27.95, user.jobHistory.getTotalAmountEarned(), 0.1);
    }

    @Test
    public void gettingRatingAverage() {
        user.addRating(4);
        user.addRating(5);
        user.addRating(3);
        user.addRating(3);

        assertEquals(4, user.jobHistory.getNumberOfRatings());
        assertEquals(3.75, user.getRating(), 0.1);
    }


}
