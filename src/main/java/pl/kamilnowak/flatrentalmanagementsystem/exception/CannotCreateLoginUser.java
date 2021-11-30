package pl.kamilnowak.flatrentalmanagementsystem.exception;

public class CannotCreateLoginUser extends RuntimeException {
    public CannotCreateLoginUser(String reasone) {
        super("cannot create loginUser because: " + reasone);
    }
}
