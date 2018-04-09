package pinkpanthers.pinkshelters;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pinkpanthers.pinkshelters.Controller.Registration;
import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.NoSuchUserException;


@SuppressWarnings("ChainedMethodCall")
@RunWith(AndroidJUnit4.class)
@LargeTest


public class RegistrationActivityTest {

    // use to hook up tests with Registration activity
    @Rule
    private final ActivityTestRule<Registration> mActivityRule =
            new ActivityTestRule<>(Registration.class);


    /**
     * Helper method to delete user account from database
     */
    public void deleteUser() {
        DBI db = new Db("pinkpanther", "PinkPantherReturns!");
        try {
            db.deleteAccount("jeanuser");
        } catch (NoSuchUserException e) {
            // user has already been deleted. Continue testing
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Delete username has failed");
        }
    }

    @Test

    /**
     * test cancel registration button
     *
     */
    public void testCancelRegister() {
        // to make sure the account did not exist beforehand
        deleteUser();
        // types "jeannie" in name text box
        onView(withId(R.id.name)).perform(typeText("jeannie"), closeSoftKeyboard());
        // types "jean@gmail.com" in email text box
        onView(withId(R.id.email)).perform(typeText("jean@gmail.com"), closeSoftKeyboard());
        // types "jeanuser" in username text box
        onView(withId(R.id.username)).perform(typeText("jeanuser"), closeSoftKeyboard());
        // types "jean123" in password text box
        onView(withId(R.id.pw)).perform(typeText("jean123"), closeSoftKeyboard());
        // select Homeless for user type
        onView(withId(R.id.user_type_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Homeless"))).perform(click());

        // click on cancel button
        onView(withId(R.id.cancel_button)).perform(click());

        // currently on WelcomePageActivity

        /* attempting to login should pop up "Wrong username, you have " + (3 - loginTrial)
        + " tries left" 3 - loginTrial should equal 2 in this case since there will be only one
        wrong attempt at logging in */

        // click on login button
        onView(withId(R.id.login_button)).perform(click());

        //currently on loginActivity page
        // types "jeanuser" into username text box
        onView(withId(R.id.name)).perform(typeText("jeanuser"), closeSoftKeyboard());
        // types "jean123" into password text box
        onView(withId(R.id.password)).perform(typeText("jean123"), closeSoftKeyboard());
        // click on login button
        onView(withId(R.id.login_button)).perform(click());
        // warning should pop up
        onView(withId(R.id.validationWarn)).check(matches(withText("Wrong username, you have" +
                " 2 tries left")));
    }

    @Test
    public void testSuccessfulRegister() {
        // to make sure the account did not exist beforehand
        deleteUser();
        // types "jeannie" in name text box
        onView(withId(R.id.name)).perform(typeText("jeannie"), closeSoftKeyboard());
        // types "jean@gmail.com" in email text box
        onView(withId(R.id.email)).perform(typeText("jean@gmail.com"), closeSoftKeyboard());
        // types "jeanuser" in username text box
        onView(withId(R.id.username)).perform(typeText("jeanuser"), closeSoftKeyboard());
        // types "jean123" in password text box
        onView(withId(R.id.pw)).perform(typeText("jean123"), closeSoftKeyboard());
        // select Homeless for user type
        onView(withId(R.id.user_type_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Homeless"))).perform(click());

        // click on register button
        onView(withId(R.id.register_button)).perform(click());

        /* Successfully registered and will attempt to login which should be successful*/

        // currently on loginActivity page
        // types "jeanuser" into username text box
        onView(withId(R.id.name)).perform(typeText("jeanuser"), closeSoftKeyboard());
        // types "jean123" into password text box
        onView(withId(R.id.password)).perform(typeText("jean123"), closeSoftKeyboard());
        // click on login button
        onView(withId(R.id.login_button)).perform(click());

        // currently on HomePageActivity
        // checks that Hello message is correct: "Hello name!"
        onView(withId(R.id.textView1)).check(matches(withText("Hello jeannie!")));
        // checks that the usertype text box is the correct user type: Homeless
        onView(withId(R.id.textView3)).check(matches(withText("Homeless")));

        // logout to return to WelcomePage activity
        onView(withId(R.id.logout_btn)).perform(click());
        // currently on WelcomePageActivity
        // click on register button for the rest of the tests
        onView(withId(R.id.register_button)).perform(click());
    }

    @Test
    public void testMissingName() {
        // does not fill out name text box
        // types "jean@gmail.com" in email text box
        onView(withId(R.id.email)).perform(typeText("jean@gmail.com"), closeSoftKeyboard());
        // types "jeanuser" in username text box
        onView(withId(R.id.username)).perform(typeText("jeanuser"), closeSoftKeyboard());
        // types "jean123" in password text box
        onView(withId(R.id.pw)).perform(typeText("jean123"), closeSoftKeyboard());
        // select Homeless for user type
        onView(withId(R.id.user_type_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Homeless"))).perform(click());

        // click on register button
        onView(withId(R.id.register_button)).perform(click());

        // missingName text box should be visible
        onView(withId(R.id.missingName)).check(matches(isDisplayed()));
        //nothing else is missing
        onView(withId(R.id.missingEmail)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingUsername)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingPassword)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingUserType)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testMissingEmail() {
        // types "jeannie" in name text box
        onView(withId(R.id.name)).perform(typeText("jeannie"), closeSoftKeyboard());
        // does not fill out email text box
        // types "jeanuser" in username text box
        onView(withId(R.id.username)).perform(typeText("jeanuser"), closeSoftKeyboard());
        // types "jean123" in password text box
        onView(withId(R.id.pw)).perform(typeText("jean123"), closeSoftKeyboard());
        // select Homeless for user type
        onView(withId(R.id.user_type_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Homeless"))).perform(click());

        // click on register button
        onView(withId(R.id.register_button)).perform(click());

        // missingEmail text box should be visible
        onView(withId(R.id.missingEmail)).check(matches(isDisplayed()));
        // nothing else is missing
        onView(withId(R.id.missingName)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingUsername)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingPassword)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingUserType)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testMissingUsername() {
        // types "jeannie" in name text box
        onView(withId(R.id.name)).perform(typeText("jeannie"), closeSoftKeyboard());
        // types "jean@gmail.com" in email text box
        onView(withId(R.id.email)).perform(typeText("jean@gmail.com"), closeSoftKeyboard());
        // does not fill out username
        // types "jean123" in password text box
        onView(withId(R.id.pw)).perform(typeText("jean123"), closeSoftKeyboard());
        // select Homeless for user type
        onView(withId(R.id.user_type_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Homeless"))).perform(click());

        // click on register button
        onView(withId(R.id.register_button)).perform(click());

        // missingUsername text box should be visible
        onView(withId(R.id.missingUsername)).check(matches(isDisplayed()));
        // nothing else is missing
        onView(withId(R.id.missingName)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingEmail)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingPassword)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingUserType)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testMissingPassword() {
        // types "jeannie" in name text box
        onView(withId(R.id.name)).perform(typeText("jeannie"), closeSoftKeyboard());
        // types "jean@gmail.com" in email text box
        onView(withId(R.id.email)).perform(typeText("jean@gmail.com"), closeSoftKeyboard());
        // types "jeanuser" in username text box
        onView(withId(R.id.username)).perform(typeText("jeanuser"), closeSoftKeyboard());
        // does not fill out password
        // select Homeless for user type
        onView(withId(R.id.user_type_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Homeless"))).perform(click());

        // click on register button
        onView(withId(R.id.register_button)).perform(click());

        // missingPassword text box should be visible
        onView(withId(R.id.missingPassword)).check(matches(isDisplayed()));
        // nothing else is missing
        onView(withId(R.id.missingName)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingEmail)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingUsername)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingUserType)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testMissingUserType() {
        // types "jeannie" in name text box
        onView(withId(R.id.name)).perform(typeText("jeannie"), closeSoftKeyboard());
        // types "jean@gmail.com" in email text box
        onView(withId(R.id.email)).perform(typeText("jean@gmail.com"), closeSoftKeyboard());
        // types "jeanuser" in username text box
        onView(withId(R.id.username)).perform(typeText("jeanuser"), closeSoftKeyboard());
        // types "jean123" in password text box
        onView(withId(R.id.pw)).perform(typeText("jean123"), closeSoftKeyboard());
        // does not chose a user type

        // click on register button
        onView(withId(R.id.register_button)).perform(click());

        // missingUserType text box should be visible
        onView(withId(R.id.missingUserType)).check(matches(isDisplayed()));
        // nothing else is missing
        onView(withId(R.id.missingName)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingEmail)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingUsername)).check(matches(not(isDisplayed())));
        onView(withId(R.id.missingPassword)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testMissingEverything() {
        // does not fill out name
        // does not fill out email
        // does not fill out username
        // does not fill out password
        // does not chose a user type

        // click on register button
        onView(withId(R.id.register_button)).perform(click());

        // missingName text box should be visible
        onView(withId(R.id.missingName)).check(matches(isDisplayed()));
        // missingEmail text box should be visible
        onView(withId(R.id.missingEmail)).check(matches(isDisplayed()));
        // missingUsername text box should be visible
        onView(withId(R.id.missingUsername)).check(matches(isDisplayed()));
        // missingPassword text box should be visible
        onView(withId(R.id.missingPassword)).check(matches(isDisplayed()));
        // missingUserType text box should be visible
        onView(withId(R.id.missingUserType)).check(matches(isDisplayed()));

    }

    @Test
    public void testDuplicateUsername() {
        // creates account
        testSuccessfulRegister();
        // tries to create another account with same username
        // types "jeannie" in name text box
        onView(withId(R.id.name)).perform(typeText("jeannie"), closeSoftKeyboard());
        // types "jean@gmail.com" in email text box
        onView(withId(R.id.email)).perform(typeText("jean@gmail.com"), closeSoftKeyboard());
        // types "jeanuser" in username text box
        onView(withId(R.id.username)).perform(typeText("jeanuser"), closeSoftKeyboard());
        // types "jean123" in password text box
        onView(withId(R.id.pw)).perform(typeText("jean123"), closeSoftKeyboard());
        // select Homeless for user type
        onView(withId(R.id.user_type_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Homeless"))).perform(click());

        // click on register button
        onView(withId(R.id.register_button)).perform(click());

        // duplicate text box should be visible
        onView(withId(R.id.duplicate)).check(matches(isDisplayed()));
    }

}
