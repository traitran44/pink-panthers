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
    private DBI db;
    String description="";
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.name);
        password = findViewById(R.id.password);

        name=(EditText) findViewById(R.id.name);

        // set up Cancel button
        Button cancel_btn = findViewById(R.id.cancel_button);
        cancel_btn.setOnClickListener(this);

        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
    }



    public void logIn(View view) {
        TextView txtView = findViewById(R.id.validationWarn);
        String user = username.getText().toString().toLowerCase();
        String pass = password.getText().toString();
        String nameValue=name.getText().toString();

        try {
            Account account = db.getAccountByUsername(user);
            if (account.getPassword().equals(pass)) { // correct password
                txtView.setVisibility(View.INVISIBLE);
                //String nameValue=name.getText().toString();
                Intent homePageIntent = new Intent(this, HomePageActivity.class);
                homePageIntent.putExtra("NAME", nameValue);
                homePageIntent.putExtra("DESCRIPTION", description);
                startActivity(homePageIntent);
            } else { // incorrect password
                txtView.setVisibility(View.VISIBLE);
            }
        } catch (NoSuchUserException e) {
            // User doesn't exist
            txtView.setVisibility(View.VISIBLE);
        }
    }

    public void onClick(View v) { //cancel button
        Intent welcomeIntent = new Intent(LoginActivity.this, WelcomePageActivity.class);
        startActivity(welcomeIntent);
    }
}