package pinkpanthers.pinkshelters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
    }



    public void logIn(View view) {
        if (email.getText().toString().equals("user") //correct username
                && password.getText().toString().equals("pass")) { //correct password
            Intent homePageIntent = new Intent(this, HomePageActivity.class);
            startActivity(homePageIntent);
        } else { // incorrect password or username
        

        }

    }
}
