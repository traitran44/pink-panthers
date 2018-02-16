package pinkpanthers.pinkshelters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private Spinner userTypes;
    private Button cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);

        // set up Cancel button
        Button cancel_btn = (Button) findViewById(R.id.cancel_button);
        cancel_btn.setOnClickListener(this);
    }



    public void logIn(View view) {
        TextView txtView = (TextView) findViewById(R.id.validationWarn);
        if (username.getText().toString().equals("user") //correct username
                && password.getText().toString().equals("pass")) { //correct password
            txtView.setVisibility(View.INVISIBLE);
            Intent homePageIntent = new Intent(this, HomePageActivity.class);
            startActivity(homePageIntent);
        } else { // incorrect password or username
            txtView.setVisibility(View.VISIBLE);
        }
    }

    public void onClick(View v) {
        Intent welcomeIntent = new Intent(LoginActivity.this, WelcomePageActivity.class);
        startActivity(welcomeIntent);
    }
}
