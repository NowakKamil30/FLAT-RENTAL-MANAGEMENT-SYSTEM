package pl.kamilnowak.flatrentalmanagementsystem.exception.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonError {
    private String message;
    private String errorCode;

    public JsonError() {
    }

    @Builder
    public JsonError(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
