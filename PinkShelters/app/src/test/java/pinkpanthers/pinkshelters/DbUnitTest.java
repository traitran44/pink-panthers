package pinkpanthers.pinkshelters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pinkpanthers.pinkshelters.Model.Account;
import pinkpanthers.pinkshelters.Model.Admin;
import pinkpanthers.pinkshelters.Model.Db;
import pinkpanthers.pinkshelters.Model.DBI;
import pinkpanthers.pinkshelters.Model.Homeless;
import pinkpanthers.pinkshelters.Model.NoSuchUserException;
import pinkpanthers.pinkshelters.Model.Shelter;
import pinkpanthers.pinkshelters.Model.Volunteer;

public class DbUnitTest {
    private DBI db;
    private Account account;
    private Shelter shelter;

    @Before
    public void setUp() {
        db = new Db("pinkpanther", "PinkPantherReturns!");
    }

    // account as null object
    @Test(expected = NullPointerException.class)
    public void testNullForUpdateAccount() throws SQLException, NoSuchUserException {
        // account is null
        db.updateAccount(null);
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
        account = new Volunteer("test123", "test123", "test",
                "blocked", "test123@", 1000);
        db.updateAccount(account);
    }

    @Test(expected = NullPointerException.class)
    public void testPosInvalidHomelessUpdateAccount() throws SQLException, NoSuchUserException {
        account = new Homeless("test123", "test123", "test",
                "blocked", "test123@", 1000);
        // not setting shelterId, familyMemberNumber and restrictionMatch
        // NullPointerException returns in the method where restrictionMatch is null by defaults
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
        List<String> restrictionsMatch = ((Homeless) updatedAccount).getRestrictionsMatch();
        assertArrayEquals(expectedRestrictions, restrictionsMatch.toArray());

        assertEquals(-1, ((Homeless) updatedAccount).getShelterId());
    }

    //not supposed to update the username
    @Test (expected = NoSuchUserException.class)
    public void testUsernameUpdateAccount() throws SQLException, NoSuchUserException {
        account = new Volunteer("test123", "test123", "test",
                "blocked", "test123@", 55);
        db.updateAccount(account);
        account = db.getAccountByUsername("test123");
    }

    @Test
    public void testNotHomelessUpdateAccount() throws SQLException, NoSuchUserException {
        account = new Volunteer("test123", "test123", "test",
                "blocked", "test123@", 54);
        db.updateAccount(account);
        account = db.getAccountByUsername("testcase5"); // correct username ad userId 54

        assertFalse("Account is not instance of Volunteer: ", account instanceof Volunteer);
        assertTrue(account instanceof Homeless);

        assertEquals(0, ((Homeless) account).getFamilyMemberNumber());
        assertEquals(0, ((Homeless) account).getShelterId());
        String[] expectedRestriction = {""};
        List<String> restrictionsMatch = ((Homeless) account).getRestrictionsMatch();
        assertArrayEquals(expectedRestriction, restrictionsMatch.toArray());

    }

    @Test(expected = NoSuchUserException.class)
    public void testInvalidGetAccountByUsername() throws NoSuchUserException {
        account = db.getAccountByUsername("invalid");
    }

    @Test(expected = NoSuchUserException.class)
    public void testNullParamGetAccountByUsername() throws NoSuchUserException {
        account = db.getAccountByUsername(null);
    }

    @Test(expected = NoSuchUserException.class)
    public void testEmptyStringParamGetAccountByUsername() throws NoSuchUserException {
        account = db.getAccountByUsername("");
    }

    @Test
    public void testValidGetAccountByUsername() throws NoSuchUserException {
        account = db.getAccountByUsername("cphan31");
        assertEquals(6, account.getUserId());
        assertEquals("cphan31@gatech.edu", account.getEmail());
        assertEquals("cphan31", account.getUsername());
    }


    @Test(expected = NoSuchUserException.class)
    public void testNegativeParamGetShelterById() throws NoSuchUserException {
        shelter = db.getShelterById(-1);
    }

    @Test(expected = NoSuchUserException.class)
    public void testZeroParamGetShelterById() throws NoSuchUserException {
        shelter = db.getShelterById(0);
    }

    @Test(expected = NoSuchUserException.class)
    public void testInvalidParamGetShelterById() throws NoSuchUserException {
        shelter = db.getShelterById(20);
    }

    @Test(expected = NoSuchUserException.class)
    public void testHugeParamGetShelterById() throws NoSuchUserException {
        shelter = db.getShelterById(10000);
    }

    @Test
    public void testValidGetShelterById() throws NoSuchUserException {
        shelter = db.getShelterById(4);
        assertEquals("Fuqua Hall", shelter.getShelterName());
        assertEquals("Men", shelter.getRestrictions());
        assertEquals("92", shelter.getCapacity());
    }


    @Test
    public void testGetAllShelters() {
        List<Shelter> shelters = db.getAllShelters();

        assertNotNull(shelters);
        assertEquals(13, shelters.size());

        ArrayList<String> shelterNamesArr = new ArrayList<>();
        ArrayList<String> addressArr = new ArrayList<>();
        ArrayList<String> capArr = new ArrayList<>();

        // Get Values
        for (Shelter each : shelters) {
            capArr.add(each.getCapacity());
            shelterNamesArr.add(each.getShelterName());
            addressArr.add(each.getAddress());
        }
        // Test Capacity
        String[] capTest = {
                "264",
                "140",
                "450",
                "92",
                "40",
                "32 for families, 80 singles",
                "76 family rooms ",
                "80",
                "",
                "22 apartments",
                "330",
                "12",
                ""
        };

        // Test Address
        String[] addressTest = {
                "921 Howell Mill Road, Atlanta, Georgia 30318",
                "655 Ethel Street, Atlanta, Georgia 30318",
                "156 Mills Street, Atlanta, Georgia 30313",
                "144 Mills Street, Atlanta, Georgia 30313",
                "607 Peachtree Street NE Atlanta, GA 30308",
                "1300 Joseph E. Boone Blvd NW, Atlanta, GA 30314",
                "173 Boulevard Northeast, Atlanta, GA 30312",
                "1559 Johnson Road NW, Atlanta, GA 30318",
                "830 Boulevard SE, Atlanta, GA 30312",
                "34 Peachtree Street NW, Suite 700, Atlanta, GA 30303",
                "275 Pryor St. SW, Atlanta, GA 30303",
                "1230 Hightower Road NW Atlanta, GA 30318",
                "1800 Memorial Dr SE G3, Atlanta, GA 30317"
        };

        // Test Shelter Names
        String[] shelterNamesTest = {"My Sister's House",
                "The Atlanta Day Center for Women & Children",
                "The Shepherd's Inn",
                "Fuqua Hall",
                "Atlanta's Children Center",
                "Eden Village ",
                "Our House ",
                "Covenant House Georgia ",
                "Nicholas House",
                "Hope Atlanta ",
                "Gateway Center",
                "Young Adult Guidance Center ",
                "Homes of Light "};

        assertArrayEquals(shelterNamesTest, shelterNamesArr.toArray());
        assertArrayEquals(addressTest, addressArr.toArray());
        assertArrayEquals(capTest, capArr.toArray());
    }
}
