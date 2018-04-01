package pinkpanthers.pinkshelters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import pinkpanthers.pinkshelters.Model.Account;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.NoSuchUserException;

public class DbUnitTest {
    private DBI db;
    private Account account;

    public static final int TIMEOUT = 300;

    @Before
    public void setUp() {
        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
    }

    @Test(expected = NullPointerException.class)
    public void testNullForUpdateAccount() throws SQLException, NoSuchUserException {
        // account is null
        account = null;
        db.updateAccount(account);

        // account with invalid id (negative)
        // account with invalid id (positive)
        // account with valid id
        // account is instance of Homeless
        // account is instance of Volunteer
        // account is instance of Admin
    }

}
