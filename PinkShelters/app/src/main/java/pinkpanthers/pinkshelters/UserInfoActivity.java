package pinkpanthers.pinkshelters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener, View.OnClickListener {
    private DBI db;

    private List<String> restrictionList = new ArrayList<>();
    private List<String> familySizeList = new ArrayList<>();

    private Button back_btn;
    private Button update_btn;

    private Spinner identity_spinner;
    private ArrayAdapter<String> identity_adapter;

    private Spinner family_spinner;
    private ArrayAdapter<String> family_adapter;

    private String restriction;
    private String familySize;

    private TextView name;
    private TextView email;
    private TextView buttonStatus;
    private TextView restrictionView;

    private Account account;

    private CheckBox ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8, ch9;

    public static final String PREFS_NAME = "com.example.sp.LoginPrefs";

    public void resetAllFields() {
        buttonStatus.setVisibility(View.INVISIBLE);
        family_spinner.setSelection(0);
    }

    //Create Back Button
    public void backOnClick(View v) {
        Intent startMain = new Intent(this, HomePageActivity.class);
        startMain.putExtra("username", account.getName());
        startActivity(startMain);
    }


    //Create Update Info Button
    public void updateOnClick(View v) {
        updateRestrictionList();

        for (String s : restrictionList) {
            System.out.println(s + ", ");
        }
        try {
        // TODO: Uncomment this line below when you fix updateAccountInformationById's param to take a list.
            db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
            db.updateAccount(this.account);
            buttonStatus.setVisibility(View.VISIBLE);
            resetAllFields();
            System.out.println("Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }
    }

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

        //check box restrictions:
        buttonStatus = findViewById(R.id.buttonStatus);
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
            familySizeList.add(i + "");
        }
        family_spinner = (Spinner)findViewById(R.id.family_spinner);
        family_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, familySizeList);
        family_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        family_spinner.setAdapter(family_adapter);

        family_spinner.setSelection(0);

        // Spinner click listener for family size
        family_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                familySize = family_spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing happens when nothing is selected
            }
        });

        //Grab name and user type to show in homepage
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);

        // Get name and user type
        name.setText("Name: " + account.getName());
        email.setText("Email: " + account.getEmail());



        //check box restrictions:


    }


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

    private String sqlConverter(String chosenItem) {
        switch (chosenItem) {
            case ("Men"):
                return Restrictions.MEN.toString();
            case ("Non-Binary"):
                return Restrictions.NON_BINARY.toString();
            case ("Women"):
                return Restrictions.WOMEN.toString();
            case ("Children"):
                return Restrictions.CHILDREN.toString();
            case ("Young Adults"):
                return Restrictions.YOUNG_ADULTS.toString();
            case ("Families with Newborns"):
                return Restrictions.FAMILIES_W_NEWBORNS.toString();
            case ("Anyone"):
                return Restrictions.ANYONE.toString();
            default:
                return "None";
        }
    }
}
