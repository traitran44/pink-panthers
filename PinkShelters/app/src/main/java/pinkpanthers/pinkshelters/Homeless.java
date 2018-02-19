package pinkpanthers.pinkshelters;
import pinkpanthers.pinkshelters.Account;

public class Homeless extends Account {
    private String shelterID;

    public Homeless (String userName,
                          String password,
                          String name,
                          String accountState,
                          String email,
                          String shelterID){
        super(userName, password, name, accountState, email);
        this.shelterID = shelterID;
    }

    //getter for name of homeless people
    public String getNameOfHomeless() {
        return super.getName();
    }

    //setter for name of homeless people
    public void setNameOftHomeless(String newName) {
        super.setName(newName);
    }
    //getter for shelterID
    public String getShelterID() {
        return this.shelterID;
    }
    //setter for shelterID
    public void setShelterID(String shelterID) {
        this.shelterID = shelterID;
    }
}




