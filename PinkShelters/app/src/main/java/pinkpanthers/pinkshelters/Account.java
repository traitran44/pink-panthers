package pinkpanthers.pinkshelters;



public class Account {

    private String Username;
    private String Password;
    private String AccountState;
    private String Email;
    private static int userID;
    private int SSN;

    public static void main(String[] args) {
//code
    }

    public Account(){
        this(null, null, null, null, 0);
    }

    public Account(String username, String password, String accountState, String email, int SSN){
        Username = username;
        Password = password;
        AccountState = accountState;
        Email = email;
        this.SSN = SSN;
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

