package pl.kamilnowak.flatrentalmanagementsystem.exception;

public class EntityExistException extends RuntimeException {
    public EntityExistException() {
        super("entity exist in system");
    }
}
