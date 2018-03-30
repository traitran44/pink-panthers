package pinkpanthers.pinkshelters.Controller;

public class NoSuchUserException extends Exception {
    public NoSuchUserException(String message) {
        super(message);
    }
}