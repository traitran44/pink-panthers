package pinkpanthers.pinkshelters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Homeless homeless;
    private List<CheckBox> checkBoxList;
    List<Restrictions> enums;

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
        //require homeless to select at least one restriction to update
        if (restrictionList.isEmpty()) {
            buttonStatus.setText("Please select restriction(s) to update");
            buttonStatus.setVisibility(View.VISIBLE);

        } else {
            try {
                homeless.setRestrictionsMatch(restrictionList);
                homeless.setFamilyMemberNumber(familySize);

                //checked if homeless has already claimed beds
                if (homeless.getShelterId() != 0 && db.getShelterById(homeless.getShelterId()) != null) {
                    Shelter shelter = db.getShelterById(homeless.getShelterId());
                    //create dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Confirm cancellation to update info");
                    builder.setMessage("Are you sure to cancel bed(s) claimed at" + shelter.getShelterName() + "?");
                    //if homeless presses on YES, then cancel claimed beds
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Shelter shelter = db.getShelterById(homeless.getShelterId());
                                int vacancy = shelter.getVacancy() + familySize;
                                int occupancy = shelter.getUpdate_capacity() - vacancy;
                                homeless.setShelterId(0);
                                db.updateShelterOccupancy(shelter.getId(), occupancy);
                                //updateRestrictionList();

                                if (restrictionList.isEmpty()) {
                                    buttonStatus.setText("Please select restriction(s) to update");
                                    buttonStatus.setVisibility(View.VISIBLE);
                                } else {
                                    List<String> a = homeless.getRestrictionsMatch();
                                    //send that homeless to db.
                                    db.updateAccount(homeless);
                                    //show successful text and reset everything()
                                    buttonStatus.setText("Update successfully");
                                    buttonStatus.setVisibility(View.VISIBLE);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (NoSuchUserException e) {
                                e.printStackTrace();
                            }
                            dialog.cancel();
                        }
                    });
                    //if homeless presses NO then nothing happens
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            restrictionList.clear();
                            finish();
                            startActivity(getIntent());
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    //if homeless has not claimed any beds yet, then update info
                } else {
                    List<String> a = homeless.getRestrictionsMatch();
                    //send that homeless to db.
                    db.updateAccount(homeless);
                    //show successful text and reset everything()
                    buttonStatus.setText("Update successfully");
                    buttonStatus.setVisibility(View.VISIBLE);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NoSuchUserException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * When checkbox is clicked, add restriction to list.
     */
    private void updateRestrictionList() {

        for (int i = 0; i < checkBoxList.size(); i++) {
            if (checkBoxList.get(i).isChecked()) {
                restrictionList.add(enums.get(i).toString());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_page);

        //set up check box restrictions
        buttonStatus = (TextView) findViewById(R.id.status);
        buttonStatus.setVisibility(View.INVISIBLE);

        List<String> currentRestrictionList;
        checkBoxList = new ArrayList<CheckBox>();
        checkBoxList.add((CheckBox) findViewById(R.id.checkBox1));
        checkBoxList.add((CheckBox) findViewById(R.id.checkBox2));
        checkBoxList.add((CheckBox) findViewById(R.id.checkBox3));
        checkBoxList.add((CheckBox) findViewById(R.id.checkBox4));
        checkBoxList.add((CheckBox) findViewById(R.id.checkBox5));
        checkBoxList.add((CheckBox) findViewById(R.id.checkBox6));
        checkBoxList.add((CheckBox) findViewById(R.id.checkBox7));
        checkBoxList.add((CheckBox) findViewById(R.id.checkBox8));
        checkBoxList.add((CheckBox) findViewById(R.id.checkBox9));
        enums = Arrays.asList(Restrictions.values());


        try {
            getUserAccount();
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }

        if (account instanceof Homeless) {
            homeless = (Homeless) account;

            currentRestrictionList = ((Homeless) account).getRestrictionsMatch();
            for (int i = 0; i < currentRestrictionList.size(); i++) {
                for (int j = 0; j < checkBoxList.size(); j++) {
                    if (!checkBoxList.get(j).isChecked()) {
                        boolean isCheck = checkCheckbox(currentRestrictionList.get(i), enums.get(j).toString());
                        checkBoxList.get(j).setChecked(isCheck);
                    }
                }

            }
        }


        // add choices to family size
        for (int i = 1; i < 16; i++) {
            familySizeList.add(i);
        }
        family_spinner = (Spinner) findViewById(R.id.family_spinner);
        family_adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, familySizeList);
        family_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        family_spinner.setAdapter(family_adapter);


        family_spinner.setSelection(homeless.getFamilyMemberNumber() - 1);

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

    private boolean checkCheckbox(String homelessRestriction, String shelterRestriction) {
        return homelessRestriction.equals(shelterRestriction);
    }

    /**
     * Retrieving active account
     *
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