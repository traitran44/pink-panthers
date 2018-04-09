package pinkpanthers.pinkshelters.Model;

/**
 * exception is raised when an invalid user/shelter is accessed
 */
public class NoSuchUserException extends Exception {
    public NoSuchUserException(String message) {
        super(message);
    }
}