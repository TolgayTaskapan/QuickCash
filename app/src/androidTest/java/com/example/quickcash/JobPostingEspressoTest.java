package com.example.quickcash;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

public class JobPostingEspressoTest {

    @Rule
    public IntentsTestRule<LandingPageActivity> myIntentRuleHome = new IntentsTestRule<>(LandingPageActivity.class);


    private void loginAsEmployer(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("TylerJ"));
        onView(withId(R.id.password)).perform(typeText("Johnson2021!"));
        onView(withId(R.id.employerLoginButton)).perform(click());
    }

    @Test
    public void postJobSuccessful(){
        loginAsEmployer();

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
        onView(withText(R.string.JOB_POST_SUCCESS)).inRoot(withDecorView(not(is(this.myIntentRuleHome.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void postJobEmptyTitle(){
        loginAsEmployer();

        onView(withId(R.id.addButton)).perform(click());
        intended(hasComponent(JobPostingActivity.class.getName()));

        //fill in job post fields
        onView(withId(R.id.jobTitle)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.job_type_list)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Labour"))).perform(click());
        onView(withId(R.id.location)).perform(typeText("6299 South St, Halifax, NS B3H 4R2"));
        onView(withId(R.id.hourlyWage)).perform(typeText("15.00"));
        onView(withId(R.id.duration)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.button)).perform(click());
        onView(withText(R.string.EMPTY_JOBNAME_OR_DESCRIPTION)).inRoot(withDecorView(not(is(this.myIntentRuleHome.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void postJobEmptyType(){
        loginAsEmployer();

        onView(withId(R.id.addButton)).perform(click());
        intended(hasComponent(JobPostingActivity.class.getName()));

        //fill in job post fields
        onView(withId(R.id.jobTitle)).perform(typeText("Lawn Mowing"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.location)).perform(typeText("6299 South St, Halifax, NS B3H 4R2"));
        onView(withId(R.id.hourlyWage)).perform(typeText("15.00"));
        onView(withId(R.id.duration)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.button)).perform(click());
        onView(withText(R.string.EMPTY_JOBNAME_OR_DESCRIPTION)).inRoot(withDecorView(not(is(this.myIntentRuleHome.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void postJobEmptyWage(){
        loginAsEmployer();

        onView(withId(R.id.addButton)).perform(click());
        intended(hasComponent(JobPostingActivity.class.getName()));

        //fill in job post fields
        onView(withId(R.id.jobTitle)).perform(typeText("Lawn Mowing"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.job_type_list)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Labour"))).perform(click());
        onView(withId(R.id.location)).perform(typeText("6299 South St, Halifax, NS B3H 4R2"));
        onView(withId(R.id.hourlyWage)).perform(typeText(""));
        onView(withId(R.id.duration)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.button)).perform(click());
        onView(withText(R.string.EMPTY_WAGE)).inRoot(withDecorView(not(is(this.myIntentRuleHome.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void postJobEmptyLocation(){
        loginAsEmployer();

        onView(withId(R.id.addButton)).perform(click());
        intended(hasComponent(JobPostingActivity.class.getName()));

        //fill in job post fields
        onView(withId(R.id.jobTitle)).perform(typeText("Lawn Mowing"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.job_type_list)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Labour"))).perform(click());
        onView(withId(R.id.location)).perform(typeText(""));
        onView(withId(R.id.hourlyWage)).perform(typeText("15.00"));
        onView(withId(R.id.duration)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.button)).perform(click());
        onView(withText(R.string.EMPTY_LOCATION)).inRoot(withDecorView(not(is(this.myIntentRuleHome.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void postJobInvalidTitle(){
        loginAsEmployer();

        onView(withId(R.id.addButton)).perform(click());
        intended(hasComponent(JobPostingActivity.class.getName()));

        //fill in job post fields
        onView(withId(R.id.jobTitle)).perform(typeText("uichaiuciuaehcocaohcohcuehcia"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.job_type_list)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Labour"))).perform(click());
        onView(withId(R.id.location)).perform(typeText("6299 South St, Halifax, NS B3H 4R2"));
        onView(withId(R.id.hourlyWage)).perform(typeText("15.00"));
        onView(withId(R.id.duration)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.button)).perform(click());
        onView(withText(R.string.INVALID_JOBNAME)).inRoot(withDecorView(not(is(this.myIntentRuleHome.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void postJobInvalidWage(){
        loginAsEmployer();

        onView(withId(R.id.addButton)).perform(click());
        intended(hasComponent(JobPostingActivity.class.getName()));

        //fill in job post fields
        onView(withId(R.id.jobTitle)).perform(typeText("Lawn Mowing"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.job_type_list)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Labour"))).perform(click());
        onView(withId(R.id.location)).perform(typeText("6299 South St, Halifax, NS B3H 4R2"));
        onView(withId(R.id.hourlyWage)).perform(typeText("-15.00"));
        onView(withId(R.id.duration)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.button)).perform(click());
        onView(withText(R.string.INVALID_WAGE)).inRoot(withDecorView(not(is(this.myIntentRuleHome.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void postJobInvalidLocation(){
        loginAsEmployer();

        onView(withId(R.id.addButton)).perform(click());
        intended(hasComponent(JobPostingActivity.class.getName()));

        //fill in job post fields
        onView(withId(R.id.jobTitle)).perform(typeText("Lawn Mowing"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.job_type_list)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Labour"))).perform(click());
        onView(withId(R.id.location)).perform(typeText("nowhere land"));
        onView(withId(R.id.hourlyWage)).perform(typeText("15.00"));
        onView(withId(R.id.duration)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.button)).perform(click());
        onView(withText(R.string.INVALID_LOCATION)).inRoot(withDecorView(not(is(this.myIntentRuleHome.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
