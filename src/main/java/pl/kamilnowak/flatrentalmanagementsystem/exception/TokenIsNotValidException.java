package pl.kamilnowak.flatrentalmanagementsystem.exception;

public class TokenIsNotValidException extends Exception {
    public TokenIsNotValidException() {
        super("token is not valid");
    }
}
