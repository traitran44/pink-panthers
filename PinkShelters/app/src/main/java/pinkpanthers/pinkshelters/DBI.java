package pinkpanthers.pinkshelters;

import java.util.List;

/**
 * Created by hdang on 2/19/18.
 */

public interface DBI {
    /**
     * createAccount a new account/assignment
     *
     * @return if an account is added to the database
     */
    Account createAccount(String type, String username, String password, String name, String email) throws UniqueKeyError;

    Account getAccountByUsername(String username) throws NoSuchUserException;

    List<Account> getAllAccounts();

    List<Shelter> getAllShelters();

    Shelter getShelterById(int id) throws NoSuchUserException;

    List<Shelter> getShelterByRestriction(String restriction) throws NoSuchUserException;

    List<Shelter> getShelterByName(String shelterName) throws NoSuchUserException;

    int[] getShelterVacancyById(int shelterId) throws NoSuchUserException;

    Shelter createShelter(String shelterName,
                          String capacity,
                          String specialNotes,
                          double latitude,
                          double longitude,
                          String phoneNumber,
                          String restrictions,
                          String address);
}
