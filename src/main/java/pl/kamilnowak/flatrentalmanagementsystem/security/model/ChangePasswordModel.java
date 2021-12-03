package pl.kamilnowak.flatrentalmanagementsystem.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordModel {
    private boolean isPasswordChange;

    public ChangePasswordModel() {
    }

    @Builder
    public ChangePasswordModel(boolean isPasswordChange) {
        this.isPasswordChange = isPasswordChange;
    }
}
