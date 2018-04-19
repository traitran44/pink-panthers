package pinkpanthers.pinkshelters.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pinkpanthers.pinkshelters.Model.Account;
import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.Homeless;
import pinkpanthers.pinkshelters.R;

public class AllHomelessActivity extends AppCompatActivity implements
        RecyclerAdapter.ItemClickListener{

    private List<Account> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        // data to populate the RecyclerView with
        ArrayList<String> accountNames = new ArrayList<>();
        DBI db = new Db("pinkpanther", "PinkPantherReturns!");
        accounts = db.getAllAccounts();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        assert extras != null;
        int shelterId = extras.getInt("shelterId");

        for (Account account: accounts) {
            if (account instanceof Homeless) {
                if (((Homeless) account).getShelterId() == shelterId) {
                    accountNames.add(account.getName());
                }
            }
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvShelters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter adapter = new RecyclerAdapter(this, accountNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {}

}
