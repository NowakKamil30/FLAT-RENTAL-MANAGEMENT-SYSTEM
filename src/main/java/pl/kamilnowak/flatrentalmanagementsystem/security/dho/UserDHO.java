package pl.kamilnowak.flatrentalmanagementsystem.security.dho;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kamilnowak.flatrentalmanagementsystem.view.Views;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDHO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String firstName;
    @JsonView(Views.Public.class)
    private String lastName;
    @JsonView(Views.Private.class)
    private LocalDateTime activeAccountData;
    @JsonView(Views.Private.class)
    private LocalDateTime createUserData = LocalDateTime.now();
    @JsonView(Views.Private.class)
    private Long loginUserId;

    public UserDHO() {
    }

    @Builder
    public UserDHO(Long id, String firstName, String lastName, LocalDateTime activeAccountData, LocalDateTime createUserData, Long loginUserId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.activeAccountData = activeAccountData;
        this.createUserData = createUserData;
        this.loginUserId = loginUserId;
    }
}
