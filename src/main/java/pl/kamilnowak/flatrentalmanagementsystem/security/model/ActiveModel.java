package pl.kamilnowak.flatrentalmanagementsystem.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveModel {
    private boolean isActive;

    public ActiveModel() {
    }

    @Builder
    public ActiveModel(boolean isActive) {
        this.isActive = isActive;
    }
}
