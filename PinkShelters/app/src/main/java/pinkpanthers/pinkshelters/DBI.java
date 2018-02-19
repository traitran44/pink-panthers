package pinkpanthers.pinkshelters;

/**
 * Created by hdang on 2/19/18.
 */

public interface DBI {
    /**
     * create a new account/assignment
     * @return if an account is added to the database
     */
    boolean create();

    /**
     * update the database when something is added or removed
     * @return true if the database has been updated
     */
    boolean update();
    void get();
    void delete();
}
