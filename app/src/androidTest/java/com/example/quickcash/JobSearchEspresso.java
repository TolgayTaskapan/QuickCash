package com.example.quickcash;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.quickcash.jobsearch.JobSearchActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class JobSearchEspresso{
    private static DatabaseReference dbRef;

    @Rule
    public IntentsTestRule<JobSearchActivity> myIntentRule = new IntentsTestRule<>(JobSearchActivity.class);

    @BeforeClass
    public static void setup() {
        dbRef = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child("job");

    }

    @AfterClass
    public static void tearDown() {
        clearDatabase();
        System.gc();
    }

    private static void clearDatabase() {
        dbRef.removeValue();
    }

    private static void loginAsEmployee(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("Adminmin"));
        onView(withId(R.id.password)).perform(typeText("Admin123456"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
    }

    @Test
    public void searchWithPreferences(){
        loginAsEmployee();
        onView(withId(R.id.JobSearchBtn)).perform(click());






    }

    @Test
    public void searchWithoutPreferences(){
        loginAsEmployee();
        onView(withId(R.id.JobSearchBtn)).perform(click());






    }

}


