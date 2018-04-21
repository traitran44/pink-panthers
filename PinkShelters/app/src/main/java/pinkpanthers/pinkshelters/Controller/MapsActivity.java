package pinkpanthers.pinkshelters.Controller;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
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

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;


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

    private String sheltername;
    private double longitude;
    private double latitude;
    private String address;
    private String phoneNum;
    private String restrictions;
    private String specialNote;
    private String capacity;

    public static final int REQUEST_CODE = 2;
    private final int CITY = 15;
    MapView mapView;
    GoogleMap gMap;
    LocationRequest locationRequest;
    Location location;
    GeoLocation destinationLocation;
    int callMapDraw = 0;
    int time = 0;
    View v;
    WaterData waterData;
    private boolean permissionDenied = false;
    private GoogleApiClient mGoogleApiClient;


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
            user = db.getAccountByUsername(getIntent().getExtras().getString("username"));
        } catch (NoSuchUserException e) {
            throw new RuntimeException("There is not account with this username");
        }


        // set adapter for the first spinner
        choices = Arrays.asList("Gender", "Age Range", "Name");
        choices_spinner = findViewById(R.id.choices_spinner);
        ArrayAdapter<String> choices_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, choices);
        choices_spinner.setAdapter(choices_adapter);


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

            //create marker
            MarkerOptions markerOptions = new MarkerOptions()
                    .title(shelter.getShelterName())
                    .position(shelterLocation)
                    .snippet( shelter.getAddress()
                            + "\n Phone Number: + " + shelter.getPhoneNumber()
                            + "\n Restrictions: " + shelter.getRestrictions()
                            + "\n Vacancy: " + shelter.getVacancy()
                    );

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
     *
     * @param context
     * @return permission to use location service
     */
    public static boolean haveLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static Location getBestLastKnownLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getAllProviders();
        Location bestLocation = null;

        for (String provider : providers) {
            try {
                Location location = locationManager.getLastKnownLocation(provider);
                if (bestLocation == null || location != null
                        && location.getAccuracy() > bestLocation.getAccuracy())
                    bestLocation = location;
            } catch (SecurityException ignored) {
            }
        }

        return bestLocation;
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



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        mMap.setMyLocationEnabled(true);

        Location location = getBestLastKnownLocation(MapsActivity.this);
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        }


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
                        mapLayout, false );

                // get user inputs from dialog box
                EditText shelternameText = addShelter.findViewById(R.id.name);
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
                        sheltername = shelternameText.getText().toString();
                        longitude = Double.parseDouble(longitudeText.getText().toString());
                        latitude = Double.parseDouble(latitudeText.getText().toString());
                        address = addressText.getText().toString();
                        phoneNum = phoneNumText.getText().toString();
                        restrictions = restrictionsText.getText().toString();
                        specialNote = specialNoteText.getText().toString();
                        capacity = capacityText.getText().toString();


                        Shelter newShelter = db.createShelter(sheltername, capacity, specialNote,
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
     * @param chosenItem
     * @return name
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
     *  Calculate the address using the tapped longitude and latitude
     * @param latLng
     * @param addressText
     */
    private void setAddress(LatLng latLng, EditText addressText) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude,1);
            String address = addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getLocality() +
                    ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getPostalCode();
            addressText.setText(address);
        } catch (java.io.IOException e) {
            throw new RuntimeException("GeoCoder could not calculate the address using the " +
                    "tapped longitude and latitude");
        }
    }

    /**
     * Put the map point at the current location of the user
     */
    private void putUserInCurrentLocation() {
        if (location != null && time == 0) {
            Log.d("Location", "Location is not null");
            LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
            EventBus.getDefault().post(location);
            gMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
            gMap.addMarker(new MarkerOptions().position(current).title("Current location"));
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, CITY));
            time++;
        } else {
            Log.d("Location", "not going to the if");
        }
    }

    /**
     * Create location request for the map
     */
    protected void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        connectGoogleApiClient();
    }

    /**
     * Callback from the onConnected method to get the location from the request
     *
     * @param location The location changed from the onConnected method
     */
    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        putUserInCurrentLocation();
    }

    /**
     * connect When the device unsuccessfully connected
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d("Connection failed", "Connection failed:" + result.getErrorMessage());
        Toast
                .makeText(getContext(), "Connection failed: " + result.getErrorMessage(), Toast.LENGTH_LONG)
                .show();
    }

    /**
     * Convert capacity string to int
     * @return int capacity
     */
    private int capacityConverter() {
        if ((capacity == null) || capacity.equals("")) {
            return 300; // default value for capacity
        }

        int num = 0;
        String[] str = capacity.split(" ");
        for (String ele: str) {
            if ((ele.charAt(0) >= 48) && (ele.charAt(0) <= 57)) {
                num += Integer.parseInt(ele);
            }
        }
        return num;
    }
}
