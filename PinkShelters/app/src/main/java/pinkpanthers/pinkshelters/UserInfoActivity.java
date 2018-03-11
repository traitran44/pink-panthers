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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener, View.OnClickListener {
    private DBI db;

    private List<String> identityList = new ArrayList<>();
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

    public static final String PREFS_NAME = "com.example.sp.LoginPrefs";

    public void resetAllFields() {
        buttonStatus.setVisibility(View.INVISIBLE);
        identity_spinner.setSelection(0);
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
        try {
            db.updateAccountInformationById(account.getUserId(), restriction);
            db.updateAccountInformationById(account.getUserId(), familySize);
            buttonStatus.setVisibility(View.VISIBLE);
            System.out.println("Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_page);
        buttonStatus = findViewById(R.id.buttonStatus);

        try {
            getUserAccount();
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }

        identityList.add("No restriction");
        identityList.add("Adult male only");
        identityList.add("Adult female only");
        identityList.add("Veterans only");
        identityList.add("Non-Binary only");
        identityList.add("Young adult only");
        identityList.add("Children only (age 5 - 13)");
        identityList.add("Family with children (age 5 - 13)");
        identityList.add("Family with child(ren) under 5");
        identityList.add("Family with newborn(s)");
        identityList.add("Family");

        identity_spinner = (Spinner)findViewById(R.id.identity_spinner);
        identity_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, identityList);
        identity_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        identity_spinner.setAdapter(identity_adapter);
        identity_spinner.setSelection(0);


        // add choices to family size
        for (int i = 0; i < 16; i++) {
            familySizeList.add(i + "");
        }
        family_spinner = (Spinner)findViewById(R.id.family_spinner);
        family_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, familySizeList);
        family_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        family_spinner.setAdapter(family_adapter);

        family_spinner.setSelection(0);

        // Spinner click listener
        family_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                familySize = identity_spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing happens when nothing is selected
            }
        });

        identity_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = identity_spinner.getSelectedItem().toString();

                switch(selected) {
                    case "No restriction" :
                        restriction = (Restrictions.ANYONE.toString());
                        break;
                    case "Adult male only" :
                        restriction = (Restrictions.MEN.toString());
                        break;
                    case "Adult female only" :
                        restriction = (Restrictions.WOMEN.toString());
                        break;
                    case "Non-binary adult only" :
                        restriction = (Restrictions.NON_BINARY.toString());
                        break;
                    case "Young adult only (age 13 - 18)" :
                        restriction = (Restrictions.YOUNG_ADULTS.toString());
                        break;
                    case "Children only (age under 13)" :
                        restriction = (Restrictions.CHILDREN.toString());
                        break;
                    case "Family with child(ren) under 5" :
                        restriction = (Restrictions.FAMILIES_W_CHILDREN_UNDER_5.toString());
                        break;
                    case "Family with newborn(s)" :
                        restriction = (Restrictions.FAMILIES_W_NEWBORNS.toString());
                        break;
                    case "Family" :
                        restriction = (Restrictions.FAMILY.toString());
                        break;
                    case "Veterans only" :
                        restriction = (Restrictions.VETERAN.toString());
                        break;
                    default :
                        restriction = (Restrictions.ANYONE.toString());
                }


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
