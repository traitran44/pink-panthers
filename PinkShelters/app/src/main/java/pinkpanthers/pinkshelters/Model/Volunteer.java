package pinkpanthers.pinkshelters.Model;

/**
 * Volunteer class, one of 3 user types
 */
public class Volunteer extends Account {
    private int shelterID;

    /**
     * constructor
     *
     * @param username     username of account
     * @param password     password of account
     * @param name         name of owner of account
     * @param accountState blocked or active
     * @param email        email address of account
     * @param userId       unique user identification in database
     */
    public Volunteer (String username,
                      String password,
                      String name,
                      String accountState,
                      String email,
                      int userId) {
        super(username, password, name, accountState, email, userId);
    }

    /**
     * get current shelter id
     * @return current shelter id
     */
    public int getShelterID() {
        return shelterID;
    }

    /**
     * set new shelter id
     * @param shelterID new shelter id
     */
    public void setShelterID(int shelterID) {
        this.shelterID = shelterID;
    }
}



