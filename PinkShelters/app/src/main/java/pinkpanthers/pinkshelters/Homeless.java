package pinkpanthers.pinkshelters;
import pinkpanthers.pinkshelters.Account;

public class Homeless extends Account {
    private String shelterID;

    public void Homeless (String username,
                          String password,
                          String name,
                          String accountState,
                          String email,
                          String userID,
                          String shelterID){
        super(String username, password, name, accountState,email,userID);
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




