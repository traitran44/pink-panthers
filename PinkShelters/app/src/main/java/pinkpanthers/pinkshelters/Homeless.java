package pinkpanthers.pinkshelters;

public class Homeless extends Account {
    private String shelterId; // might change to int depends on the type parsed in csv file
    private String assignmentId;

    public Homeless (String userName,
                          String password,
                          String name,
                          String accountState,
                          String email,
<<<<<<< HEAD
                          String ShelterId){
        super(userName, password, name, accountState, email, ShelterId);


        

=======
                          String userId){
        super(userName, password, name, accountState, email, userId);
>>>>>>> 259fd15498276be3c53419c22dcf99dad34db960
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




