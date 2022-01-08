package pl.kamilnowak.flatrentalmanagementsystem.exception.type;

public class TokenIsNotValidException extends Exception {
    public TokenIsNotValidException() {
        super("token is not valid");
    }
}
