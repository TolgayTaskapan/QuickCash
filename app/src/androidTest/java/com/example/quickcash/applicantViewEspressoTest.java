package com.example.quickcash;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

public class applicantViewEspressoTest {

    @Rule
    public IntentsTestRule<LandingPageActivity> myIntentRuleHome = new IntentsTestRule<>(LandingPageActivity.class);

    private void loginAsEmployer(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("TylerJ"));
        onView(withId(R.id.password)).perform(typeText("Johnson2021!"));
        onView(withId(R.id.employerLoginButton)).perform(click());
    }

    @Test
    public void checkApplicantView(){
      /*  loginAsEmployer();
        onView(withId(R.id.applicants_listview)).perform;*/


    }










}