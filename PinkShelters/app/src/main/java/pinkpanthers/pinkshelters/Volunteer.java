package pinkpanthers.pinkshelters;

/**
 * Created by Chau Phan on 2/18/18.
 */

public class Volunteer extends Account {
    private String volunteerId;
    private String shelterId;
    private String adminId;

    public Volunteer (String userName, String password, String accountState, String email,
                      String name, String volunteerId, String ShelterId, String adminId) {
        super(userName, password, accountState, email, name, ShelterId);
        this.volunteerId = volunteerId;
        this.shelterId = ShelterId;
        this.adminId = adminId;
    }

    //getter for Volunteer ID
    public String getVolunteerId() {
        return volunteerId;
    }
    //setter for Volunteer ID
    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    //getter for Shelter ID
    public String getShelterId() {
        return shelterId;
    }
    //setter for Shelter ID
    public void setShelterId(String shelterId) {
        this.shelterId = shelterId;
    }

    //getter for Admin ID
    public String getAdminId() {
        return adminId;
    }
    //setter for Admin ID
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String toString() {
        return volunteerId;
    }

    public boolean isDuplicate(String volunteerId) {
        return this.volunteerId.equals(volunteerId);
    }
}



