package pinkpanthers.pinkshelters.Controller;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import pinkpanthers.pinkshelters.Model.Account;
import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.Homeless;
import pinkpanthers.pinkshelters.Model.Shelter;
import pinkpanthers.pinkshelters.Model.NoSuchUserException;
import pinkpanthers.pinkshelters.R;

public class HomePageActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "com.example.sp.LoginPrefs";
    private String username;
    private Account user;
    private DBI db;


    public void buttonOnClick(View v) { //logout button
        Db.activeAccount = null;
        Intent startMain = new Intent(this, WelcomePageActivity.class);
        startActivity(startMain);
    }

    public void shelterListButton(View v) { //View Shelter button
        Intent shelterListIntent = new Intent(this, ListOfSheltersActivity.class);
        shelterListIntent.putExtra("username", username);
        startActivity(shelterListIntent);
    }

    public void infoOnClick(View v) { //View/Edit User Info button
        Intent info = new Intent(this, UserInfoActivity.class);
        info.putExtra("username", username);
        startActivity(info);
    }

    public void setShelterText() {
        TextView message = findViewById(R.id.shelterMessage);
        if (user instanceof Homeless) {
            try {
                Shelter shelter = db.getShelterById(((Homeless) user).getShelterId());
                String bed = ((Homeless) user).getFamilyMemberNumber() == 1 ? " bed" : " beds";
                message.setText("You have claim " + ((Homeless) user).getFamilyMemberNumber() + bed +
                        " at shelter: " + shelter.getShelterName());
            } catch (NoSuchUserException e) {
                message.setText("You have not claimed any bed yet");
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //Grab name and user type to show in homepage
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREFS_NAME, Registration.MODE_PRIVATE);
        TextView textUserType = findViewById(R.id.textView3);
        TextView textName = findViewById(R.id.textView1);
        TextView textWelcome = findViewById(R.id.textView2);


        //Get name and user type
        String prefName = preferences.getString("NAME", "");
        String prefUserType = preferences.getString("USER_TYPE", "");


        textName.setText("Hello " + prefName + "!");
        textWelcome.setText("Welcome to Pink Shelter");
        textUserType.setText(prefUserType);

        username = getIntent().getExtras().getString("username");
        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
        try {
            user = db.getAccountByUsername(username);
        } catch (NoSuchUserException e) {
            throw new RuntimeException("cannot find the account");
        }
        if (user instanceof Homeless) {
            setShelterText();
        }

    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}