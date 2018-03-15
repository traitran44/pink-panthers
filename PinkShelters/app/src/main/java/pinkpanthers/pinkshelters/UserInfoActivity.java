package pinkpanthers.pinkshelters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener, View.OnClickListener {
    private DBI db;

    private List<String> restrictionList = new ArrayList<>();
    private List<Integer> familySizeList = new ArrayList<>();

    private Button back_btn;
    private Button update_btn;

    private Spinner identity_spinner;
    private ArrayAdapter<String> identity_adapter;

    private Spinner family_spinner;
    private ArrayAdapter<Integer> family_adapter;

    private int familySize;

    private TextView name;
    private TextView email;
    private TextView buttonStatus;
    private TextView restrictionView;

    private Account account;

    private CheckBox ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8, ch9;

    //Create Back Button
    public void backOnClick(View v) {
        //passing username to intent
        Intent startMain = new Intent(this, HomePageActivity.class);
        startMain.putExtra("username", account.getUsername());
        startActivity(startMain);
    }


    //Create Update Info Button
    public void updateOnClick(View v) {
        updateRestrictionList();

        try {
            if (account instanceof Homeless) {
                Homeless homeless = (Homeless) account;
                homeless.setRestrictionsMatch(restrictionList);
                homeless.setFamilyMemberNumber(familySize);
                List<String> a = homeless.getRestrictionsMatch();
                //send that homeless to db.
                db.updateAccount(homeless);
                buttonStatus.setVisibility(View.VISIBLE);
                //show successfull text and reset everything( )
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }
    }

    /**
     * When checkbox is clicked, add restriction to list.
     */
    private void updateRestrictionList() {
        if (ch1.isChecked())
            restrictionList.add(Restrictions.MEN.toString());
        if (ch2.isChecked())
            restrictionList.add(Restrictions.WOMEN.toString());
        if (ch3.isChecked())
            restrictionList.add(Restrictions.YOUNG_ADULTS.toString());
        if (ch4.isChecked())
            restrictionList.add(Restrictions.CHILDREN.toString());
        if (ch5.isChecked())
            restrictionList.add(Restrictions.FAMILIES_W_CHILDREN_UNDER_5.toString());
        if (ch6.isChecked())
            restrictionList.add(Restrictions.NON_BINARY.toString());
        if (ch7.isChecked())
              restrictionList.add(Restrictions.FAMILY.toString());
        if (ch8.isChecked())
            restrictionList.add(Restrictions.VETERAN.toString());
        if (ch9.isChecked())
            restrictionList.add(Restrictions.FAMILIES_W_NEWBORNS.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_page);

        //set up check box restrictions
        buttonStatus = (TextView) findViewById(R.id.status);
        buttonStatus.setVisibility(View.INVISIBLE);
        ch1 =(CheckBox)findViewById(R.id.checkBox1);
        ch2 =(CheckBox)findViewById(R.id.checkBox2);
        ch3 =(CheckBox)findViewById(R.id.checkBox3);
        ch4 =(CheckBox)findViewById(R.id.checkBox4);
        ch5 =(CheckBox)findViewById(R.id.checkBox5);
        ch6 =(CheckBox)findViewById(R.id.checkBox6);
        ch7 =(CheckBox)findViewById(R.id.checkBox7);
        ch8 =(CheckBox)findViewById(R.id.checkBox8);
        ch9 =(CheckBox)findViewById(R.id.checkBox9);

        try {
            getUserAccount();
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }

        // add choices to family size
        for (int i = 0; i < 16; i++) {
            familySizeList.add(i);
        }
        family_spinner = (Spinner)findViewById(R.id.family_spinner);
        family_adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, familySizeList);
        family_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        family_spinner.setAdapter(family_adapter);

        family_spinner.setSelection(0);

        // Spinner click listener for family size
        family_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                familySize = Integer.valueOf(family_spinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing happens when nothing is selected
            }
        });

        //Grab name and user type to show in homepage
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);

        // Display name and email
        name.setText("Name: " + account.getName());
        email.setText("Email: " + account.getEmail());



    }

    /**
     * Retrieving active account
     * @throws NoSuchUserException
     */
    public void getUserAccount() throws NoSuchUserException {
        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
        String username = getIntent().getExtras().getString("username");
        account = db.getAccountByUsername(username);
    }


    @Override
    public void onClick(View view) {
    }

    @Override
    public void onItemClick(View view, int position) {
    }
}