package pinkpanthers.pinkshelters.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pinkpanthers.pinkshelters.Model.Account;
import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.Homeless;
import pinkpanthers.pinkshelters.Model.NoSuchUserException;
import pinkpanthers.pinkshelters.Model.Shelter;
import pinkpanthers.pinkshelters.R;

/**
 * to create a view that allow users to view more details about a shelter
 */
public class ShelterDetails extends AppCompatActivity {
    private DBI db;
    private Shelter s;
    private Homeless a; //current user that is logged in
    private TextView errorMessage;
    private TextView vacancy;
    private Shelter reservedShelter;
    private Button updateInfoButton;
    private String username;
    private String message; //message for error message
    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private Button checkIn;
    private Spinner peopleNumber;
    private Button viewResident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_details);
        db = new Db("pinkpanther", "PinkPantherReturns!");
        errorMessage = findViewById(R.id.errorMessage);
        vacancy = findViewById(R.id.vacancy);
        Button claimBedButton = findViewById(R.id.claimBed);
        updateInfoButton = findViewById(R.id.updateAccountButton);
        Button cancelBedButton = findViewById(R.id.cancelReservation);
        checkIn = findViewById(R.id.check_in_btn);
        viewResident = findViewById(R.id.view_all_homeless);
        peopleNumber = findViewById(R.id.people_number);
        List<Integer> number = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                number);
        peopleNumber.setAdapter(adapter);

        try {
            Intent intent = getIntent();
            Bundle extra = intent.getExtras();
            assert extra != null;
            int shelterId = extra.getInt("shelterId");
            s = db.getShelterById(shelterId);
            updateView(s);
        } catch (NoSuchUserException e) {
            throw new RuntimeException("This is not how it works " + e.toString());
        } catch (NullPointerException e) {
            throw new RuntimeException("NullPointerException is raised: " +
                    "getExtras() returns null in ListOfShelter");
        }

        try {
            Intent intent = getIntent();
            Bundle extra = intent.getExtras();
            assert extra != null;
            username = extra.getString("username");
            Account user = db.getAccountByUsername(username);
            if (user instanceof Homeless) { // user is a homeless person
                a = (Homeless) user;
                claimBedButton.setVisibility(View.VISIBLE);
                try {
                    reservedShelter = db.getShelterById(a.getShelterId());
                    if (reservedShelter.getId() == s.getId()) {
                        cancelBedButton.setVisibility(View.VISIBLE);
                        message = "You have claimed " + a.getFamilyMemberNumber()
                                + " bed(s) at this shelter";
                        errorMessage.setText(message);
                        errorMessage.setTextColor(Color.GREEN);
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                } catch (NoSuchUserException e) {
                    // this shelter is not the one that the user reserved to
                    // so nothing happens (cancel reservation button remains invisible)
                }
            } else { // user is not a homeless person
                claimBedButton.setVisibility(View.INVISIBLE);
                checkIn.setVisibility(View.VISIBLE);
                peopleNumber.setVisibility(View.VISIBLE);
                viewResident.setVisibility(View.VISIBLE);

            }
        } catch (NoSuchUserException e) {
            throw new RuntimeException("There is no user with that " +
                    "username or shelter with that ID");

        } catch (NullPointerException e) {
            throw new RuntimeException("getExtras() returns null username");
        }

        addListenerOnRatingBar();
        addListenerOnButton();
        checkInButton();
    }

    /**
     * specific details about a shelter
     *
     * @param s the selected shelter
     */
    private void updateView(Shelter s) {
        TextView name = findViewById(R.id.accountUserName);
        String forName = "Name: " + s.getShelterName();
        name.setText(forName);

        TextView capacity = findViewById(R.id.capacity);
        String forCapacity = "Capacity: " + s.getCapacity();
        capacity.setText(forCapacity);

        TextView longitude = findViewById(R.id.longitude);
        String forLongitude = "Longitude: " + s.getLongitude();
        longitude.setText(forLongitude);

        TextView latitude = findViewById(R.id.latitude);
        String forLatitude = "Latitude: " + s.getLatitude();
        latitude.setText(forLatitude);

        TextView restrictions = findViewById(R.id.restrictions);
        String forRestrictions = "Restrictions: " + s.getRestrictions();
        restrictions.setText(forRestrictions);

        TextView address = findViewById(R.id.address);
        String forAddress = "Address: " + s.getAddress();
        address.setText(forAddress);

        TextView phoneNum = findViewById(R.id.phoneNum);
        String forPhoneNum = "Phone Number: " + s.getPhoneNumber();
        phoneNum.setText(forPhoneNum);

        TextView specialNote = findViewById(R.id.specialNote);
        String forSpecialNote = "Special Note: " + s.getSpecialNotes();
        specialNote.setText(forSpecialNote);

        String forVacancy = "Vacancy: " + s.getVacancy();
        vacancy.setText(forVacancy);
    }

    /**
     * the button that allows users to claim a bed at a shelter
     *
     * @param view the current view that holds the claim button
     */
    public void claimBedButton(@SuppressWarnings("unused") View view) {
        // check to see if user has updated their information
        if ((a.getFamilyMemberNumber() == 0) || (a.getRestrictionsMatch() == null)) {
            message = "You need to update your information before you can claim a bed or beds. "
                    + "Please update your information by using the button below";
            errorMessage.setText(message);
            errorMessage.setVisibility(View.VISIBLE);
            updateInfoButton.setVisibility(View.VISIBLE);
        } else {
            //homeless person cant claim bed(s) if they have already claimed bed(s)
            // at a different shelter

            int familyMemberNumber = a.getFamilyMemberNumber();
            if (a.getShelterId() != 0) {
                if (s.getId() == reservedShelter.getId()) {
                    message = "Sorry, you have already claimed " + familyMemberNumber +
                            " bed(s)";
                    errorMessage.setText(message);
                    errorMessage.setTextColor(Color.RED);
                } else {
                    message = "You have already claimed " + familyMemberNumber +
                            " bed(s) at another shelter called " + reservedShelter.getShelterName();
                    errorMessage.setText(message);
                    errorMessage.setTextColor(Color.RED);
                }
                errorMessage.setVisibility(View.VISIBLE);

                // check if there are still available bed(s) for their families
            } else if (s.getVacancy() <= familyMemberNumber) {
                message = "Sorry, there are not enough beds";
                errorMessage.setText(message);
                errorMessage.setVisibility(View.VISIBLE);

            } else {
                //set of restriction of shelter
                Set<String> shelterRestrictionSet = new HashSet<>();

                //turn String of shelter restriction to List
                String restrictions = s.getRestrictions();
                List<String> shelterRestrictionList = Arrays.asList(restrictions.split((", ")));

                //add Strings of shelter restriction list to set
                for (String s : shelterRestrictionList) {
                    shelterRestrictionSet.add(s.toLowerCase());
                }

                //list of homeless restriction
                List<String> homelessRestrictions = a.getRestrictionsMatch();

                //turn list into set of homeless restriction
                Set<String> homelessRestrictionsSet = new HashSet<>(homelessRestrictions);
                Set<String> common = new HashSet<>(shelterRestrictionSet);
                common.retainAll(homelessRestrictionsSet);
                String sRestrictions = s.getRestrictions();
                String anyone1 = sRestrictions.toLowerCase();
                String anyone = "anyone";
                if (anyone1.equals(anyone) || (common.equals(homelessRestrictionsSet))) {
                    try {
                        //update vacancy of shelter
                        int vacancy1 = s.getVacancy() - familyMemberNumber;
                        //update current vacancy on screen
                        String forVacancy = "Vacancy: " + s.getVacancy();
                        vacancy.setText(forVacancy);

                        //update shelter occupancy for Db
                        int occupancy = s.getUpdate_capacity() - vacancy1;
                        s.setOccupancy(occupancy);
                        db.updateShelterOccupancy(s.getId(), occupancy);
                        a.setShelterId(s.getId());

                        //pass in account object to update account
                        db.updateAccount(a);
                        // refresh page to update vacancy TextView
                        finish();
                        startActivity(getIntent());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (NoSuchUserException e) {
                        e.printStackTrace();
                    }
                } else {
                    message = "You do not fit the restrictions of this shelter";
                    errorMessage.setText(message);
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * To direct to all homeless that check in that shelter
     * @param view curent view that holds the button
     */
    public void viewAllHomelessButton(View view) {
        Intent viewAllHomeless = new Intent(this, AllHomelessActivity.class);
        viewAllHomeless.putExtra("shelterId", s.getId());
        startActivity(viewAllHomeless);
    }


    /**
     * to check in manually how many people want to reside at that shelter
     *
     */
    public void checkInButton() {
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int occupancy = s.getOccupancy() + (Integer) peopleNumber.getSelectedItem();
                if (occupancy > s.getUpdate_capacity()) {
                    message = "Sorry, there are not enough beds";
                    errorMessage.setText(message);
                    errorMessage.setVisibility(View.VISIBLE);
                } else {
                    s.setOccupancy(occupancy);
                    try {
                        db.updateShelterOccupancy(s.getId(), s.getOccupancy());
                    } catch (NoSuchUserException e) {
                        throw new RuntimeException("Homeless user is null or shelterId does not exist");
                    } catch (java.sql.SQLException e){
                        throw new RuntimeException("SQLException raised when trying " +
                                "to update account or shelter" +
                                " during canceling reservation");
                    }

                    finish();
                    startActivity(getIntent());
                }
            }
        });
    }

    /**
     * to direct to userInfoActivity
     *
     * @param view the current view that holds the update button
     */
    public void updateInfoButton(@SuppressWarnings("unused") View view) {
        Intent updateInfoPage = new Intent(ShelterDetails.this,
                UserInfoActivity.class);
        updateInfoPage.putExtra("username", username);
        startActivity(updateInfoPage);
    }

    /**
     * to cancel a reservation and will update the vacancy number
     *
     * @param view the current view that holds the cancel button
     */
    public void cancelReservationButton(@SuppressWarnings("unused") View view) {
        // update vacancy
        int vacancy1 = s.getVacancy() + a.getFamilyMemberNumber();
        String forVacancy = "Vacancy: " + vacancy1;
        vacancy.setText(forVacancy);

        //update occupancy
        int occupancy = s.getOccupancy() - a.getFamilyMemberNumber();
        s.setOccupancy(occupancy);

        //update homeless's shelter id
        a.setShelterId(0); // no longer associate with any shelter
        try {
            db.updateAccount(a);
            db.updateShelterOccupancy(s.getId(), occupancy);
        } catch (NoSuchUserException e) {
            throw new RuntimeException("Homeless user is null or shelterId does not exist");
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("SQLException raised when trying " +
                    "to update account or shelter" +
                    " during canceling reservation");
        }

        message = "You have successfully cancel your reservation for " +
                a.getFamilyMemberNumber() + " bed(s)";
        errorMessage.setText(message);
        errorMessage.setTextColor(Color.GREEN);
        errorMessage.setVisibility(View.VISIBLE);

        // refresh page to update vacancy textView
        finish();
        startActivity(getIntent());
    }

    /**
     * listener for the rating bar so that it shows the current rating of the shelter
     */
    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setRating(s.getRating());
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                txtRatingValue.setText("Thanks for rating " + String.valueOf(rating));
                s.setRating(rating);

            }
        });
    }

    /**
     * click on this button to save the result on the database
     */
    public void addListenerOnButton() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button btnSubmit = (Button) findViewById(R.id.ratingButton);

        //if click on this button, then display the current rating value.
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(ShelterDetails.this,
                        String.valueOf(ratingBar.getRating()),
                        Toast.LENGTH_SHORT).show();

                try {
                    db.updateShelter(s);
                } catch (NoSuchUserException e) {
                    throw new RuntimeException("No shelter with this ID get updated");
                } catch (SQLException e) {
                    throw new RuntimeException("SQL statement is written wrong");
                }
            }

        });

    }

}