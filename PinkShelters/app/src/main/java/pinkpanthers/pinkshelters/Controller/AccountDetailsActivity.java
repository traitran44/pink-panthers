package pinkpanthers.pinkshelters.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pinkpanthers.pinkshelters.Model.Account;
import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.Homeless;
import pinkpanthers.pinkshelters.Model.NoSuchUserException;
import pinkpanthers.pinkshelters.Model.Shelter;
import pinkpanthers.pinkshelters.R;
import android.util.Log;


public class AccountDetailsActivity extends AppCompatActivity {

    private DBI db;
    private Account a;
    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        db = new Db("pinkpanther", "PinkPantherReturns!");


        try {
            Intent detailAccount = getIntent();
            Bundle extra = detailAccount.getExtras();
            String username = extra.getString("accUserName");
            a = db.getAccountByUsername(username);
            updateView(a);
        } catch (NoSuchUserException e) {
            throw new RuntimeException("This is not how it works " + e.toString());
        } catch (NullPointerException e) {
            throw new RuntimeException("NullPointerException is raised: " +
                    "getExtras() returns null in Account List");
        }
    }





    /**
     * specific details about a account
     *
     * @param a the selected account
     */

    private void updateView(Account a) {
        Button testButton = findViewById(R.id.ban_btn);

        TextView username = findViewById(R.id.accountUserName);
        String forUserName = "Account User Name: " + a.getUsername();
        username.setText(forUserName);

        TextView name = findViewById(R.id.accountName);
        String forName = "Account Name: " + a.getName();
        name.setText(forName);

        TextView email = findViewById(R.id.accountEmail);
        String forEmail = "Account Email " + a.getEmail();
        email.setText(forEmail);

        TextView password = findViewById(R.id.accountPassword);
        String forPassword = "Account password: " + a.getPassword();
        password.setText(forPassword);

        TextView accountState = findViewById(R.id.accountState);
        String forAccountState = "Account State: " + a.getAccountState();
        accountState.setText(forAccountState);

        TextView userId = findViewById(R.id.accountUserId);
        String forUserId = "Account Id: " + a.getUserId();
        userId.setText(forUserId);

        setAccountStateText();
        setBanButton();

        if (accountState.equals(new String("blocked")) ) {
            testButton.setText("Unban");
        } else {
                testButton.setText("Ban");
            }
            }


    private void setAccountStateText() {
        txtView = findViewById(R.id.banNotification_text);
        String blocked = "blocked";
        Log.d("tate", a.getAccountState());
        String accountState = a.getAccountState();
        if (accountState.equals(new String("blocked"))) {
            txtView.setText("This account has been banned");
            txtView.setVisibility(View.VISIBLE);
        } else {
            txtView.setVisibility(View.INVISIBLE);
        }
    }

         private void setBanButton() {
             String accountState = a.getAccountState();

             Button testButton = findViewById(R.id.ban_btn);
             if (!(a instanceof Homeless) || accountState.equals(new String("blocked"))) {
                 testButton.setVisibility(View.VISIBLE);}
                     else {
                 Homeless hobo= (Homeless) a;
                 List<String> homelessRestrictions = hobo.getRestrictionsMatch();
                 if (homelessRestrictions != null && homelessRestrictions.isEmpty())
                     testButton.setVisibility(View.INVISIBLE);
                 testButton.setVisibility(View.INVISIBLE);
             }}





            public void onClick(View v) {
            Button testButton = findViewById(R.id.ban_btn);
            String accountState = a.getAccountState();
            String blocked = "blocked";
                if (accountState.equals(blocked) ){
                    testButton.setText("Unban");
                    TextView accountState1 = findViewById(R.id.accountState);
                    String block="active";
                    a.setAccountState(block);
                    String forAccountState = "Account State: " + a.getAccountState();
                    accountState1.setText(forAccountState);
                } else {
                    a.setAccountState(blocked);
                    testButton.setText("Ban");
                    TextView accountState2 = findViewById(R.id.accountState);
                    String forAccountState = "Account State: " + a.getAccountState();
                    accountState2.setText(forAccountState );
                }
                try {
                    db.updateAccount(a);
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to update account " +
                            e.toString());
                } catch (NoSuchUserException e) { // this shouldn't happen
                    txtView.setText("Your account doesn't exist");
                }
                finish();
                startActivity(getIntent());
            }}