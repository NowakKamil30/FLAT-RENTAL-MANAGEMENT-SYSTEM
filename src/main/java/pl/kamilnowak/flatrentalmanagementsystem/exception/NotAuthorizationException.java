package pl.kamilnowak.flatrentalmanagementsystem.exception;

public class NotAuthorizationException extends Exception {
    public NotAuthorizationException() {
        super("you don't have access to this resource");
    }
}
