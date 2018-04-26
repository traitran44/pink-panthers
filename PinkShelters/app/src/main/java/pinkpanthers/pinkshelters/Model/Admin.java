package pinkpanthers.pinkshelters.Model;

/**
 * Admin class as one of 3 user types
 */
public class Admin extends Account {
    /**
     * constructor
     *
     * @param username     username of account
     * @param password     password of account
     * @param name         name of owner of account
     * @param accountState blocked or active or not_verified
     * @param email        email address of account
     * @param userId       unique user identification in database
     */
    public Admin(String username,
                 String password,
                 String name,
                 String accountState,
                 String email,
                 int userId) {
        super(username, password, name, accountState, email, userId);
    }
}

