package pinkpanthers.pinkshelters;

import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;

public class ShelterDetails extends AppCompatActivity {
    private DBI db;
    private Shelter s;
    private Account a;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_details);
        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
        errorMessage = findViewById(R.id.errorMessage);

        try {
            int shelterId = getIntent().getExtras().getInt("shelterId");
            s = db.getShelterById(shelterId);
            updateView(s);

        } catch (NoSuchUserException e) {
            throw new RuntimeException("This is not how it works " + e.toString());
        } catch (NullPointerException e) {
            throw new RuntimeException("NullPointerException is raised: getExtras() returns null in ListOfShelter");
        }

        try {
            String username = getIntent().getExtras().getString("username");
            a = db.getAccountByUsername(username);
        } catch (NoSuchUserException e) {
            throw new RuntimeException("There is no user with that username");
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

        String numCapacity = s.getCapacity();
        String numberOnly = numCapacity.replaceAll("[^0-9]", "");


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

        TextView vacancy = findViewById(R.id.vacancy);
        String forVacancy = "Vacancy: " + s.getVacancy();
        vacancy.setText(forVacancy);
    }

    // TODO: cancel reservation
    public void claimBedButton(View view) {
        // check to see if user has updated their information (single/with families and gender)
//      if (user hasnt update info)
//        Intent updateInfoPege = new Intent (ShelterDetails.this, UpdateInfo.class);
//        startActivity(updateInfoPage);
//     }

//
//       // check if account type of homeless, if homeless then getFamilyMemberNumber
        if (a instanceof Homeless) {
            int familyMemberNumber = ((Homeless) a).getFamilyMemberNumber();
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
                    errorMessage.setVisibility(View.VISIBLE);
            }
        }
    }
//
//    // TODO need to check to see if user is checked into this shelter
//    // and make button visible in onCreate method
//    public void cancelReservationButton(View view) {
//        //int familyNumber = a.getFamilyMemberNumber();
//    }

}
