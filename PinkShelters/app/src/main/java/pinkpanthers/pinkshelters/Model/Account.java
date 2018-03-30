package pinkpanthers.pinkshelters.Model;

// Parent class of Homeless, Volunteer , Administrator
public abstract class Account {
    private String username;
    private String password;
    private String accountState;
    private String email;
    private String name;
    private int userId;

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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }
}



