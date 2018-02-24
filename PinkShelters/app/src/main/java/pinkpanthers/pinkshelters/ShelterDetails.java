package pinkpanthers.pinkshelters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ShelterDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_details);
//        in shelter list class, add:
//        getIntent().putExtra("shelterId", shelterId);

        
//        int shelterId = getIntent().getExtras().getInt("shelterId");
        int shelterId = 0;
        Shelter s = findShelterById(shelterId);
        updateView(s);

    }


    //wont need later if already made in other classes or database?
    private Shelter findShelterById(int shelterId) {
        //find shelter with shelter id
        Shelter s = new Shelter("My Sister's House", "Women/Children",
                "921 Howell Mill Road, Atlanta, Georgia 30318", 264,
                33.78017, -84, "(404) 367-2465",
                "Temporary, Emergency, Residential Recovery");
        //above - first shelter -- for testing
        return s;
    }

    private void updateView (Shelter s) {
        TextView name = findViewById(R.id.name);
        String forName = "Name: \t" +s.getShelterName();
        name.setText(forName);

        TextView capacity = findViewById(R.id.capacity);
        String forCapacity = "Capacity: \t" +s.getCapacity();
        capacity.setText(forCapacity);

        TextView longitude = findViewById(R.id.longitude);
        String forLongitude = "Longitude: \t" +s.getLongitude();
        longitude.setText(forLongitude);

        TextView latitude = findViewById(R.id.latitude);
        String forLatitude = "Latitude: \t" +s.getLatitude();
        latitude.setText(forLatitude);

        TextView restrictions = findViewById(R.id.restrictions);
        String forRestrictions = "Restrictions: \t" +s.getRestrictions();
        restrictions.setText(forRestrictions);

        TextView address = findViewById(R.id.address);
        String forAddress = "Address: \t" +s.getAddress();
        address.setText(forAddress);

        TextView phoneNum = findViewById(R.id.phoneNum);
        String forPhoneNum = "Phone Number: \t" +s.getPhoneNumber();
        phoneNum.setText(forPhoneNum);

        TextView specialNote = findViewById(R.id.specialNote);
        String forSpecialNote = "Special Note:\t " +s.getSpecialNote();
        specialNote.setText(forSpecialNote);
    }


}
