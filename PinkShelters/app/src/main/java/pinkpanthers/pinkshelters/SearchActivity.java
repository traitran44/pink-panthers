package pinkpanthers.pinkshelters;

import android.content.Intent;
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

    private List<String> choices = new ArrayList<>();
    private List<String> genders = new ArrayList<>();
    private List<String> ageRanges = new ArrayList<>();
    private Spinner choices_spinner;
    private Spinner age_range_gender_spinner;
    private ArrayAdapter<String> age_range_adapter;
    private  ArrayAdapter<String> gender_adapter;
    private EditText shelter_name_edit_text;

    private RecyclerAdapter recycler_adapter;
    private RecyclerView search_recycler_view;

    private ArrayList<String> shelterNames;
    private List<Shelter> shelters;

    private Db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");

        // data to populate the RecyclerView with
        shelterNames = new ArrayList<>();
        shelters = db.getAllShelters(); // contain all shelters

        // set up the RecyclerView
        search_recycler_view = findViewById(R.id.search_recycler_view);
        search_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_adapter = new RecyclerAdapter(this, shelterNames);
        recycler_adapter.setClickListener(this);
        search_recycler_view.setAdapter(recycler_adapter);

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
                // nothing happens when nothing is selected
            }
        });

        choices_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String searchBy = choices.get(i);

                if ("Gender".equals(searchBy)) { // search by gender was selected
                    age_range_gender_spinner.setAdapter(gender_adapter);
                    age_range_gender_spinner.setVisibility(View.VISIBLE);
                    shelter_name_edit_text.setVisibility(View.INVISIBLE);

                } else if ("Age Range".equals(searchBy)) { // search by age range was selected
                    age_range_gender_spinner.setAdapter(age_range_adapter);
                    age_range_gender_spinner.setVisibility(View.VISIBLE);
                    shelter_name_edit_text.setVisibility(View.INVISIBLE);
                } else if ("Name".equals(searchBy)) { // search by name was selected
                    age_range_gender_spinner.setVisibility(View.INVISIBLE);
                    shelter_name_edit_text.setVisibility(View.VISIBLE);

                    shelterNames.clear(); // clear out old results found by different categories

                    //fill recyclerView with all shelters
                    for (int j = 0; j < shelters.size(); j++) {
                        shelterNames.add(shelters.get(j).getShelterName());
                    }

//                    recycler_adapter = new RecyclerAdapter(SearchActivity.this, shelterNames);
//                    search_recycler_view.setAdapter(recycler_adapter);

                    shelter_name_edit_text.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            // nothing changes before user types anything
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            // grabs each new character that the user types into the textView
                            shelterNames.clear();
                            try {
                                List<Shelter> myShelters = db.getShelterByName(charSequence.toString());
                                for (Shelter s : myShelters) {
                                    shelterNames.add(s.getShelterName());
                                }
                                recycler_adapter = new RecyclerAdapter(SearchActivity.this, shelterNames);
                                recycler_adapter.setClickListener(SearchActivity.this::onItemClick);
                                search_recycler_view.setAdapter(recycler_adapter);
                            } catch (NoSuchUserException e) {
                                shelterNames.add("No results found");

                            }
                            recycler_adapter.notifyDataSetChanged();
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


    }

    @Override
    public void onClick(View view) {

    }
    // when user clicks on a shelter in the search, should go to ShelterDetails activity
    @Override
    public void onItemClick(View view, int position) {
        Intent detail = new Intent(this, ShelterDetails.class);
        detail.putExtra("shelterId", shelters.get(position).getId());
        startActivity(detail);
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
