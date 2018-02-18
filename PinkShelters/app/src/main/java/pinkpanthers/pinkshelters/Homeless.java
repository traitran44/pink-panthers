package pinkpanthers.pinkshelters;
import pinkpanthers.pinkshelters.accountClass;

public class Homeless extends Account {
    //fields for homeless people
    private String shelterID;


    //overriding Account method with information from the homeless people class
    public void Homeless (String username, String password, String accountState, String email, String userID){
        super(String username, String password, String accountState, String email, String userID);
    }


    //constructor for homeless people class
    public Homeless(String nameOftHomeless, String shelterID) {
        NameOftHomeless = nameOftHomeless;
        ShelterID = shelterID;
    }
    //getter for name of homeless people
    public String getNameOftHomeless() {
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




