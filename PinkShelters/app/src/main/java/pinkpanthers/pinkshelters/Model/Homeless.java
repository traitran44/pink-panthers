package pinkpanthers.pinkshelters.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Homeless class, one of 3 user type
 */
@SuppressWarnings("ALL")
public class Homeless extends Account {
    private int shelterId; // might change to int depends on the type parsed in csv file
    private List<String> restrictionsMatch;
    private int familyMemberNumber;

    /**
     * constructor
     *
     * @param username     username of account
     * @param password     password of account
     * @param name         name of owner of account
     * @param accountState blocked or active
     * @param email        email address of account
     * @param userId       user id of account in database
     */
    public Homeless(String username,
                    String password,
                    String name,
                    String accountState,
                    String email,
                    int userId) {
        super(username, password, name, accountState, email, userId);
    }

    /**
     * set new shelterId
     *
     * @param shelterId id of a shelter in database
     */
    public void setShelterId(int shelterId) {
        this.shelterId = shelterId;
    }

    /**
     * get shelterId
     *
     * @return shelter id that the current homeless checks in
     */
    public int getShelterId() {
        return this.shelterId;
    }

    /**
     * set new total family member number
     *
     * @param familyMemberNumber number of family members including self
     */
    public void setFamilyMemberNumber(int familyMemberNumber) {
        this.familyMemberNumber = familyMemberNumber;
    }

    /**
     * get current total family member number
     *
     * @return number of family members including self
     */
    public int getFamilyMemberNumber() {
        return this.familyMemberNumber;
    }

    /**
     * set new restriction match
     *
     * @param restrictionsMatch list of restrictions that current homeless
     *                          matches with shelter
     */
    public void setRestrictionsMatch(List<String> restrictionsMatch) {
        this.restrictionsMatch = Collections.unmodifiableList(
                new ArrayList<String>(restrictionsMatch));
    }

    /**
     * get current list of restriction match
     *
     * @return list of restrictions that current homeless
     * matches with shelter
     */
    public List<String> getRestrictionsMatch() {
        return Collections.unmodifiableList(restrictionsMatch);
    }


}