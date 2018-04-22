package pinkpanthers.pinkshelters.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;

import pinkpanthers.pinkshelters.Model.Account;
import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.NoSuchUserException;
import pinkpanthers.pinkshelters.Model.Shelter;
import pinkpanthers.pinkshelters.R;

public class AccountDetailsActivity extends AppCompatActivity {

    private DBI db;
    private Account a;
    private TextView errorMessage;
    private String username;
    private String message;


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
        TextView username = findViewById(R.id.accountUserName);
        String forUserName = "Account User Name: " + a.getUsername();
        username.setText(forUserName);

        TextView name = findViewById(R.id.accountUserName);
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


    }






    public void banAccountButton(View view) {
        final Button testButton = (Button) findViewById(R.id.banAccount);
        testButton.setTag(1);
        testButton.setText("Ban");
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int status = (Integer) v.getTag();
                if (status == 1) {
                    String ban = "banned";
                    a.setAccountState(ban);
                    testButton.setText("Unban");
                    TextView accountState = findViewById(R.id.accountState);
                    accountState.setText(ban);
                    v.setTag(0); //ban
                    finish();
                    startActivity(getIntent());
                    Log.d("account state", a.getAccountState());
                } else {
                    String ban = "active";
                    a.setAccountState(ban);
                    testButton.setText("Ban");
                    TextView accountState = findViewById(R.id.accountState);
                    accountState.setText(ban);
                    v.setTag(1); //ban
                    finish();
                    startActivity(getIntent());
                    Log.d("account state", a.getAccountState());
                }
                try {
                db.updateAccount(a);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to update account " +
                        e.toString());
            } catch (NoSuchUserException e) { // this shouldn't happen
                errorMessage.setText("Your account doesn't exist");
            }
        }
        });


    }
}

