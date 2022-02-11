package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginEspresso {

    @Rule
    public IntentsTestRule<HomePageActivity> myIntentRuleHome = new IntentsTestRule<>(HomePageActivity.class);

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        clearDatabase();
        System.gc();
    }

    public static void clearDatabase(){
        DatabaseReference database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child("account");
        database.removeValue();
    }

    public static void register(){
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("Tylerj"));
        onView(withId(R.id.password)).perform(typeText("Asd123456!"));
        onView(withId(R.id.registerButton)).perform(click());
        Espresso.pressBack();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.quickcash", appContext.getPackageName());
    }

    @Test
    public void checkIfRegistrationPageExist(){
        onView(withId(R.id.loginButton)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
    }

    @Test
    public void checkIfUsernameIsEmptyForEmployee(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText(""));
        onView(withId(R.id.password)).perform(typeText("Aa_123456"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_USERNAME_OR_PASSWORD)));
    }

    @Test
    public void checkIfPasswordIsEmptyForEmployee(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("taichengzz"));
        onView(withId(R.id.password)).perform(typeText(""));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_USERNAME_OR_PASSWORD)));
    }

    @Test
    public void checkIfUsernameIsEmptyForEmployer(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText(""));
        onView(withId(R.id.password)).perform(typeText("Aa_123456"));
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_USERNAME_OR_PASSWORD)));
    }

    @Test
    public void checkIfPasswordIsEmptyForEmployer(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("taichengzz"));
        onView(withId(R.id.password)).perform(typeText(""));
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_USERNAME_OR_PASSWORD)));
    }

    @Test
    public void checkIfAccountIsNotExistForEmployee(){
        register();
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("taichengzz"));
        onView(withId(R.id.password)).perform(typeText("Asd123456"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.CANNOT_FIND_ACCOUNT)));
    }

    @Test
    public void checkIfAccountIsNotExistForEmployer(){
        register();
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("taichengzz"));
        onView(withId(R.id.password)).perform(typeText("Asd123456"));
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.CANNOT_FIND_ACCOUNT)));
    }

    @Test
    public void checkIfPasswordIsWrongForEmployee(){
        register();
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("taichengzz"));
        onView(withId(R.id.password)).perform(typeText("Zxc098765"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.WRONG_PASSWORD)));
    }

    @Test
    public void checkIfPasswordIsWrongForEmployer(){
        register();
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("taichengzz"));
        onView(withId(R.id.password)).perform(typeText("Zxc098765"));
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.WRONG_PASSWORD)));
    }

    @Test
    public void checkIfLoginSuccessfulForEmployee(){
        register();
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("Tylerj"));
        onView(withId(R.id.password)).perform(typeText("Asd123456!"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        intended(hasComponent(JobSearchActivity.class.getName()));
    }

    @Test
    public void checkIfLoginSuccessfulForEmployer(){
        register();
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("Tylerj"));
        onView(withId(R.id.password)).perform(typeText("Asd123456!"));
        onView(withId(R.id.employerLoginButton)).perform(click());
        intended(hasComponent(JobSearchActivity.class.getName()));
    }
}
