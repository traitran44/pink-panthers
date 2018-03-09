package pinkpanthers.pinkshelters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import pinkpanthers.pinkshelters.R;
import pinkpanthers.pinkshelters.RecyclerAdapter;

public class ListOfSheltersActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener, View.OnClickListener {

    RecyclerAdapter adapter;
    private Button search_button;
    private int selectedShelter;
    private DBI db;
    private List<Shelter> shelters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        // data to populate the RecyclerView with
        ArrayList<String> shelterNames = new ArrayList<>();

        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");

        shelters = db.getAllShelters();
        for (int i = 0; i < shelters.size(); i++) {
            shelterNames.add(shelters.get(i).getShelterName());
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvShelters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this, shelterNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        // set up search button
        Button search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) { //clicked on one shelter
        selectedShelter = position;
        Intent detail = new Intent(this, ShelterDetails.class);
        detail.putExtra("shelterId", shelters.get(selectedShelter).getId());
        startActivity(detail);
    }
        //TODO: fix Intent to link to Search Page when we create one for M7
    @Override
    public void onClick(View v) { //search button
        Intent intent = new Intent(ListOfSheltersActivity.this, SearchActivity.class);
        startActivity(intent);
    }

//    /**
//     * customizable toast message
//     * @param message message to display
//     */
//    private void toastMessage(String message){
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }

}