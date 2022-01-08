package pl.kamilnowak.flatrentalmanagementsystem.security.dho;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kamilnowak.flatrentalmanagementsystem.util.view.Views;

import java.time.LocalDateTime;

@Getter
@Setter
public class VerificationTokenDHO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String token;
    @JsonView(Views.Private.class)
    private Long loginUserId;
    @JsonView(Views.Public.class)
    private LocalDateTime createTime;

    public VerificationTokenDHO() {
    }

    @Builder
    public VerificationTokenDHO(Long id, String token, Long loginUserId, LocalDateTime createTime) {
        this.id = id;
        this.token = token;
        this.loginUserId = loginUserId;
        this.createTime = createTime;
    }
}
