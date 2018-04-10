package pinkpanthers.pinkshelters.Controller;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import pinkpanthers.pinkshelters.Model.Account;
import pinkpanthers.pinkshelters.Model.Admin;
import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.NoSuchUserException;
import pinkpanthers.pinkshelters.R;
import pinkpanthers.pinkshelters.Model.Restrictions;
import pinkpanthers.pinkshelters.Model.Shelter;

/**
 * to create a map and filtering features
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<String> choices = new ArrayList<>();
    private List<String> genders = new ArrayList<>();
    private List<String> ageRanges = new ArrayList<>();
    private Spinner choices_spinner;
    private Spinner second_spinner;
    private ArrayAdapter<String> age_range_adapter;
    private ArrayAdapter<String> gender_adapter;
    private EditText shelter_name_edit_text;

    private List<Shelter> shelters;
    private List<Shelter> myShelters;
    private DBI db;
    private TextView noResult;

    private Account user;

    private String shelterName;
    private double longitude;
    private double latitude;
    private String address;
    private String phoneNum;
    private String restrictions;
    private String specialNote;
    private String capacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // set up Db workspace
        db = new Db("pinkpanther", "PinkPantherReturns!");
        noResult = findViewById(R.id.no_result_found);
        shelters = db.getAllShelters(); // only shows at the beginning
        myShelters = shelters;

        try {
            Intent intent = getIntent();
            Bundle extra = intent.getExtras();
            String username = extra.getString("username");
            user = db.getAccountByUsername(username);
        } catch (NoSuchUserException e) {
            throw new RuntimeException("There is not account with this username");
        }


        // set adapter for the first spinner
        choices = Arrays.asList("Gender", "Age Range", "Name");
        choices_spinner = findViewById(R.id.choices_spinner);
        ArrayAdapter<String> choices_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                choices);
        choices_spinner.setAdapter(choices_adapter);


        // set adapter for second spinner, depending on which choice was made
        genders = Arrays.asList("None", "Men", "Women", "Non-Binary");
        ageRanges = Arrays.asList("None", "Families with Newborns",
                "Children", "Young Adults", "Anyone");
        second_spinner = findViewById(R.id.age_range_gender_spinner);
        second_spinner.setVisibility(View.INVISIBLE);
        gender_adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, genders);
        age_range_adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, ageRanges);
        second_spinner.setAdapter(gender_adapter);
        // end


        shelter_name_edit_text = findViewById(R.id.shelter_name_edit_text);
        choices_spinner.setSelection(0);
        second_spinner.setSelection(0);

        choices_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String searchBy = choices.get(i);
                String gender = "Gender";
                String ageRange = "Age Range";
                String name = "Name";
                if (searchBy.equals(gender)) { // search by gender was selected
                    second_spinner.setAdapter(gender_adapter);
                    second_spinner.setVisibility(View.VISIBLE);
                    shelter_name_edit_text.setVisibility(View.INVISIBLE);
                    noResult.setText("");

                } else if (searchBy.equals(ageRange)) { // search by age range was selected
                    second_spinner.setAdapter(age_range_adapter);
                    second_spinner.setVisibility(View.VISIBLE);
                    shelter_name_edit_text.setVisibility(View.INVISIBLE);
                    noResult.setText("");

                } else if (searchBy.equals(name)) { // search by name was selected
                    second_spinner.setVisibility(View.INVISIBLE);
                    shelter_name_edit_text.setVisibility(View.VISIBLE);

                    // set interaction for the previewed list of shelter before starting the search
                    shelter_name_edit_text.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence,
                                                      int i,
                                                      int i1,
                                                      int i2) {
                            myShelters = shelters;
                            setMarkersOnMap();
                            noResult.setText("");
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence,
                                                  int i,
                                                  int i1,
                                                  int i2) {
                            // grabs each new character that the user types into the textView
                            try {
                                myShelters = db.getShelterByName(charSequence.toString());
                                setMarkersOnMap();
                            } catch (NoSuchUserException e) {
                                noResult.setText("No Result Found");
                                MapsActivity.this.mMap.clear();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            // changes occurred during onTextChanged
                            // so no changes after text changed
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing changes when nothing is selected
            }
        });

        second_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = choices_spinner.getSelectedItem();
                String firstSelection = item.toString();
                String gender = "Gender";
                if (firstSelection.equals(gender)) {
                    String searchBy = sqlConverter(genders.get(i));
                    noResult.setText("");
                    if (!("None".equals(searchBy))) {
                        try {
                            myShelters = db.getShelterByRestriction(searchBy);
                            setMarkersOnMap();
                        } catch (NoSuchUserException e) {
                            noResult.setText("No Result Found");
                            mMap.clear();
                        }
                    } else {
                        myShelters = shelters;
                        setMarkersOnMap();
                    }
                } else if ("Age Range".equals(firstSelection)) {
                    String searchBy = sqlConverter(ageRanges.get(i));
                    noResult.setText("");
                    if (!("None".equals(searchBy))) {
                        try {
                            myShelters = db.getShelterByRestriction(searchBy);
                            setMarkersOnMap();
                        } catch (NoSuchUserException e) {
                            noResult.setText("No Result Found");
                            mMap.clear();
                        }
                    } else {
                        myShelters = shelters;
                        setMarkersOnMap();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // nothing happens when nothing is selected
            }
        });

    }

    /**
     * Refresh the map with new results
     */
    private void setMarkersOnMap() {
        mMap.clear();
        LatLng shelterLocation;
        for (Shelter shelter : myShelters) {
            shelterLocation = new LatLng(shelter.getLatitude(), shelter.getLongitude());

            //create marker
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(shelter.getShelterName());
            markerOptions.position(shelterLocation);
            markerOptions.snippet(shelter.getAddress()
                                    + "\n Phone Number: + " + shelter.getPhoneNumber()
                                    + "\n Restrictions: " + shelter.getRestrictions()
                                    + "\n Vacancy: " + shelter.getVacancy());

            mMap.addMarker(markerOptions);
            float zoomLevel = 12.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shelterLocation, zoomLevel));

            //set up info window
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    Context context = getApplicationContext();
                    LinearLayout info = new LinearLayout(context);
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(context);
                    title.setGravity(Gravity.CENTER);
                    title.setTextColor(Color.BLACK);
                    title.setTypeface(null, Typeface.BOLD);
                    TextView snippet = new TextView(context);
                    title.setText(marker.getTitle());
                    snippet.setTextColor(Color.BLACK);
                    snippet.setText(marker.getSnippet());

                    info.addView(title);
                    info.addView(snippet);

                    return info;
                }
            });
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // makes a new shelter when user clicks once on the map
        if (user instanceof Admin) {
            mMap.setOnMapLongClickListener(latLng -> {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                // create dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add a shelter");

                // get view from xml and inflate into map activity
                LinearLayout mapLayout = findViewById(R.id.linearLayout2);
                LayoutInflater layoutInflater = getLayoutInflater();
                View addShelter = layoutInflater.inflate(R.layout.add_new_shelter,
                        mapLayout, false);

                // get user inputs from dialog box
                EditText shelterNameText = addShelter.findViewById(R.id.name);
                EditText longitudeText = addShelter.findViewById(R.id.longitude);
                EditText latitudeText = addShelter.findViewById(R.id.latitude);
                EditText addressText = addShelter.findViewById(R.id.address);
                EditText phoneNumText = addShelter.findViewById(R.id.phoneNum);
                EditText restrictionsText = addShelter.findViewById(R.id.restrictions);
                EditText specialNoteText = addShelter.findViewById(R.id.specialNote);
                EditText capacityText = addShelter.findViewById(R.id.capacity);

                // set the longitude and latitude texts to the tapped position on map
                String tappedLongitude = "" + latLng.longitude;
                longitudeText.setText(tappedLongitude);
                String tappedLatitude = "" + latLng.latitude;
                latitudeText.setText(tappedLatitude);
                builder.setView(addShelter);

                // calculating the address from longitude and latitude
                setAddress(latLng, addressText);

                // click on "Add a shelter" button
                builder.setPositiveButton("add a shelter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Editable textName = shelterNameText.getText();
                        shelterName = textName.toString();

                        Editable textLongitude = longitudeText.getText();
                        String numLongitude = textLongitude.toString();
                        longitude = Double.parseDouble(numLongitude);

                        Editable textLatitude = latitudeText.getText();
                        String numLatitude = textLatitude.toString();
                        latitude = Double.parseDouble(numLatitude);

                        Editable textAddress = addressText.getText();
                        address = textAddress.toString();

                        Editable textPhone = phoneNumText.getText();
                        phoneNum = textPhone.toString();

                        Editable textRestrict = restrictionsText.getText();
                        restrictions = textRestrict.toString();

                        Editable textSpecial = specialNoteText.getText();
                        specialNote = textSpecial.toString();

                        Editable textCapacity = capacityText.getText();
                        capacity = textCapacity.toString();


                        Shelter newShelter = db.createShelter(shelterName, capacity, specialNote,
                                latitude, longitude, phoneNum, restrictions, address);

                        newShelter.setUpdate_capacity(capacityConverter());

                        // details that pops up when user clicks onto the marker
                        markerOptions.title(newShelter.getShelterName());
                        markerOptions.snippet(newShelter.getAddress()
                                + "\n Phone Number: + " + newShelter.getPhoneNumber()
                                + "\n Restrictions: " + newShelter.getRestrictions()
                                + "\n Vacancy: " + newShelter.getVacancy());

                        // add marker to map and move camera to center its screen around it
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        Marker marker = mMap.addMarker(markerOptions);
                        marker.showInfoWindow();

                    }
                });
                // click on "cancel" button
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        markerOptions.visible(false);
                    }
                });
                //show the builder (the dialog window)
                builder.show();
            });
        }
    }

    /**
     * convert item name to corresponding under mysql
     *
     * @param chosenItem the item picked from the spinner
     * @return the sql portion
     */
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

    /**
     * Calculate the address using the tapped longitude and latitude
     *
     * @param latLng latitude and longitude
     * @param addressText the address at that location
     */
    private void setAddress(LatLng latLng, EditText addressText) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude,
                    latLng.longitude,1);
            Address onlyAddress = addresses.get(0);
            String address = onlyAddress.getAddressLine(0) + ", "
                    + onlyAddress.getLocality() +
                    ", " + onlyAddress.getAdminArea() + ", " + onlyAddress.getPostalCode();
            addressText.setText(address);
        } catch (java.io.IOException e) {
            throw new RuntimeException("GeoCoder could not calculate the address using the " +
                    "tapped longitude and latitude");
        }
    }

    /**
     * Convert capacity string to int
     *
     * @return int capacity
     */
    private int capacityConverter() {
        String empty = "";
        if ((capacity == null) || capacity.equals(empty)) {
            return 300; // default value for capacity
        }

        int num = 0;
        String[] str = capacity.split(" ");
        for (String ele : str) {
            if ((ele.charAt(0) >= 48) && (ele.charAt(0) <= 57)) {
                num += Integer.parseInt(ele);
            }
        }
        return num;
    }
}
