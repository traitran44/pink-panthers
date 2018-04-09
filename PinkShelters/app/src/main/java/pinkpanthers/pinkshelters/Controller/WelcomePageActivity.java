package pinkpanthers.pinkshelters.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pinkpanthers.pinkshelters.R;

@SuppressWarnings("ALL")
public class WelcomePageActivity extends AppCompatActivity {

    /**
     * Display Welcome Page content
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
    }

    /**
     * Direct User to Login Page
     * @param view
     */
    public void loginButton(@SuppressWarnings("unused") View view) {
        Intent loginPageIntent = new Intent(this, LoginActivity.class);
        startActivity(loginPageIntent);
    }

    /**
     * Direct User to Register Page
     * @param view
     */
    public void registerButton(@SuppressWarnings("unused") View view) {
        Intent registerPageIntent = new Intent(this, Registration.class);
        startActivity(registerPageIntent);
    }
}
