package pinkpanthers.pinkshelters;

/**
 * Created by Chau Phan on 2/18/18.
 */

public class Volunteer extends Account {
    private String volunteerId;
    private String adminId;
    private String assignment;

    public Volunteer (String userName, String password, String accountState, String email,
                      String name, String volunteerId, String ShelterId, String adminId,
                      String assignment) {
        super(userName, password, accountState, email, name, ShelterId);
        this.volunteerId = volunteerId;
        this.adminId = adminId;
        this.assignment = assignment;
    }

    //getter for Volunteer ID
    public String getVolunteerId() {
        return volunteerId;
    }
    //setter for Volunteer ID
    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    //getter for Admin ID
    public String getAdminId() {
        return adminId;
    }
    //setter for Admin ID
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    //getter for Assignment
    public String getAssignment() {
        return assignment;
    }

    //setter for Assignment
    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String toString() {
        return volunteerId;
    }

    public boolean isDuplicate(String volunteerId) {
        return this.volunteerId.equals(volunteerId);
    }
}



