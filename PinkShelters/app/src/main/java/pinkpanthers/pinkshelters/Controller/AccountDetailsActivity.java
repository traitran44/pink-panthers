package pinkpanthers.pinkshelters.Controller;

import android.content.Intent;
import android.widget.RemoteViews;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.sql.SQLException;

import pinkpanthers.pinkshelters.Model.Account;
import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Db;

import pinkpanthers.pinkshelters.Model.NoSuchUserException;
import pinkpanthers.pinkshelters.R;




public class AccountDetailsActivity extends AppCompatActivity {

    private DBI db;
    private Account a;
    private TextView txtView;
    ToggleButton togglebutton;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        Toolbar title = findViewById(R.id.titleBar);
        title.setTitle("Account Details");
        db = new Db("pinkpanther", "PinkPantherReturns!");
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.activity_account_details);
        //remoteViews.setTextViewText(R.id.ban_btn, "Ban");

        togglebutton = (ToggleButton) findViewById(R.id.toggleButton1);
        togglebutton.setChecked(false);
        button = (Button) findViewById(R.id.button1);

        button.setEnabled(false);

        togglebutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String accountState = a.getAccountState();
                String blocked = "blocked";
                if (togglebutton.isChecked())
                // && (accountState.equals(blocked) ))
                {
                    //Disabling button on toggle button off.
                    a.setAccountState(new String("blocked"));
                    TextView accountState2 = findViewById(R.id.accountState);
                    //bButton.setText("Ban");
                    String forAccountState = "Account State: " + a.getAccountState();
                    accountState2.setText(forAccountState);
                    txtView.setText("You have banned this account successfully");
                    txtView.setVisibility(View.VISIBLE);
                    button.setEnabled(false);

                } else {
                    //Enabling button on toggle button on.
                    TextView accountState1 = findViewById(R.id.accountState);
                    String block = "active";
                    a.setAccountState(block);
                    txtView.setText("You have un-banned this account successfully");
                    txtView.setVisibility(View.VISIBLE);
                    String forAccountState = "Account State: " + a.getAccountState();
                    accountState1.setText(forAccountState);
                    button.setEnabled(true);

                }


                try {
                    db.updateAccount(a);
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to update account " +
                            e.toString());
                } catch (NoSuchUserException e) { // this shouldn't happen
                    txtView.setText("Your account doesn't exist");
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(AccountDetailsActivity.this, " Button is Enable ", Toast.LENGTH_LONG).show();

            }
        });


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
        // Button testButton = findViewById(R.id.ban_btn);

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

    }


    private void setAccountStateText() {
        togglebutton = (ToggleButton) findViewById(R.id.toggleButton1);
        txtView = findViewById(R.id.banNotification_text);
        String blocked = "blocked";
        String active = "active";
        String accountState = a.getAccountState();
        if (accountState.equals(blocked)) {
            txtView.setText("This account has been banned");
            togglebutton.setChecked(true);
            txtView.setVisibility(View.VISIBLE);
        } else {
            txtView.setVisibility(View.INVISIBLE);

        }
    }
}


