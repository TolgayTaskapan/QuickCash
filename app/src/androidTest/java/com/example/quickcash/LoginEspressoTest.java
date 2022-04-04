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
import static org.junit.Assert.assertEquals;

import android.content.Context;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.quickcash.account.LoginActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginEspressoTest {

    @Rule
    public IntentsTestRule<LandingPageActivity> myIntentRuleHome = new IntentsTestRule<>(LandingPageActivity.class);

    @BeforeClass
    public static void setup() {
    }

    @AfterClass
    public static void tearDown() {
        //clearDatabase();
        System.gc();
    }

    public static void clearDatabase(){
        DatabaseReference database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child("Account");
        database.removeValue();
    }

    public static void registerAsEmployer(String username){
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText(username));
        onView(withId(R.id.password)).perform(typeText("Asd123456!"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.identitySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employer"))).perform(click());
        onView(withId(R.id.registerButton)).perform(click());
        Espresso.pressBack();
    }

    public static void registerAsEmployee(String username){
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText(username));
        onView(withId(R.id.password)).perform(typeText("Asd123456!"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.identitySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
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
        String username = "Tylerj";
        registerAsEmployee(username);
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("taichengzz"));
        onView(withId(R.id.password)).perform(typeText("Asd123456"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.USER_DOES_NOT_EXIST)));
    }

    @Test
    public void checkIfAccountIsNotExistForEmployer(){
        String username = "Tylerj";
        registerAsEmployer(username);
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("taichengzz"));
        onView(withId(R.id.password)).perform(typeText("Asd123456"));
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.USER_DOES_NOT_EXIST)));
    }

    @Test
    public void checkIfPasswordIsWrongForEmployee(){
        String username = "taichengzz";
        registerAsEmployee(username);
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("taichengzz"));
        onView(withId(R.id.password)).perform(typeText("Zxc098765"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INCORRECT_PASSWORD)));
    }

    @Test
    public void checkIfPasswordIsWrongForEmployer(){
        String username = "taichengzz";
        registerAsEmployer(username);
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("taichengzz"));
        onView(withId(R.id.password)).perform(typeText("Zxc098765"));
        onView(withId(R.id.employerLoginButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INCORRECT_PASSWORD)));
    }

    @Test
    public void checkIfLoginSuccessfulForEmployee(){
        String username = "Tylerj";
        registerAsEmployee(username);
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("Tylerj"));
        onView(withId(R.id.password)).perform(typeText("Asd123456!"));
        onView(withId(R.id.employeeLoginButton)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void checkIfLoginSuccessfulForEmployer(){
        String username = "Tylerj";
        registerAsEmployer(username);
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("Tylerj"));
        onView(withId(R.id.password)).perform(typeText("Asd123456!"));
        onView(withId(R.id.employerLoginButton)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }
}
