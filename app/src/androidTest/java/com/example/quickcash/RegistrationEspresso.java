package com.example.quickcash;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RegistrationEspresso {

    @Rule
    public IntentsTestRule<HomePageActivity> myIntentRule = new IntentsTestRule<>(HomePageActivity.class);

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

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.quickcash", appContext.getPackageName());
    }
    /** Separate tests can pass **/
    @Test
    public void checkIfRegistrationPageExist(){
        onView(withId(R.id.signUpButton)).perform(click());
        intended(hasComponent(RegistrationPageActivity.class.getName()));
    }

    @Test
    public void checkIfUsernameIsEmpty() {
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText(""));
        onView(withId(R.id.password)).perform(typeText("Aa_123456"));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_USERNAME_OR_PASSWORD)));
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("aasd"));
        onView(withId(R.id.password)).perform(typeText(""));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_USERNAME_OR_PASSWORD)));
    }

    @Test
    public void checkIfUsernameIsInvalid() {
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("te"));
        onView(withId(R.id.password)).perform(typeText("Aa_123456"));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_USERNAME)));
    }

    @Test
    public void checkIfPasswordIsInvalid() {
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("aaatest"));
        onView(withId(R.id.password)).perform(typeText("asd"));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.INVALID_PASSWORD)));
    }

    @Test
    public void checkIfAccountWriteSuccess() {
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("aaaaaaaa"));
        onView(withId(R.id.password)).perform(typeText("Asd123456!"));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfAccountExists() {
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withId(R.id.username)).perform(typeText("Tylerj"));
        onView(withId(R.id.password)).perform(typeText("Asd123456!"));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.EMPTY_STRING)));

        //attempt to create the same user
        onView(withId(R.id.username)).perform(replaceText("Tylerj"));
        onView(withId(R.id.password)).perform(replaceText("Asd123456!"));
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.statusLabel)).check(matches(withText(R.string.USER_ALREADY_EXISTS)));
    }
}