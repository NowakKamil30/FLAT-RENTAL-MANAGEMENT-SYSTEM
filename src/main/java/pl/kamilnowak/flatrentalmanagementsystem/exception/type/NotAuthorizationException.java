package pl.kamilnowak.flatrentalmanagementsystem.exception.type;

public class NotAuthorizationException extends Exception {
    public NotAuthorizationException() {
        super("you don't have access to this resource");
    }
}
