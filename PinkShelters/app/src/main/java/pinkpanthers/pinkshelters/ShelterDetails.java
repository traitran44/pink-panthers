package pinkpanthers.pinkshelters;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;

public class ShelterDetails extends AppCompatActivity {
    private DBI db;
    private Shelter s;
    private Homeless a; //current user that is logged in
    private TextView errorMessage;
    private TextView vacancy;
    private Shelter reservedShelter;
    private Button claimBedButton;
    private Button updateInfoButton;
    private Button cancelBedButton;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_details);
        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
        errorMessage = findViewById(R.id.errorMessage);
<<<<<<< HEAD
        //get preferences to pass in username for account
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREFS_NAME, Registration.MODE_PRIVATE);
        String userName=preferences.getString("USERNAME", "");


        try {    a = db.getAccountByUsername(userName);
                 int shelterId = getIntent().getExtras().getInt("shelterId");
                 s = db.getShelterById(shelterId);
                 updateView(s);
                 Log.d("HAHAHAHAH", a.getUsername());
=======
        vacancy = findViewById(R.id.vacancy);
        claimBedButton = findViewById(R.id.claimBed);
        updateInfoButton = findViewById(R.id.updateAccountButton);
        cancelBedButton = findViewById(R.id.cancelReservation);

        try {
            int shelterId = getIntent().getExtras().getInt("shelterId");
            s = db.getShelterById(shelterId);
            updateView(s);
        } catch (NoSuchUserException e) {
            throw new RuntimeException("This is not how it works " + e.toString());
        } catch (NullPointerException e) {
            throw new RuntimeException("NullPointerException is raised: getExtras() returns null in ListOfShelter");
        }
>>>>>>> bb649dc2b78d3722c614f1c212bcc04dc4b70add

        try {
            username = getIntent().getExtras().getString("username");
            Account user = db.getAccountByUsername(username);
            if (user instanceof Homeless) { // user is a homeless person
                a = (Homeless) user;
                claimBedButton.setVisibility(View.VISIBLE);
                try {
                    reservedShelter = db.getShelterById(a.getShelterId());
                    if (reservedShelter.equals(s)) {
                        cancelBedButton.setVisibility(View.VISIBLE);
                    }
                } catch (NoSuchUserException e) {
                    // this shelter is not the one the user reserved to
                    // so nothing happens (cancel reservation button remains invisible)
                }
            } else { // user is not a homeless person
                claimBedButton.setVisibility(View.INVISIBLE);
            }
        } catch (NoSuchUserException e) {
            throw new RuntimeException("There is no user with that username or shelter with that ID");
        } catch (NullPointerException e) {
            throw new RuntimeException("getExtras() returns null username");
        }
    }

    private void updateView(Shelter s) {
        TextView name = findViewById(R.id.name);
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

    public void claimBedButton(View view) {
<<<<<<< HEAD
      // check to see if user has updated their information (single/with families and gender)
//      if (user hasnt update info)
//        Intent updateInfoPege = new Intent (ShelterDetails.this, UpdateInfo.class);
//        startActivity(updateInfoPage);
//     }
//
////     // check if account type of homeless, if homeless then getFamilyMemberNumber
        if (a instanceof Homeless) {
            int familyMemberNumber = ((Homeless) a).getFamilyMemberNumber();
            System.out.print(familyMemberNumber);
            {
                if (s.getVacancy() <= familyMemberNumber) {
                    errorMessage.setText("Sorry, there are not enough beds");
                    errorMessage.setVisibility(View.VISIBLE);
                } else if (((Homeless) a).getRestrictionsMatch().contains(s.getRestrictions())) {
                    try {
                        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
                        //update vacancy of shelter
                        int vacancy = s.getVacancy()-familyMemberNumber;
                        s.setVacancy(vacancy);
                        //update shelter occupancy for Db
                        int occupancy=s.getUpdate_capacity()-vacancy;
                        s.setOccupancy(occupancy);
                        db.updateShelterOccupancy(s.getId(),occupancy);
                        //pass in account object to update account
                        db.updateAccount(this.a);
                        errorMessage.setText("You have claim your bed(s) successfully");
                        errorMessage.setVisibility(View.VISIBLE);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (NoSuchUserException e) {
                        e.printStackTrace();
                    }
                } else
                    errorMessage.setText("Restrictions error");
=======
        // check to see if user has updated their information
        if (a.getFamilyMemberNumber() == 0 || a.getRestrictionsMatch() == null) {
            errorMessage.setText("You need to update your information before you can claim a bed or beds. "
                    + "Please update your information");
            errorMessage.setVisibility(View.VISIBLE);
            updateInfoButton.setVisibility(View.VISIBLE);
        } else {
            // check if account type of homeless, if homeless then getFamilyMemberNumber

            int familyMemberNumber = a.getFamilyMemberNumber();
            if (s.getVacancy() <= familyMemberNumber) {
                errorMessage.setText("Sorry, there are not enough beds");
                errorMessage.setVisibility(View.VISIBLE);
            } else if (a.getRestrictionsMatch().contains(s.getRestrictions())) {
                try {
                    db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
                    //update vacancy of shelter
                    int vacancy = s.getVacancy() - familyMemberNumber;
                    s.setVacancy(vacancy);
                    //update shelter occupancy for Db
                    int occupancy = s.getUpdate_capacity() - vacancy;
                    s.setOccupancy(occupancy);
                    db.updateShelterOccupancy(s.getId(), occupancy);
                    a.setShelterId(s.getId());
                    //pass in account object to update account
                    db.updateAccount(a);
                    String success = "You have claimed " + familyMemberNumber + " bed(s) successfully";
                    errorMessage.setText(success);
>>>>>>> bb649dc2b78d3722c614f1c212bcc04dc4b70add
                    errorMessage.setVisibility(View.VISIBLE);
                    errorMessage.setTextColor(Color.GREEN);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NoSuchUserException e) {
                    e.printStackTrace();
                }
            } else{
                errorMessage.setText("You do not fit the restrictions of this shelter");
                errorMessage.setVisibility(View.VISIBLE);
            }
        }


    }

    public void updateInfoButton(View view) {
        Intent updateInfoPage = new Intent(ShelterDetails.this, UserInfoActivity.class);
        updateInfoPage.putExtra("username", username);
        startActivity(updateInfoPage);
    }

    public void cancelReservationButton(View view) {
        s.setVacancy(s.getVacancy() + a.getFamilyMemberNumber());
        String forVacancy = "Vacancy: " + s.getVacancy();
        vacancy.setText(forVacancy);
        int occupancy = s.getUpdate_capacity() - s.getVacancy();
        s.setOccupancy(occupancy);
        a.setShelterId(0); // no longer associate with any shelter
        try {
            db.updateAccount(a);
            db.updateShelterOccupancy(s.getId(), occupancy);
        } catch (NoSuchUserException e) {
            throw new RuntimeException("Homeless user is null");
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("SQLException raised when trying to update account" +
                    " during canceling reservation");
        }

    }

}
