package pinkpanthers.pinkshelters;

import java.util.List;

public class Homeless extends Account {
    private int shelterId; // might change to int depends on the type parsed in csv file
    private List<String> restrictionsMatch;
    private int familyMemberNumber;

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

    public void setFamilyMemberNumber(int familyMemberNumber) {
        this.familyMemberNumber = familyMemberNumber;
    }

    public int getFamilyMemberNumber() {
        return this.familyMemberNumber;
    }

    public void setRestrictionsMatch(List<String> restrictionsMatch) {
        this.restrictionsMatch = restrictionsMatch;
    }

    public List<String> getRestrictionsMatch() {
        return this.restrictionsMatch;
    }


}