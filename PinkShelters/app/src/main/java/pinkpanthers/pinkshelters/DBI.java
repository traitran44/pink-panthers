package pinkpanthers.pinkshelters;

import java.util.List;

/**
 * Created by hdang on 2/19/18.
 */

public interface DBI {
    /**
     * createAccount a new account/assignment
     * @return if an account is added to the database
     */
    Account createAccount(String type, String username, String password, String name, String email) throws UniqueKeyError;
    Account getAccountByUsername(String username) throws NoSuchUserException;
    List<Account> getAllAccounts();

    Shelter createShelter(String shelterName,
                          String capacity,
                          String specialNotes,
                          double latitude,
                          double longitude,
                          String phoneNumber,
                          String restrictions,
                          String address);
}
