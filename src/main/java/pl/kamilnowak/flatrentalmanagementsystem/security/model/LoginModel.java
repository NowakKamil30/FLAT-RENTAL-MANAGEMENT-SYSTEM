package pl.kamilnowak.flatrentalmanagementsystem.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kamilnowak.flatrentalmanagementsystem.security.type.TypeAccount;

@Getter
@Setter
public class LoginModel {

    private Long id;
    private String token;
    private TypeAccount role;
    private String mail;

    public LoginModel() {
    }

    @Builder
    public LoginModel(Long id, String token, TypeAccount role, String mail) {
        this.id = id;
        this.token = token;
        this.role = role;
        this.mail = mail;
    }
}
