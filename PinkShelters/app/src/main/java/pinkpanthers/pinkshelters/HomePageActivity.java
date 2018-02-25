package pinkpanthers.pinkshelters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {
    TextView tv, tv1, tv2;


    public void buttonOnClick(View v) {
        Intent startMain = new Intent(this, WelcomePageActivity.class);
//        startMain.addCategory(Intent.CATEGORY_HOME);
//        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        tv=(TextView)findViewById(R.id.textView1);
        tv1=(TextView)findViewById(R.id.textView2);
        tv2=(TextView)findViewById(R.id.textView4);

        tv.setText("Hello " +getIntent().getStringExtra("NAME") + "!");
        tv1.setText("Welcome to Pink Shelter");
        tv2.setText(getIntent().getStringExtra("DESCRIPTION"));

    }
}
