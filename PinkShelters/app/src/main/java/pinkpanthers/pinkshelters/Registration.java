package pinkpanthers.pinkshelters;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private Spinner userTypes;
    private EditText name;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText ssn;
    public static Map<String, Account> accounts = new HashMap<>(); //stores username and account




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.titleBar);
        setSupportActionBar(toolbar);

        // set up the spinner (user type)
        List<String> legalUsers = Arrays.asList("", "Homeless", "Shelter Volunteer", "Admin");
        userTypes = (Spinner) findViewById(R.id.user_type_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, legalUsers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypes.setAdapter(adapter);

        //grabbing user inputs: name, email, username, password, and ssn
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.pw);
        ssn = findViewById(R.id.ssn);
    }

    public static Map getAccounts() {
        return accounts;
    }

    public void registerButton(View view) {
        if (!accounts.containsKey(username.getText().toString())) {
            Account acc = new Account(username.getText().toString(), password.getText().toString(),
                    "unlocked", email.getText().toString(),
                    Integer.parseInt(ssn.getText().toString()));
            accounts.put(acc.getUsername(), acc);
            Intent loginPageIntent = new Intent(this, LoginActivity.class);
            startActivity(loginPageIntent);
        } else { //username is not available

        }
    }


}
