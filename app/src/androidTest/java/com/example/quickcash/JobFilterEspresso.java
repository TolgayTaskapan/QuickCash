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
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.quickcash.identity.Employee;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class JobFilterEspresso {

    private static DatabaseReference db;

    @BeforeClass
    public static void setup() {
        db = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child("job");
        EmployeeAccountLogin();
        postJobs();
    }

    @AfterClass
    public static void tearDown() {
        clearDatabase();
        System.gc();
    }

    private static void clearDatabase() {
        db.removeValue();
    }

    private static void EmployeeAccountLogin(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("Adminmin"));
        onView(withId(R.id.password)).perform(typeText("Admin123456"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
    }

    private static void postJobs(){
        onView(withId(R.id.addButton)).perform(click());
        intended(hasComponent(JobPostingActivity.class.getName()));

        onView(withId(R.id.jobTitle)).perform(typeText("Clean"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.job_type_list)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Labour"))).perform(click());
        onView(withId(R.id.location)).perform(typeText("5633 Fenwick St, Halifax, NS B3H 4M2"));
        onView(withId(R.id.hourlyWage)).perform(typeText("02.00"));
        onView(withId(R.id.duration)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button)).perform(click());
    }

    @Rule
    public ActivityTestRule<LandingPageActivity> activityTestRule = new ActivityTestRule<>(LandingPageActivity.class);

    @Test
    public void listHasJob(){
        onView(withId(R.id.categorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Labour"))).perform(click());
        onView(withText(R.string.EMPTY_LIST)).inRoot(withDecorView(not(is(this.activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(not(isDisplayed())));
    }

    @Test
    public void listHasNoJob(){
        onView(withId(R.id.categorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Babysitting"))).perform(click());
        onView(withText(R.string.EMPTY_LIST)).inRoot(withDecorView(not(is(this.activityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
