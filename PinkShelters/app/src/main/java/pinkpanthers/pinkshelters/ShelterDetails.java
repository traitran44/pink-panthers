package pinkpanthers.pinkshelters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ShelterDetails extends AppCompatActivity {
    private DBI db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_details);

        int shelterId = getIntent().getExtras().getInt("shelterId");
        try {
            db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
            Shelter s = db.getShelterById(shelterId);
            updateView(s);

        } catch (NoSuchUserException e) {
            throw new RuntimeException("This is not how it works " + e.toString());
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
    }


}
