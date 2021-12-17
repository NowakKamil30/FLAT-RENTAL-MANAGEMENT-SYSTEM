package pl.kamilnowak.flatrentalmanagementsystem.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfoModel {
    private String firstName;
    private String lastName;
    private Long loginUserId;
    private String mail;

    public UserInfoModel() {
    }

    @Builder
    public UserInfoModel(String firstName, String lastName, Long loginUserId, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginUserId = loginUserId;
        this.mail = mail;
    }
}
