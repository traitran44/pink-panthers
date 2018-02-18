package pinkpanthers.pinkshelters;



public class Account {

    private String Username;
    private String Password;
    private String AccountState;
    private String Email;
    private String userID;
    private int SSN;

    public static void main(String[] args) {
//code
    }


    public Account(String username, String password, String accountState, String email, String userID, int SSN){
        Username = username;
        Password = password;
        AccountState = accountState;
        Email = email;
        this.userID = userID;
        this.SSN = SSN;
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

    public String getUserID () {

        return userID;
    }

    public void setUserID (String userID){

        this.userID = userID;
    }

    public void setSSN ( int SSN){

        this.SSN = SSN;
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

    public int getSSN () {

        return SSN;
    }
}

