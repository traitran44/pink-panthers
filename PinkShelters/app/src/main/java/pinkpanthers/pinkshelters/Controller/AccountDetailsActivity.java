package pinkpanthers.pinkshelters.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import pinkpanthers.pinkshelters.Model.Account;
import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.Homeless;
import pinkpanthers.pinkshelters.Model.NoSuchUserException;
import pinkpanthers.pinkshelters.Model.Shelter;
import pinkpanthers.pinkshelters.R;

public class AccountDetailsActivity extends AppCompatActivity {

    private DBI db;
    private Account a;
    private TextView txtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        db = new Db("pinkpanther", "PinkPantherReturns!");
        Button onClick=(Button) findViewById(R.id.ban_btn);
        Button unBanButton=(Button) findViewById(R.id.unBan_btn);
        txtView = findViewById(R.id.banNotification_text);


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
        txtView = findViewById(R.id.banNotification_text);
        String ban= "banned";
        Log.d("tate", a.getAccountState());
        String accountState = a.getAccountState();
        if (accountState.equals(new String("banned"))) {
            txtView.setText("This account has been banned");
            txtView.setVisibility(View.VISIBLE);
        } else {
            txtView.setVisibility(View.INVISIBLE);
        }
    }


    public void onClick(View v) { //ban button
        TextView accountState = findViewById(R.id.accountState);
        Button unBanButton = (Button) findViewById(R.id.unBan_btn);
        String ban= "banned";
        a.setAccountState(ban);
        txtView.setVisibility(View.VISIBLE);
        String forAccountState = "Account State: " + a.getAccountState();
        accountState.setText(forAccountState);
        unBanButton.setVisibility(View.VISIBLE);
        Button onClick = (Button) findViewById(R.id.ban_btn);
        onClick.setVisibility(View.INVISIBLE);
        txtView.setText("This account has been banned");
        txtView.setVisibility(View.VISIBLE);
        finish();
        startActivity(getIntent());
        try {
            db.updateAccount(a);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update account " +
                    e.toString());
        } catch (NoSuchUserException e) { // this shouldn't happen
            txtView.setText("Your account doesn't exist");
        }
    }

    public void onUnbanClick(View v) { //unban button
        TextView accountState = findViewById(R.id.accountState);
        String ban = "unbanned";
        a.setAccountState(ban);
        String forAccountState = "Account State: " + a.getAccountState();
        accountState.setText(forAccountState);
        Button onClick = (Button) findViewById(R.id.ban_btn);
        onClick.setVisibility(View.VISIBLE);
        Button unBanButton = (Button) findViewById(R.id.unBan_btn);
        unBanButton.setVisibility(View.INVISIBLE);
        txtView.setText("This account has been un-banned");
        txtView.setVisibility(View.VISIBLE);
        finish();
        startActivity(getIntent());
        try {
            db.updateAccount(a);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update account " +
                    e.toString());
        } catch (NoSuchUserException e) { // this shouldn't happen
            txtView.setText("Your account doesn't exist");
        }
    }}



