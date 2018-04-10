package pinkpanthers.pinkshelters.Model;

/**
 * exception is raised when a duplicate is added
 */
public class UniqueKeyError extends Exception {
    public UniqueKeyError(String s) {
        super(s);
    }
}
