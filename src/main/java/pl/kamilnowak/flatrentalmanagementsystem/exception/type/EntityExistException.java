package pl.kamilnowak.flatrentalmanagementsystem.exception.type;

public class EntityExistException extends RuntimeException {
    public EntityExistException() {
        super("entity exist in system");
    }
}
