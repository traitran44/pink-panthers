package pinkpanthers.pinkshelters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class WelcomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
    }
    public void login(View view) {
        Intent loginPageIntent = new Intent(this, LoginActivity.class);
        startActivity(loginPageIntent);
    }
}
