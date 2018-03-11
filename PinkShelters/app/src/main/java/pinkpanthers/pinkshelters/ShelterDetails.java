package pinkpanthers.pinkshelters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ShelterDetails extends AppCompatActivity {
    private DBI db;
    private Shelter s;
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

    }

    private void updateView (Shelter s) {
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

        TextView vacancy = findViewById(R.id.vacancy);
        String forVacancy = "Vacancy: " + s.getVacancy();
        vacancy.setText(forVacancy);
    }

    // TODO: cancel reservation

    public void claimBedButton(View view) {
        // check to see if user has updated their information (single/with families and gender)
//        if (user hasnt update info) {
//          Intent updateInfoPege = new Intent (ShelterDetails.this, UpdateInfo.class);
//          startActivity(updateInfoPage);
//        }

        // check available spots
        if (s.getVacancy() <= 0) {
            errorMessage.setText("Not enough beds");
            errorMessage.setVisibility(View.VISIBLE);
        } else {
            // checks restrictions
            // if fit restricitons, decrease vacancy and update vacancy
            // if dont fit restrictions, display restriction error message -> errorMessage.setText("restriction");
        }

    }

}
