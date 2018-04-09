package pinkpanthers.pinkshelters.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pinkpanthers.pinkshelters.R;

/**
 * to create a welcome page that allows users to either log in or register
 */
public class WelcomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
    }

    /**
     * Direct User to Login Page
     * @param view the current view that holds the log-in button
     */
    public void loginButton(@SuppressWarnings("unused") View view) {
        Intent loginPageIntent = new Intent(this, LoginActivity.class);
        startActivity(loginPageIntent);
    }

    /**
     * Direct User to Register Page
     * @param view the current view that hold the register button
     */
    public void registerButton(@SuppressWarnings("unused") View view) {
        Intent registerPageIntent = new Intent(this, Registration.class);
        startActivity(registerPageIntent);
    }
}
