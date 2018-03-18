package pinkpanthers.pinkshelters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // set up Db workspace
        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
        noResult = findViewById(R.id.no_result_found);
        shelters = db.getAllShelters(); // only shows at the beginning
        myShelters = shelters;

        // end

        // data to populate the RecyclerView with


        // set adapter for the first spinner
        choices = Arrays.asList("Gender", "Age Range", "Name");
        choices_spinner = findViewById(R.id.choices_spinner);
        ArrayAdapter<String> choices_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, choices);
        choices_spinner.setAdapter(choices_adapter);
        // end


        // set adapter for second spinner, depending on which choice was made
        genders = Arrays.asList("None", "Men", "Women", "Non-Binary");
        ageRanges = Arrays.asList("None", "Families with Newborns",
                "Children", "Young Adults", "Anyone");
        second_spinner = findViewById(R.id.age_range_gender_spinner);
        second_spinner.setVisibility(View.INVISIBLE);
        gender_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genders);
        age_range_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ageRanges);
        second_spinner.setAdapter(gender_adapter);
        // end


        shelter_name_edit_text = findViewById(R.id.shelter_name_edit_text);
        choices_spinner.setSelection(0);
        second_spinner.setSelection(0);

        choices_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String searchBy = choices.get(i);

                if (searchBy.equals("Gender")) { // search by gender was selected
                    second_spinner.setAdapter(gender_adapter);
                    second_spinner.setVisibility(View.VISIBLE);
                    shelter_name_edit_text.setVisibility(View.INVISIBLE);
                    noResult.setText("");

                } else if (searchBy.equals("Age Range")) { // search by age range was selected
                    second_spinner.setAdapter(age_range_adapter);
                    second_spinner.setVisibility(View.VISIBLE);
                    shelter_name_edit_text.setVisibility(View.INVISIBLE);
                    noResult.setText("");

                } else if (searchBy.equals("Name")) { // search by name was selected
                    second_spinner.setVisibility(View.INVISIBLE);
                    shelter_name_edit_text.setVisibility(View.VISIBLE);

                    // set interaction for the previewed list of shelter before starting the search
                    shelter_name_edit_text.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            myShelters = shelters;
                            setMarkersOnMap();
                            noResult.setText("");
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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
                            // changes occurred during onTextChanged so no changes after text changed
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
                String firstSelection = choices_spinner.getSelectedItem().toString();

                if (firstSelection.equals("Gender")) {
                    String searchBy = sqlConverter(genders.get(i));
                    noResult.setText("");
                    if (!("None".equals(searchBy))) {
                        try {
                            myShelters = db.getShelterByRestriction(searchBy);
                            setMarkersOnMap();
                        } catch (NoSuchUserException e) {
                            noResult.setText("No Result Found");
                            MapsActivity.this.mMap.clear();
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
                            MapsActivity.this.mMap.clear();
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

            MarkerOptions markerOptions = new MarkerOptions()
                    .title(shelter.getShelterName())
                    .position(shelterLocation)
                    .snippet( shelter.getAddress()
                            + "\n phone: + " + shelter.getPhoneNumber()
                            + "\n restrictions : " + shelter.getRestrictions()
                            + "\n vacancy : " + shelter.getVacancy()
                    );

            mMap.addMarker(markerOptions);
            float zoomLevel = 12.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shelterLocation, zoomLevel));
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

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent details = new Intent(MapsActivity.this, ShelterDetails.class);
                    details.putExtra("shelterId", shelter.getId());
                    details.putExtra("username", getIntent().getExtras().getString("username"));
                    startActivity(details);
                }
            });
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng shelterLocation;
        for (Shelter shelter : myShelters) {
            shelterLocation = new LatLng(shelter.getLatitude(), shelter.getLongitude());
            mMap.addMarker(new MarkerOptions().position(shelterLocation).title(shelter.getShelterName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(shelterLocation));
        }

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
