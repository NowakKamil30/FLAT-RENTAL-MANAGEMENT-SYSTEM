package pl.kamilnowak.flatrentalmanagementsystem.exception.type;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super("Not found object" + message);
    }
}
