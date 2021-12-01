package pl.kamilnowak.flatrentalmanagementsystem.exception;

public class CannotCreateLoginUserException extends RuntimeException {
    public CannotCreateLoginUserException(String reasone) {
        super("cannot create loginUser because: " + reasone);
    }
}
