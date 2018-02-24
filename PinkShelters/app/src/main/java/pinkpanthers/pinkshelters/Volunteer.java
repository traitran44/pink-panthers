package pinkpanthers.pinkshelters;

/**
 * Created by Chau Phan on 2/18/18.
 */

public class Volunteer extends Account {
    private String shelterID;
    private String assignmentId;

    public Volunteer (String username,
                      String password,
                      String name,
                      String accountState,
                      String email,
                      int userId) {
        super(username, password, name, accountState, email, userId);
    }

    // setter/getter deals with Volunteer working at a specific shelter
    public String getShelterID() {
        return shelterID;
    }
    public void setShelterID(String shelterID) {
        this.shelterID = shelterID;
    }

    // setter/getter deals with volunteer getting assignment from Admin or homeless
    public String getAssignment() {
        return assignmentId;
    }
    public void setAssignment(String assignmentId) {
        this.assignmentId = assignmentId;
    }
}



