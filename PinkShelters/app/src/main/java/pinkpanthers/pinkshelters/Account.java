package pinkpanthers.pinkshelters;



public class Account {

    private String Username;
    private String Password;
    private String AccountState;
    private String Email;
    private static int userID;

    // should not have Account() that puts in null.
    public Account() {
        this(null, null, null, null);
    }

    public Account(String username, String password, String accountState, String email){
        Username = username;
        Password = password;
        AccountState = accountState;
        Email = email;
        userID++;
    }

    public void setUsername (String username){

        Username = username;
    }

    public void setPassword (String password){

        Password = password;
    }

    public void setAccountState (String accountState){

        AccountState = accountState;
    }

    public void setEmail (String email){

        Email = email;
    }

    public int getUserID () {

        return userID;
    }

    public String getUsername () {

        return Username;
    }

    public String getPassword () {

        return Password;
    }

    public String getAccountState () {
        return AccountState;
    }

    public String getEmail () {

        return Email;
    }

}

