package pinkpanthers.pinkshelters;

// Parent class of Homeless, Volunteer , Administrator
public abstract class Account {
    private String userName;
    private String password;
    private String accountState;
    private String email;
    private String name;
    private String userId;

    Account(String userName,
                   String password,
                   String name,
                   String accountState,
                   String email,
                   String userId) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.accountState = accountState;
        this.email = email;
        this.name = name;
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getUsername() {
        return this.userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }

    public String getAccountState() {
        return this.accountState;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }
}


}

