package pinkpanthers.pinkshelters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.name);
        password = findViewById(R.id.password);



        // set up Cancel button
        Button cancel_btn = findViewById(R.id.cancel_button);
        cancel_btn.setOnClickListener(this);

        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
    }



    public void logIn(View view) {
        TextView txtView = findViewById(R.id.validationWarn);
        String user = username.getText().toString().toLowerCase();
        String pass = password.getText().toString();


        try {
            Account account = db.getAccountByUsername(user);
            if (account.getPassword().equals(pass)) { // correct password
                txtView.setVisibility(View.INVISIBLE);


                SharedPreferences preferences = getApplicationContext().getSharedPreferences("com.example.sp.LoginPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if (account instanceof Homeless) {
                    editor.putString("USER_TYPE", "Homeless");
                } else if (account instanceof Admin) {
                    editor.putString("USER_TYPE", "Admin");
                } else if (account instanceof  Volunteer) {
                    editor.putString("USER_TYPE", "Volunteer");
                }

                editor.putString("UserID", ((Integer)account.getUserId()).toString());
                editor.putString("NAME", account.getName());
                //get name to use for shelter details
                editor.putString("USERNAME", account.getUsername());
                Log.d("hihihihi",account.getUsername().toString());
                editor.commit();

                Intent homePageIntent = new Intent(this, HomePageActivity.class);
                homePageIntent.putExtra("username", user);
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