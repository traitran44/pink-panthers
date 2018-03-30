package pinkpanthers.pinkshelters.Model;

/**
 * Created by Chau Phan on 2/18/18.
 */

public class Volunteer extends Account {
    private int shelterID;

    public Volunteer (String username,
                      String password,
                      String name,
                      String accountState,
                      String email,
                      int userId) {
        super(username, password, name, accountState, email, userId);
    }

    // setter/getter deals with Volunteer working at a specific shelter
    public int getShelterID() {
        return shelterID;
    }
    public void setShelterID(int shelterID) {
        this.shelterID = shelterID;
    }
}



