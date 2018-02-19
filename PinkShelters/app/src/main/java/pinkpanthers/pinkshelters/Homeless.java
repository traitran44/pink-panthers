package pinkpanthers.pinkshelters;
import pinkpanthers.pinkshelters.Account;

public class Homeless extends Account {

    public Homeless (String userName,
                          String password,
                          String name,
                          String accountState,
                          String email,
                          String ShelterId){
        super(userName, password, name, accountState, email, ShelterId);


        

    }

    //getter for name of homeless people
    public String getNameOfHomeless() {
        return super.getName();
    }

    //setter for name of homeless people
    public void setNameOftHomeless(String newName) {
        super.setName(newName);
    }

}




