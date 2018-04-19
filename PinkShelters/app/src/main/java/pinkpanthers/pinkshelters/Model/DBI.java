package pinkpanthers.pinkshelters.Model;

import java.sql.SQLException;
import java.util.List;

/**
 * interface for MySQL database
 */
public interface DBI {
    /**
     * createAccount a new account/assignment
     *
     */
    void createAccount(String type,
                       String username,
                       String password,
                       String name,
                       String email) throws UniqueKeyError;

    /**
     * to retrieve an account by its username (unique)
     *
     * @param username unique identification - username of account
     * @return an account object that holds information related to that account
     * @throws NoSuchUserException when there is no account with that username
     */
    Account getAccountByUsername(String username) throws NoSuchUserException;

    /**
     * to get all the accounts in the database
     *
     * @return list of account objects
     */
    List<Account> getAllAccounts();

    /**
     * to get all the shelters in the database
     *
     * @return list of Shelter objects
     */
    List<Shelter> getAllShelters();

    /**
     * to get a shelter by its ids
     *
     * @param id unique identification
     * @return Shelter object with associated id
     * @throws NoSuchUserException when the id pass in the database doesn't exist,
     *                             no rows get updated
     */
    Shelter getShelterById(int id) throws NoSuchUserException;

    /**
     * to retrieve shelter by its restriction
     *
     * @param restriction gender_restriction and/or age_range_restriction
     * @return a list of shelters that share the same restriction
     * @throws NoSuchUserException when there is no shelter with that restriction
     */
    List<Shelter> getShelterByRestriction(String restriction) throws NoSuchUserException;

    /**
     * to retrieve a shelter by its name, used for searching by name (fuzzy search)
     * should not be implemented in anywhere else
     *
     * @param shelterName shelter name is not unique
     * @return a list of shelter that share that substring
     * @throws NoSuchUserException when there is no shelter with that name
     */
    List<Shelter> getShelterByName(String shelterName) throws NoSuchUserException;

    /**
     * to create a shelter object
     *
     * @param shelterName  name of the shelter
     * @param capacity     the maximum beds allowed in a shelter, for displaying purpose only
     *                     use updated_capacity for correct calculation
     * @param specialNotes specific amenities of a shelter
     * @param latitude     location attribute
     * @param longitude    location attribute
     * @param phoneNumber  phone number of a shelter, in (###) ###-####
     * @param restrictions default to empty string if nothing is enter
     * @param address      address of a shelter.
     * @return a shelter object
     */
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
     *
     * @param shelterId a unique id created by the database
     * @param occupancy the updated occupancy (incremented by number of beds requested)
     * @throws SQLException        in case something with database connection goes wrong
     * @throws NoSuchUserException when the id pass in the database doesn't exist,
     *                             no rows get updated
     */
    void updateShelterOccupancy(int shelterId, int occupancy) throws SQLException,
                                                                    NoSuchUserException;

    /**
     * update the the whole account's (whichever is editable, such as
     * NOT username or userId) attributes
     *
     * @param user the user is currently logged in
     * @throws SQLException        in case the query interrupted
     * @throws NoSuchUserException when the id pass in the database doesn't exist,
     *                             no rows get updated
     */
    void updateAccount(Account user) throws SQLException, NoSuchUserException;

    /**
     * delete an account by its username (unique key)
     *
     * @param username username of the account
     * @throws SQLException        in case the query interrupted
     * @throws NoSuchUserException when username doesn't exist, the query will execute
     *                             successfully but return 0 as # of rows
     *                             got updated in the database
     */
    void deleteAccount(String username) throws SQLException, NoSuchUserException;

    /**
     * currently just only update the rating,
     * will need to modify later if need to update more fields in shelter
     * @param shelter shelter to update
     * @throws SQLException        in case the query interrupted
     * @throws NoSuchUserException when username doesn't exist, the query will execute
     *                             successfully but return 0 as # of rows
     *                             got updated in the database
     */
    void updateShelter(Shelter shelter) throws SQLException, NoSuchUserException;
}
