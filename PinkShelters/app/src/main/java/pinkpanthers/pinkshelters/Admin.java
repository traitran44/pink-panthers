package pinkpanthers.pinkshelters;

public class Admin extends Account {

    private String assignmentId;
    private String shelterId; // might need adjustment to type

    public Admin(String userName,
                 String password,
                 String name,
                 String accountState,
                 String email,
                 int userId) {
        super(userName, password, name, accountState, email, userId);
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getShelterId() {
        return shelterId;
    }

    public void setShelterId(String shelterId) {
        this.shelterId = shelterId;
    }
}






