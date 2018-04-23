package pinkpanthers.pinkshelters.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Account;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.Shelter;
import pinkpanthers.pinkshelters.R;

/**
 * to create a view that shows a list of shelters
 */
public class AccountListActivity extends AppCompatActivity implements
        RecyclerAdapter.ItemClickListener, View.OnClickListener {

    private List<Account> allAccountList;
    private String username; //used to get current logged in user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        Toolbar title = findViewById(R.id.titleBar);
        title.setTitle("Account List");

        // data to populate the RecyclerView with
        ArrayList<String> allAccountName = new ArrayList<>();

        DBI db = new Db("pinkpanther", "PinkPantherReturns!");

        allAccountList = db.getAllAccounts();
        for (int i = 0; i < allAccountList.size(); i++) {
            Account a = allAccountList.get(i);
            allAccountName.add(a.getUsername());
            //allAccountName.add(a.getAccountState());
        }


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvShelters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter adapter = new RecyclerAdapter(this, allAccountName);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick (View view,int position){
        Intent detailAccount = new Intent(this, AccountDetailsActivity.class);
        Account acc =allAccountList.get(position);
        detailAccount.putExtra("accUserName", acc.getUsername());
        Log.d("this is the account", acc.getAccountState().toString());
        startActivity(detailAccount);
    }


    @Override
    public void onClick(View v) {

    }
}