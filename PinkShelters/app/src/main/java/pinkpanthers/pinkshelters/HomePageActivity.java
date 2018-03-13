package pinkpanthers.pinkshelters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;


public class HomePageActivity extends AppCompatActivity {
    TextView textName, textWelcome, textUserType;
    SharedPreferences preferences ;
    public static final String PREFS_NAME = "com.example.sp.LoginPrefs";
    //button to view Shelter details
    Button GoToShelterDetails;


    public void buttonOnClick(View v) { //logout button
        Intent startMain = new Intent(this, WelcomePageActivity.class);
        startActivity(startMain);
    }

    public void shelterListButton(View v) { //View Shelter button
        Intent shelterListIntent = new Intent(this, ListOfSheltersActivity.class);
//        detailPageIntent.putExtra("shelterId", 0);
        startActivity(shelterListIntent);
    }

    public void infoOnClick(View v) { //View/Edit User Info button
        Intent info = new Intent(this, UserInfoActivity.class);
        String username = getIntent().getExtras().getString("username");

        //passing username to intent
        info.putExtra("username", username);
        startActivity(info);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //Grab name and user type to show in homepage
        preferences = getApplicationContext().getSharedPreferences(PREFS_NAME, Registration.MODE_PRIVATE);
        textUserType = (TextView) findViewById(R.id.textView3);
        textName = (TextView) findViewById(R.id.textView1);
        textWelcome = (TextView) findViewById(R.id.textView2);


        //Get name and user type
        String prefName=preferences.getString("NAME", "");
        String prefUserType=preferences.getString("USER_TYPE", "");
        String userName=preferences.getString("USERNAME", "");


        textName.setText("Hello " + prefName + "!");
        textWelcome.setText("Welcome to Pink Shelter");
        textUserType.setText(prefUserType);

        //button to go to Shelter Details
        GoToShelterDetails=(Button) findViewById(R.id.viewshelter_btn);
        GoToShelterDetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Intent code for open view shelter details
                //replace welcome activity with shelter activity
                Intent intent = new Intent(HomePageActivity.this, ListOfSheltersActivity.class);
                startActivity(intent);

            }
        });

    }
}