package pinkpanthers.pinkshelters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private Db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        choices.add("gender");
        choices.add("age range");
        choices.add("name");

        genders.add("male");
        genders.add("female");

        ageRanges.add("family w/ newborns");
        ageRanges.add("children");
        ageRanges.add("young adults");
        ageRanges.add("anyone");

        choices_spinner = findViewById(R.id.choices_spinner);
        age_range_gender_spinner = findViewById(R.id.age_range_gender_spinner);
        shelter_name_edit_text = findViewById(R.id.shelter_name_edit_text);

        age_range_gender_spinner.setVisibility(View.INVISIBLE);


        ArrayAdapter<String> choices_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, choices);
        choices_spinner.setAdapter(choices_adapter);

        gender_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genders);
        age_range_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ageRanges);
        age_range_gender_spinner.setAdapter(gender_adapter);

        // sets selection to gender
        choices_spinner.setSelection(0);

        // sets selection to male
        age_range_gender_spinner.setSelection(0);

        choices_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String searchBy = choices.get(i);

                if ("gender".equals(searchBy)) {
                    age_range_gender_spinner.setAdapter(gender_adapter);
                    age_range_gender_spinner.setVisibility(View.VISIBLE);
                    shelter_name_edit_text.setVisibility(View.INVISIBLE);
                } else if ("age range".equals(searchBy)) {
                    age_range_gender_spinner.setAdapter(age_range_adapter);
                    age_range_gender_spinner.setVisibility(View.VISIBLE);
                    shelter_name_edit_text.setVisibility(View.INVISIBLE);
                } else {
                    age_range_gender_spinner.setVisibility(View.INVISIBLE);
                    shelter_name_edit_text.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // data to populate the RecyclerView with
        ArrayList<String> shelterNames = new ArrayList<>();

        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");

        List<Shelter> shelters = db.getAllShelters();
        for (int i = 0; i < shelters.size(); i++) {
            shelterNames.add(shelters.get(i).getShelterName());
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycler_adapter = new RecyclerAdapter(this, shelterNames);
        recycler_adapter.setClickListener(this);
        recyclerView.setAdapter(recycler_adapter);
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
}
