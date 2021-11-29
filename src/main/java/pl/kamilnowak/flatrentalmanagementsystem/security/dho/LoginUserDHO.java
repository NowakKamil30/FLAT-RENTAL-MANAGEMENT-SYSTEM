package pl.kamilnowak.flatrentalmanagementsystem.security.dho;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kamilnowak.flatrentalmanagementsystem.view.Views;

@Getter
@Setter
public class LoginUserDHO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String mail;

    public LoginUserDHO() {
    }

    @Builder
    public LoginUserDHO(Long id, String mail) {
        this.id = id;
        this.mail = mail;
    }
}
