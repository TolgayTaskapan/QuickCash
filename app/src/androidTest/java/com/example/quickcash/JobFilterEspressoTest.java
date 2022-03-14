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
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
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
public class JobFilterEspressoTest {

    private static void EmployeeAccountLogin(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("Adminmin"));
        onView(withId(R.id.password)).perform(typeText("Admin123456"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
    }

    @Rule
    public IntentsTestRule<LandingPageActivity> myIntentRuleHome = new IntentsTestRule<>(LandingPageActivity.class);

    @Test
    public void selectAllCategory(){
        EmployeeAccountLogin();
        onView(withId(R.id.categorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Category - All"))).perform(click());
        onView(withId(R.id.categorySpinner)).check(matches(withSpinnerText(containsString("Category - All"))));
    }

    @Test
    public void selectLabour(){
        EmployeeAccountLogin();
        onView(withId(R.id.categorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Labour"))).perform(click());
        onView(withId(R.id.categorySpinner)).check(matches(withSpinnerText(containsString("Labour"))));
    }

    @Test
    public void selectBabysitting(){
        EmployeeAccountLogin();
        onView(withId(R.id.categorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Babysitting"))).perform(click());
        onView(withId(R.id.categorySpinner)).check(matches(withSpinnerText(containsString("Babysitting"))));
    }

    @Test
    public void selectPetCare(){
        EmployeeAccountLogin();
        onView(withId(R.id.categorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Pet Care"))).perform(click());
        onView(withId(R.id.categorySpinner)).check(matches(withSpinnerText(containsString("Pet Care"))));
    }

    @Test
    public void selectDelivery(){
        EmployeeAccountLogin();
        onView(withId(R.id.categorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Delivery"))).perform(click());
        onView(withId(R.id.categorySpinner)).check(matches(withSpinnerText(containsString("Delivery"))));
    }

    @Test
    public void selectTechHelp(){
        EmployeeAccountLogin();
        onView(withId(R.id.categorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Tech Help"))).perform(click());
        onView(withId(R.id.categorySpinner)).check(matches(withSpinnerText(containsString("Tech Help"))));
    }
}
