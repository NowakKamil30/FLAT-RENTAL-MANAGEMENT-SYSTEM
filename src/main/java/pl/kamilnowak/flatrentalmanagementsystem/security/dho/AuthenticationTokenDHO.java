package pl.kamilnowak.flatrentalmanagementsystem.security.dho;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kamilnowak.flatrentalmanagementsystem.view.Views;

@Getter
@Setter
public class AuthenticationTokenDHO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String token;
    @JsonView(Views.Public.class)
    private String device;
    @JsonView(Views.Public.class)
    private Long userId;

    public AuthenticationTokenDHO() {
    }

    @Builder
    public AuthenticationTokenDHO(Long id, String token, String device, Long userId) {
        this.id = id;
        this.token = token;
        this.device = device;
        this.userId = userId;
    }
}
