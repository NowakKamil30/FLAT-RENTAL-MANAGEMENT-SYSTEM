package pl.kamilnowak.flatrentalmanagementsystem.mail.exception;

public class EmailSendException extends RuntimeException {
    public EmailSendException(String message) {
        super(message);
    }
}
