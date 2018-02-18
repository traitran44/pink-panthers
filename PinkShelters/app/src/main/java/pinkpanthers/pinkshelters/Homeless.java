package pinkpanthers.pinkshelters;
import pinkpanthers.pinkshelters.Account;

public class Homeless extends Account {
    //fields for homeless people
    private String NameOftHomeless;
    private String ShelterID;

    // THIS IS WRONG
    //overriding Account method with information from the homeless people class
    public void Account(String username, String password, String accountState, String email, String userID){
        username="homelessUsername";
        password="homelessPassword";
        accountState="homelessAccountstate";
        email="homelessEmail";
        userID="homelessUserID";
    }


    //constructor for homeless people class
    public Homeless(String nameOftHomeless, String shelterID) {
        NameOftHomeless = nameOftHomeless;
        ShelterID = shelterID;
    }
    //getter for name of homeless people
    public String getNameOftHomeless() {
        return NameOftHomeless;
    }

    //setter for name of homeless people
    public void setNameOftHomeless(String nameOftHomeless) {
        NameOftHomeless = nameOftHomeless;
    }
    //getter for shelterID
    public String getShelterID() {
        return ShelterID;
    }
    //setter for shelterID
    public void setShelterID(String shelterID) {
        ShelterID = shelterID;
    }
}




