package pinkpanthers.pinkshelters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener, View.OnClickListener {

    List<String> choices = new ArrayList<>();
    List<String> genders = new ArrayList<>();
    List<String> ageRanges = new ArrayList<>();
    Spinner choices_spinner;
    Spinner age_range_gender_spinner;
    ArrayAdapter<String> age_range_adapter;
    ArrayAdapter<String> gender_adapter;
    EditText shelter_name_edit_text;

    RecyclerAdapter recycler_adapter;

    RecyclerView search_recycler_view;

    ArrayList<String> shelterNames;

    private Db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");

        // data to populate the RecyclerView with
        shelterNames = new ArrayList<>();


        choices.add("Gender");
        choices.add("Age Range");
        choices.add("Name");

        genders.add("None");
        genders.add("Men");
        genders.add("Women");
        genders.add("Non-Binary"); // for extra credit purposes

        ageRanges.add("None");
        ageRanges.add("Families with Newborns");
        ageRanges.add("Children");
        ageRanges.add("Young Adults");
        ageRanges.add("Anyone");

        choices_spinner = findViewById(R.id.choices_spinner);
        age_range_gender_spinner = findViewById(R.id.age_range_gender_spinner);
        shelter_name_edit_text = findViewById(R.id.shelter_name_edit_text);

        age_range_gender_spinner.setVisibility(View.INVISIBLE);


        ArrayAdapter<String> choices_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, choices);
        choices_spinner.setAdapter(choices_adapter);

        gender_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genders);
        age_range_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ageRanges);
        age_range_gender_spinner.setAdapter(gender_adapter);


        choices_spinner.setSelection(0);


        age_range_gender_spinner.setSelection(0);

        age_range_gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String mainSelection = choices_spinner.getSelectedItem().toString();

                if ("Gender".equals(mainSelection)) {
                    String searchBy = sqlConverter(genders.get(i));
                    if ("None".equals(searchBy)) {
                        shelterNames.clear();
                        recycler_adapter.notifyDataSetChanged();
                    } else {
                        shelterNames.clear();
                        try {
                            List<Shelter> myShelters =  db.getShelterByRestriction(searchBy);
                            for (Shelter sh: myShelters) {
                                shelterNames.add(sh.getShelterName());
                            }
                        } catch (NoSuchUserException e) {
                            // Don't skip, gotta raise No Result Found
                        }
                        recycler_adapter.notifyDataSetChanged();
                    }
                } else if ("Age Range".equals(mainSelection)) {
                    String searchBy = sqlConverter(ageRanges.get(i));
                    if ("None".equals(searchBy)) {
                        shelterNames.clear();
                        recycler_adapter.notifyDataSetChanged();
                    } else {
                        shelterNames.clear();
                        try {
                            List<Shelter> myShelters =  db.getShelterByRestriction(searchBy);
                            for (Shelter sh: myShelters) {
                                shelterNames.add(sh.getShelterName());
                            }
                        } catch (NoSuchUserException e) {
                            // Don't skip, gotta raise No Result Found
                        }
                        recycler_adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        choices_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String searchBy = choices.get(i);

                if ("Gender".equals(searchBy)) {
                    age_range_gender_spinner.setAdapter(gender_adapter);
                    age_range_gender_spinner.setVisibility(View.VISIBLE);
                    shelter_name_edit_text.setVisibility(View.INVISIBLE);

                } else if ("Age Range".equals(searchBy)) {
                    age_range_gender_spinner.setAdapter(age_range_adapter);
                    age_range_gender_spinner.setVisibility(View.VISIBLE);
                    shelter_name_edit_text.setVisibility(View.INVISIBLE);
                } else if ("Name".equals(searchBy)) {
                    age_range_gender_spinner.setVisibility(View.INVISIBLE);
                    shelter_name_edit_text.setVisibility(View.VISIBLE);
                    shelterNames.clear(); // clear out old results found by different categories
                    List<Shelter> shelters = db.getAllShelters();
                    for (int j = 0; j < shelters.size(); j++) {
                        shelterNames.add(shelters.get(j).getShelterName());
                    }
                    recycler_adapter = new RecyclerAdapter(SearchActivity.this, shelterNames);
                    search_recycler_view.setAdapter(recycler_adapter);
                    shelter_name_edit_text.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            recycler_adapter.filter(charSequence);

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        // loads all the shelter names
//        List<Shelter> shelters = db.getAllShelters();
//        for (int i = 0; i < shelters.size(); i++) {
//            shelterNames.add(shelters.get(i).getShelterName());
//        }

        // set up the RecyclerView
        search_recycler_view = findViewById(R.id.search_recycler_view);
        search_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_adapter = new RecyclerAdapter(this, shelterNames);
        recycler_adapter.setClickListener(this);
        search_recycler_view.setAdapter(recycler_adapter);
    }

    /**
     * customizable toast message
     * @param message message to display
     */
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private String sqlConverter(String chosenItem) {
        switch (chosenItem) {
            case ("Men"):
                return Restrictions.MEN.toString();
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