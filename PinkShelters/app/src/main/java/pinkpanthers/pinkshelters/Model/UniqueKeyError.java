package pinkpanthers.pinkshelters.Model;

/**
 * exception is raised when a duplicate is added
 */
public class UniqueKeyError extends Exception {
    /**
     * exception is raised when a duplicate is added
     * @param s String
     */
    public UniqueKeyError(String s) {
        super(s);
    }
}
