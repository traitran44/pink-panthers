package pinkpanthers.pinkshelters;

public class Homeless extends Account {
    private String shelterId; // might change to int depends on the type parsed in csv file
    private String assignmentId;

    public Homeless (String userName,
                          String password,
                          String name,
                          String accountState,
                          String email,
                          String userId){
        super(userName, password, name, accountState, email, userId);
    }

    // setter/getter deals with homeless check-in/out a shelter
    public void setShelterIdByHomeless(String shelterId) {
        this.shelterId = shelterId;
    }
    public String getShelterIdByHomeless() {
        return this.shelterId;
    }

    // setter/getter deals with homeless request service to volunteers
    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }
    public String getAssignmentId() {
        return this.assignmentId;
    }

}




