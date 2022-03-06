package com.example.quickcash;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

public class JobPostingEspresso {

    @Rule
    public IntentsTestRule<LandingPageActivity> myIntentRuleHome = new IntentsTestRule<>(LandingPageActivity.class);


    @Test
    public void postJobSuccessful(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("TylerJ"));
        onView(withId(R.id.password)).perform(typeText("Johnson2021!"));
        onView(withId(R.id.employerLoginButton)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));

        onView(withId(R.id.addButton)).perform(click());
        intended(hasComponent(JobPostingActivity.class.getName()));

        //fill in job post fields
        onView(withId(R.id.jobTitle)).perform(typeText("Lawn Mowing"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.job_type_list)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Labour"))).perform(click());
        onView(withId(R.id.location)).perform(typeText("6299 South St, Halifax, NS B3H 4R2"));
        onView(withId(R.id.hourlyWage)).perform(typeText("15.00"));
        onView(withId(R.id.duration)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();


        onView(withId(R.id.button)).perform(click());
    }
}
