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
     * @throws NoSuchUserException when the id pass in the doesn't exist, no rows get updated
     */
    void updateShelterOccupancy(int shelterId, int occupancy) throws SQLException, NoSuchUserException;

    /**
     * method that update user's family members for M8
     * @param accountId user.
     * @param familyMemberNumber the total amount of family members that go with the user
     *                           {@code familyMemberNumber} default = 0;
     * @throws SQLException in case something with database connection goes wrong
     * @throws NoSuchUserException when the id pass in the doesn't exist, no rows get updated
     */
    void updateAccountInformationById(int accountId, int familyMemberNumber) throws SQLException, NoSuchUserException;

    /**
     * Overload method to update account information by user's id (that is created by the database)
     * @param accountId userId.
     * @param restrictionsMatch restrictions are stored as a string, if there are more than one
     *                          restrictions match, store all elements as space-separated string
     *                          Example: user matches 'all_women', 'all_children' restrictions
     *                          {@code restrictionsMatch} should be "all_women all_children"
     *                          {@code restrictionsMatch} default = NULL
     * @throws SQLException in case something with database connection goes wrong
     * @throws NoSuchUserException when the id pass in the doesn't exist, no rows get updated
     */
    void updateAccountInformationById(int accountId, List<String> restrictionsMatch) throws SQLException, NoSuchUserException;

    /**
     * update the shelter that this account checks in
     * @param accountId userId.
     * @param shelterId the id of shelter (created by the database) that this accounts successfully checks in
     * @throws SQLException in case something with database connection goes wrong
     * @throws NoSuchUserException when the id pass in the doesn't exist, no rows get updated
     */
    void updateShelterIdInAccountsTable(int accountId, int shelterId) throws SQLException, NoSuchUserException;
}
