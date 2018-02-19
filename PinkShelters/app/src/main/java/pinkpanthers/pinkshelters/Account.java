package pinkpanthers.pinkshelters;

// Parent class of Homeless, Volunteer , Administrator
public abstract class Account {
    private String userName;
    private String password;
    private String accountState;
    private String email;
    private String name;
    private String ShelterId;
    private int userID;

    private static int currentMaxID = 0;

    public Account() {
        this("", "", "", "", "", "");
    }

    public Account(String userName,
                   String password,
                   String name,
                   String accountState,
                   String email,
                   String ShelterId) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.accountState=accountState;
        this.email=email;
        this.name=name;
        this.ShelterId=ShelterId;
        currentMaxID++;
        this.userID = currentMaxID;

    }

    public void setUsername (String username) {
        this.userName = username;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccountState (String accountState) {
        this.accountState = accountState;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public int getUserID () {
        return this.getUserID();
    }

    public String getUsername () {
        return this.userName;
    }

    public String getPassword () {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public String getAccountState () {
        return this.accountState;
    }

    public String getEmail() {
        return this.email;
    }
    public String getShelterID() {
        return this.ShelterId;
    }
    //setter for shelterID
    public void setShelterID(String ShelterId) {
        this.ShelterId = ShelterId;
    }
}

