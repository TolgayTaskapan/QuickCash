package com.example.quickcash;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
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

    @Rule
    public IntentsTestRule<LandingPageActivity> myIntentRule = new IntentsTestRule<>(LandingPageActivity.class);

    private void loginAsEmployee(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("Adminmin"));
        onView(withId(R.id.password)).perform(typeText("Admin123456"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
    }

    @Test
    public void searchWithPreferences(){
        loginAsEmployee();
        onView(withId(R.id.JobSearchBtn)).perform(click());
        onView(withId(R.id.job_type_list)).perform(click());
        onData(is("Labour")).perform(click());
        onView(withId(R.id.hourly_wages_list)).perform(click());
        onData(is("$12.95 and up")).perform(click());
        onView(withId(R.id.distance_list)).perform(click());
        onData(is("3 KM")).perform(click());
        onView(withId(R.id.job_length_list)).perform(click());
        onData(is("3 to 5 days")).perform(click());
        onView(withId(R.id.job_search_button)).perform(click());

    }

    @Test
    public void searchWithoutPreferences(){
        loginAsEmployee();
        onView(withId(R.id.JobSearchBtn)).perform(click());
        onView(withId(R.id.job_search_button)).perform(click());





    }

}


