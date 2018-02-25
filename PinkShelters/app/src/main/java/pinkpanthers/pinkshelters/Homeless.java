package pinkpanthers.pinkshelters;

public class Homeless extends Account {
    private int shelterId; // might change to int depends on the type parsed in csv file

    public Homeless(String username,
                    String password,
                    String name,
                    String accountState,
                    String email,
                    int userId) {
        super(username, password, name, accountState, email, userId);
    }

    // setter/getter deals with homeless check-in/out a shelter
    public void setShelterId(int shelterId) {
        this.shelterId = shelterId;
    }

    public int getShelterId() {
        return this.shelterId;
    }

}
