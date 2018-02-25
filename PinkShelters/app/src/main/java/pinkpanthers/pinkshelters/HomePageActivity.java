package pinkpanthers.pinkshelters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {
    TextView tv, tv1, tv2;
    //button to view Shelter details
    Button GoToShelterDetails;


    public void buttonOnClick(View v) { //logout button
        Intent startMain = new Intent(this, WelcomePageActivity.class);
        startActivity(startMain);
    }

    public void shelterListButton(View v) {
        Intent shelterListIntent = new Intent(this, ListOfSheltersActivity.class);
//        detailPageIntent.putExtra("shelterId", 0);
        startActivity(shelterListIntent);
     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
         //button to go to Shelter Details
        GoToShelterDetails=(Button) findViewById(R.id.viewshelter_btn);
        GoToShelterDetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Intent code for open view shelter details
                //replace welcome activity with shelter activity

                Intent intent = new Intent(HomePageActivity.this, WelcomePageActivity.class);
                startActivity(intent);

            }
        });

        tv = (TextView) findViewById(R.id.textView1);
        tv1 = (TextView) findViewById(R.id.textView2);
        tv2 = (TextView) findViewById(R.id.textView4);

        tv.setText("Hello " + getIntent().getStringExtra("NAME") + "!");
        tv1.setText("Welcome to Pink Shelter");
        //getIntent or get HomepageIntent
        tv2.setText(getIntent().getStringExtra("DESCRIPTION"));

    }
}

