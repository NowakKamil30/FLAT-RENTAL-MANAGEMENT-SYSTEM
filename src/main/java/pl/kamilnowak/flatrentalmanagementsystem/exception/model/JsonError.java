package pl.kamilnowak.flatrentalmanagementsystem.exception.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonError {
    private String message;

    public JsonError() {
    }

    @Builder
    public JsonError(String message, String errorCode) {
        this.message = message;
    }
}
