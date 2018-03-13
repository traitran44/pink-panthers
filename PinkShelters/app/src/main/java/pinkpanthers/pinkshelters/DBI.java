package pinkpanthers.pinkshelters;

import java.sql.SQLException;
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

    Shelter createShelter(String shelterName,
                          String capacity,
                          String specialNotes,
                          double latitude,
                          double longitude,
                          String phoneNumber,
                          String restrictions,
                          String address);
    /**
     * update the number of people who have checked in the shelter
     * @param shelterId a unique id created by the database
     * @param occupancy the updated occupancy (incremented by number of beds requested)
     * @throws SQLException in case something with database connection goes wrong
     * @throws NoSuchUserException when the id pass in the database doesn't exist, no rows get updated
     */
    void updateShelterOccupancy(int shelterId, int occupancy) throws SQLException, NoSuchUserException;

    /**
     * update the the whole account's (whichever is editable, such as NOT username or userId) attributes
     * @param user the user is currently logged in
     * @throws SQLException in case the query interrupted
     * @throws NoSuchUserException when the id pass in the database doesn't exist, no rows get updated
     */
    void updateAccount (Account user) throws SQLException, NoSuchUserException;}
