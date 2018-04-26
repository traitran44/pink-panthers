package pinkpanthers.pinkshelters.Model;

/**
 * Parent class of Homeless, Volunteer , Administrator
 */
public abstract class Account {
    private String username;
    private String password;
    private String accountState;
    private String email;
    private String name;
    private int userId;

    /**
     * constructor
     *
     * @param username     username of account
     * @param password     password of account
     * @param name         name of owner of account
     * @param accountState active or blocked
     * @param email        email address
     * @param userId       unique identity created by the database
     */
    Account(String username,
            String password,
            String name,
            String accountState,
            String email,
            int userId) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.accountState = accountState;
        this.email = email;
        this.userId = userId;
    }

    /**
     * set new username
     *
     * @param username username of account
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get current username
     *
     * @return username of account
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * set new password
     *
     * @param password new password of account
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * get password of account
     *
     * @return current password of account
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * set new name of owner of account
     *
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get name of the owner of account
     *
     * @return current name of the owner
     */
    public String getName() {
        return this.name;
    }

    /**
     * set account state to blocked or active
     *
     * accountState blocked or active or not_verified
     */
    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }

    /**
     * get account state
     *
     * @return current account state
     */
    public String getAccountState() {
        return this.accountState;
    }

    /**
     * set new email address
     *
     * @param email new address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get current email address
     *
     * @return current email address
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * set userId of the account
     *
     * @param userId user identification
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * get current user identification of the account
     *
     * @return the current user id
     */
    public int getUserId() {
        return this.userId;
    }
}



