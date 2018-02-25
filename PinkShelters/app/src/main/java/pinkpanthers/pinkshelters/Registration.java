package pinkpanthers.pinkshelters;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.EditText;



import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Registration extends AppCompatActivity implements View.OnClickListener{
    private Spinner userTypes;
    private EditText name;
    private EditText email;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.titleBar);
        setSupportActionBar(toolbar);

        // set up the spinner (user type)
        List<String> legalUsers = Arrays.asList("", "Homeless", "Shelter Volunteer", "Admin");
        userTypes = findViewById(R.id.user_type_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, legalUsers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypes.setAdapter(adapter);

        //grabbing user inputs: name, email, username, password
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.pw);


        //grab the user to pass to the Home page
        /*TextView textView = (TextView)findViewById(R.id.name);
        Intent intent = new Intent( this, HomePageActivity.class );
        intent.putExtra( "userName",textView.getText().toString());
        startActivity(intent);*/
        //Grab the name to pass to Homepage
        /*Intent i = new Intent(this, HomePageActivity.class);
        TextView textView = (TextView) findViewById(R.id.name);
        textView.setText("Welcome" + name);
        String name=textView.getText().toString();
        i.putString("key1", var1);// if its string type
        startActivity(i);*/






        // set up Cancel button
        Button cancel_btn = findViewById(R.id.cancel_button);
        cancel_btn.setOnClickListener(this);
    }

    public void registerButton(View view) {
        Boolean noName, noUsername, noPass, noEmail, noType;

        String isValidName = name.getText().toString();
        TextView missingName = findViewById(R.id.missingName);
        if (isValidName.equals("")) { //missing name
            missingName.setVisibility(View.VISIBLE);
            noName = false;
        } else {
            missingName.setVisibility(View.INVISIBLE);
            noName = true;
        }

        String isValidEmail = email.getText().toString().toLowerCase();
        TextView missingEmail = findViewById(R.id.missingEmail);
        if (isValidEmail.equals("") || !isValidEmail.contains("@")) {  //missing email or "@" sign
            missingEmail.setVisibility(View.VISIBLE);
            noEmail = false;
        } else {
            missingEmail.setVisibility(View.INVISIBLE);
            noEmail = true;
        }

        String isValidUsername = username.getText().toString().toLowerCase();
        TextView missingUsername = findViewById(R.id.missingUsername);
        if (isValidUsername.length() < 6) { //username cannot be less than 6 characters
            missingUsername.setVisibility(View.VISIBLE);
            noUsername = false;
        } else {
            missingUsername.setVisibility(View.INVISIBLE);
            noUsername = true;
        }

        String isValidPassword = password.getText().toString();
        TextView missingPassword = findViewById(R.id.missingPassword);
        if (isValidPassword.length() < 6) { //password cannot be less than 6 characters
            missingPassword.setVisibility(View.VISIBLE);
            noPass = false;
        } else {
            missingPassword.setVisibility(View.INVISIBLE);
            noPass = true;
        }

        String isValidType = (String) userTypes.getSelectedItem();
        TextView missingUserType = findViewById(R.id.missingUserType);
        if (isValidType.equals("")) { // missing user type
            missingUserType.setVisibility(View.VISIBLE);
            noType = false;
        } else {
            missingUserType.setVisibility(View.INVISIBLE);
            noType = true;
        }

        Boolean missingAnything = noName && noEmail && noPass && noUsername && noType;
        if (missingAnything) {
            DBI account = new MockDB(isValidName, isValidEmail, isValidUsername, isValidPassword, isValidType);
            if (account.create()){ //if username is available or not
                Intent loginPageIntent = new Intent(this, LoginActivity.class);
                startActivity(loginPageIntent);
            } else {
                TextView duplicate = findViewById(R.id.duplicate);
                duplicate.setVisibility(View.VISIBLE);
            }
        }

    }
    public void onClick(View v) { //cancel button
        Intent welcomeIntent = new Intent(Registration.this, WelcomePageActivity.class);
        startActivity(welcomeIntent);
    }
}
