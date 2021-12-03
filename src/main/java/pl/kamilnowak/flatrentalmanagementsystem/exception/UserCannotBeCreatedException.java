package pl.kamilnowak.flatrentalmanagementsystem.exception;

public class UserCannotBeCreatedException extends Exception {
    public UserCannotBeCreatedException(String message) {
        super("user exist in system: " + message);
    }
}
