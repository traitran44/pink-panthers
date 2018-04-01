package pinkpanthers.pinkshelters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;

import pinkpanthers.pinkshelters.Model.Account;
import pinkpanthers.pinkshelters.Model.Admin;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Homeless;
import pinkpanthers.pinkshelters.Model.NoSuchUserException;
import pinkpanthers.pinkshelters.Model.Volunteer;

public class DbUnitTest {
    private DBI db;
    private Account account;

    @Before
    public void setUp() {
        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
    }

    // account as null object
    @Test(expected = NullPointerException.class)
    public void testNullForUpdateAccount() throws SQLException, NoSuchUserException {
        // account is null
        account = null;
        db.updateAccount(account);
    }

    // account with invalid id (negative)
    @Test(expected = NoSuchUserException.class)
    public void testNegIdUpdateAccount() throws SQLException, NoSuchUserException {
        account = new Volunteer("test123", "test123", "test",
                "blocked", "test123@", -1);
        db.updateAccount(account);

    }

    // account with invalid id (zero)
    @Test(expected = NoSuchUserException.class)
    public void testZeroIdUpdateAccount() throws SQLException, NoSuchUserException {
        account = new Admin("test123", "test123", "test",
                "blocked", "test123@", 0);
        db.updateAccount(account);
    }

    // account with invalid id (positive)
    @Test(expected = NoSuchUserException.class)
    public void testPosInvalidIdUpdateAccount() throws SQLException, NoSuchUserException {
        account = new Homeless("test123", "test123", "test",
                "blocked", "test123@", 1000);
        db.updateAccount(account);
    }

    @Test
    public void testValidIdUpdateAccount() throws SQLException, NoSuchUserException {
        account = db.getAccountByUsername("asdfasdf");
        ((Homeless) account).setFamilyMemberNumber(20);
        ((Homeless) account).setRestrictionsMatch(Arrays.asList("test_gender", "test_age"));
        ((Homeless) account).setShelterId(-1);
        db.updateAccount(account);

        Account updatedAccount = db.getAccountByUsername("asdfasdf");
        assertEquals(20, ((Homeless) updatedAccount).getFamilyMemberNumber());

        String[] expectedRestrictions = {"test_gender", "test_age"};
        assertArrayEquals(expectedRestrictions, ((Homeless) updatedAccount).getRestrictionsMatch().toArray());

        assertEquals(-1, ((Homeless) updatedAccount).getShelterId());
    }


}
