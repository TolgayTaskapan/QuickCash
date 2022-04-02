package com.example.quickcash;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.quickcash.util.UserSession;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Rule;
import org.junit.Test;

public class UserPreferenceTest {

    @Rule
    public IntentsTestRule<LandingPageActivity> mLandingPageRule = new IntentsTestRule<>(LandingPageActivity.class);

    private static final String EMPLOYEE = "Employee";
    private static final String EMPLOYER = "Employer";

    private static void EmployeeAccountLogin(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("TylerJ"));
        onView(withId(R.id.password)).perform(typeText("Johnson2021!"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
    }

    private static void EmployerAccountLogin(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("TylerJ"));
        onView(withId(R.id.password)).perform(typeText("Johnson2021!"));
        onView(withId(R.id.employerLoginButton)).perform(click());
    }

    @Test
    public void updatePreferenceToAll_Employee() {
        EmployeeAccountLogin();
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.UpdateProfile)).perform(click());
        onView(withId(R.id.prefer_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Category - All"))).perform(click());
        onView(withId(R.id.updateFinishButton)).perform(click());
        onView(withText(R.string.JOB_UPDATE_SUCCESS)).inRoot(withDecorView(not(mLandingPageRule.getActivity().getWindow().getDecorView()))).check(doesNotExist());
    }

    @Test
    public void updatePreferenceToCategory_Employee() {
        EmployerAccountLogin();
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.UpdateProfile)).perform(click());
        onView(withId(R.id.prefer_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Labour"))).perform(click());
        onView(withId(R.id.updateFinishButton)).perform(click());
        onView(withText(R.string.JOB_UPDATE_SUCCESS)).inRoot(withDecorView(not(mLandingPageRule.getActivity().getWindow().getDecorView()))).check(doesNotExist());
    }

    @Test
    public void updatePreferenceToAll_Employer() {
        EmployerAccountLogin();
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.UpdateProfile)).perform(click());
        onView(withId(R.id.prefer_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Category - All"))).perform(click());
        onView(withId(R.id.updateFinishButton)).perform(click());
        onView(withText(R.string.JOB_UPDATE_SUCCESS)).inRoot(withDecorView(not(mLandingPageRule.getActivity().getWindow().getDecorView()))).check(doesNotExist());
    }

    @Test
    public void updatePreferenceToCategory_Employer() {
        EmployerAccountLogin();
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.UpdateProfile)).perform(click());
        onView(withId(R.id.prefer_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Labour"))).perform(click());
        onView(withId(R.id.updateFinishButton)).perform(click());
        onView(withText(R.string.JOB_UPDATE_SUCCESS)).inRoot(withDecorView(not(mLandingPageRule.getActivity().getWindow().getDecorView()))).check(doesNotExist());
    }
}
