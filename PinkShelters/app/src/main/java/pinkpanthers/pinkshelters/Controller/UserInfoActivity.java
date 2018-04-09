package pinkpanthers.pinkshelters.Controller;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.Homeless;
import pinkpanthers.pinkshelters.Model.NoSuchUserException;
import pinkpanthers.pinkshelters.Model.Restrictions;
import pinkpanthers.pinkshelters.Model.Shelter;
import pinkpanthers.pinkshelters.Model.Account;

import pinkpanthers.pinkshelters.R;


/**
 * to create a view that allows users to update their information
 */
@SuppressWarnings("RedundantCast")
public class UserInfoActivity extends AppCompatActivity implements
        RecyclerAdapter.ItemClickListener, View.OnClickListener {
    private DBI db;

    private final List<String> restrictionList = new ArrayList<>();
    private final List<Integer> familySizeList = new ArrayList<>();

    private Spinner family_spinner;

    private int familySize;

    private TextView buttonStatus;

    private Account account;
    private Homeless homeless;
    private List<CheckBox> checkBoxList;
    private List<Restrictions> enums;
    private int familySizeSpinner;
    static final int familySizeSpinnerChoice=16;


    /**
     * set Click Event for Back Button
     * Direct to Start Main
     *
     * @param v current view that holds the back button
     */
    public void backOnClick(@SuppressWarnings("unused") View v) {
        //passing username to intent
        Intent startMain = new Intent(this, HomePageActivity.class);
        startMain.putExtra("username", account.getUsername());
        startActivity(startMain);
    }


    /**
     * Set Click Event Update Info Button
     * Update the information of the user when click
     *
     * @param v current view that holds the update button
     */
    @SuppressWarnings("FeatureEnvy")
    public void updateOnClick(@SuppressWarnings("unused") View v) {
        updateRestrictionList();
        //require homeless to select at least one restriction to update
        if (restrictionList.isEmpty()) {
            buttonStatus.setText("Please select restriction(s) to update");
            buttonStatus.setVisibility(View.VISIBLE);
            finish();
            startActivity(getIntent());

        } else {
            try {
                homeless.setRestrictionsMatch(restrictionList);
                homeless.setFamilyMemberNumber(familySize);

                //checked if homeless has already claimed beds
                if ((homeless.getShelterId() != 0)
                        && (db.getShelterById(homeless.getShelterId()) != null)) {
                    Shelter shelter = db.getShelterById(homeless.getShelterId());
                    //create dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Confirm cancellation to update info");
                    builder.setMessage("Are you sure to cancel bed(s) claimed at"
                            + shelter.getShelterName() + "?");
                    //if homeless presses on YES, then cancel claimed beds
                    //noinspection FeatureEnvy,FeatureEnvy
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @SuppressWarnings("LawOfDemeter")
                        @Override


                        /**
                         * to create a listener for item that gets clicked on
                         * @param dialog the dialog that this button holds
                         * @param which the position of the item
                         */
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Shelter shelter = db.getShelterById(homeless.getShelterId());
                                int vacancyGetter=shelter.getVacancy();
                                int vacancy = vacancyGetter + familySize;
                                int occupancy=shelter.getUpdate_capacity();
                                int occupancyUpdate = occupancy - vacancy;
                                homeless.setShelterId(0);
                                int shelterId=shelter.getId();
                                db.updateShelterOccupancy(shelterId, occupancyUpdate);
                                //updateRestrictionList();

                                if (restrictionList.isEmpty()) {
                                    buttonStatus.setText("Please select restriction(s) to update");
                                    buttonStatus.setVisibility(View.VISIBLE);
                                    finish();
                                    startActivity(getIntent());
                                } else {
                                    //send that homeless to db.
                                    db.updateAccount(homeless);
                                    //show successful text and reset everything()
                                    buttonStatus.setText("Update successfully");
                                    buttonStatus.setVisibility(View.VISIBLE);
                                    finish();
                                    startActivity(getIntent());
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
            CheckBox item = checkBoxList.get(i);
            if (item.isChecked()) {
                Restrictions restrictions = enums.get(i);
                restrictionList.add(restrictions.toString());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_page);

        //set up check box restrictions
        buttonStatus = findViewById(R.id.status);
        buttonStatus.setVisibility(View.INVISIBLE);

        List<String> currentRestrictionList;
        checkBoxList = new ArrayList<>();
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

        //noinspection InstanceofConcreteClass
        if (account instanceof Homeless) {
            homeless = (Homeless) account;

            currentRestrictionList = ((Homeless) account).getRestrictionsMatch();
            if (currentRestrictionList != null) {
                for (int i = 0; i < currentRestrictionList.size(); i++) {
                    for (int j = 0; j < checkBoxList.size(); j++) {
                        CheckBox item = checkBoxList.get(j);
                        if (!item.isChecked()) {
                            Restrictions restrictions = enums.get(j);
                            boolean isCheck = checkCheckbox(currentRestrictionList.get(i),
                                    restrictions.toString());
                            item.setChecked(isCheck);
                        }
                    }
                }

            }
        }

        familySizeSpinner=familySizeSpinnerChoice;
        // add choices to family size
        for (int i = 1; i <familySizeSpinnerChoice; i++) {
            familySizeList.add(i);
        }
        family_spinner = findViewById(R.id.family_spinner);
        ArrayAdapter<Integer> family_adapter =
                new ArrayAdapter<Integer>(this,
                        android.R.layout.simple_spinner_item, familySizeList);
        family_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        family_spinner.setAdapter(family_adapter);


        family_spinner.setSelection(homeless.getFamilyMemberNumber() - 1);

        // Spinner click listener for family size
        family_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = family_spinner.getSelectedItem();
                familySize = Integer.valueOf(item.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing happens when nothing is selected
            }
        });

        //Grab name and user type to show in homepage
        TextView name = findViewById(R.id.name);
        TextView email = findViewById(R.id.email);

        // Display name and email
        name.setText("Name: " + account.getName());
        email.setText("Email: " + account.getEmail());


    }

    /**
     * Check if checkbox name match with selected one
     *
     * @param homelessRestriction the restrictions that a homeless matches
     * @param shelterRestriction  the restrictions that a shelter holds
     * @return true if match
     */
    private boolean checkCheckbox(String homelessRestriction, String shelterRestriction) {
        return homelessRestriction.equals(shelterRestriction);
    }

    /**
     * Retrieving active account
     *
     * @throws NoSuchUserException when there is no user with that name
     */
    private void getUserAccount() throws NoSuchUserException {
        db = new Db("pinkpanther", "PinkPantherReturns!");
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        String username = extra.getString("username");
        account = db.getAccountByUsername(username);
    }


    @Override
    public void onClick(View view) {
    }

    @Override
    public void onItemClick(View view, int position) {
    }
}