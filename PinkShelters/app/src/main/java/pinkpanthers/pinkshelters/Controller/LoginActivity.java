package pinkpanthers.pinkshelters.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;

import pinkpanthers.pinkshelters.Model.Account;
import pinkpanthers.pinkshelters.Model.Admin;
import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.Homeless;
import pinkpanthers.pinkshelters.Model.Volunteer;
import pinkpanthers.pinkshelters.Model.NoSuchUserException;
import pinkpanthers.pinkshelters.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private int loginTrial = 0;
    private TextView txtView;
    private DBI db;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.name);
        password = findViewById(R.id.password);

        // set up Cancel button
        Button cancel_btn = findViewById(R.id.cancel_button);
        cancel_btn.setOnClickListener(this);
        db = new Db("pinkpanther", "PinkPantherReturns!");
    }

    /**
     * Check for user name, password and log the user in.
     * Success: Direct User to Home Page
     * Fail: Display Error
     * @param view current view
     */
    public void logIn(@SuppressWarnings("unused") View view) {
        txtView = findViewById(R.id.validationWarn);

        try {
            Editable userText = username.getText();
            String user = userText.toString();
            user = user.toLowerCase();
            Editable passText = password.getText();
            String pass = passText.toString();
            account = db.getAccountByUsername(user);
            txtView.setText("");
            String blocked = "blocked";
            String correctPass = account.getPassword();
            String accountState = account.getAccountState();
            if (correctPass.equals(pass)
                    && !accountState.equals(blocked)) { // correct password
                Context context = getApplicationContext();
                SharedPreferences preferences = context.getSharedPreferences(
                        "com.example.sp.LoginPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if (account instanceof Homeless) {
                    editor.putString("USER_TYPE", "Homeless");
                } else if (account instanceof Admin) {
                    editor.putString("USER_TYPE", "Admin");
                } else if (account instanceof Volunteer) {
                    editor.putString("USER_TYPE", "Volunteer");
                }

                editor.putString("UserID", ((Integer) account.getUserId()).toString());
                editor.putString("NAME", account.getName());
                //get name to use for shelter details
                editor.putString("USERNAME", account.getUsername());
                editor.apply();

                //active account is set to this static variable when
                // logged in for quick access to current user

//                Db.activeAccount = account;

                Intent homePageIntent = new Intent(this, HomePageActivity.class);
                homePageIntent.putExtra("username", user);
                startActivity(homePageIntent);
            } else { // incorrect password
                loginTrial++;
                checkLoginTrial();

            }
        } catch (NoSuchUserException e) {
            // User doesn't exist
            loginTrial++;
            checkLoginTrial();

        }
    }

    /**
     * When click on Cancel button, redirect back to Welcome Page
     * @param v current view
     */
    public void onClick(View v) { //cancel button
        Intent welcomeIntent = new Intent(LoginActivity.this, WelcomePageActivity.class);
        startActivity(welcomeIntent);
    }

    /**
     * Validate if a user inputs the correct values. If not, raise a warning and do certain action
     * associated with the activity
     */
    private void checkLoginTrial() {
        Button loginButton = findViewById(R.id.login_button);
        String blocked = "blocked";
        if (account != null) {
            String accountState = account.getAccountState();
            if (accountState.equals(blocked)) {
                txtView.setText("Your account has been disable, please contact admin");
                loginButton.setVisibility(View.INVISIBLE);
            } else if (loginTrial < 3) {
                txtView.setText("Wrong password, you have "
                        + (3 - loginTrial) + " tries left");
                account = null;
            } else {
                account.setAccountState(blocked);
                txtView.setText("Your account is disable due to 3 " +
                        "unsuccessful attempts, please contact your admin");
                loginButton.setVisibility(View.INVISIBLE);
                try {
                    db.updateAccount(account);
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to update account " +
                            e.toString());
                } catch (NoSuchUserException e) { // this shouldn't happen
                    txtView.setText("Your account doesn't exist");
                }
            }
        } else {
            if (loginTrial < 3) {
                txtView.setText("Wrong username, you have " + (3 - loginTrial) + " tries left");
                account = null;
            } else {
                txtView.setText("You've exceeded your attempts, please try again next time");
                loginButton.setVisibility(View.INVISIBLE);
            }
        }
    }
}