package pinkpanthers.pinkshelters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import pinkpanthers.pinkshelters.R;
import pinkpanthers.pinkshelters.RecyclerAdapter;

public class ListOfSheltersActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener, View.OnClickListener {

    RecyclerAdapter adapter;
    private Button search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        // data to populate the RecyclerView with
        ArrayList<String> shelterNames = new ArrayList<>();
        shelterNames.add("My Sister's House");
        shelterNames.add("The Atlanta Day Center for Women & Children");
        shelterNames.add("The Shepherd's Inn");
        shelterNames.add("Fuqua Hall");
        shelterNames.add("Atlanta's Children Center");
        shelterNames.add("Eden Village ");
        shelterNames.add("Our House");
        shelterNames.add("Covenant House Georgia ");
        shelterNames.add("Nicholas House");
        shelterNames.add("Hope Atlanta ");
        shelterNames.add("Gateway Center");
        shelterNames.add("Young Adult Guidance Center");
        shelterNames.add("Homes of Light ");



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
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Click View " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) { //search button
        Intent shelterIntent = new Intent(ListOfSheltersActivity.this, WelcomePageActivity.class);
        startActivity(shelterIntent);
    }


}