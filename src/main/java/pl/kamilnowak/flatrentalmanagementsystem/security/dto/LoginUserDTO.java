package pl.kamilnowak.flatrentalmanagementsystem.security.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kamilnowak.flatrentalmanagementsystem.security.type.TypeAccount;
import pl.kamilnowak.flatrentalmanagementsystem.util.view.Views;

@Getter
@Setter
public class LoginUserDTO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String mail;
    @JsonView(Views.Private.class)
    private boolean isEnable;
    @JsonView(Views.Private.class)
    private TypeAccount role;

    public LoginUserDTO() {
    }

    @Builder
    public LoginUserDTO(Long id, String mail, boolean isEnable, TypeAccount role) {
        this.id = id;
        this.mail = mail;
        this.isEnable = isEnable;
        this.role = role;
    }
}
