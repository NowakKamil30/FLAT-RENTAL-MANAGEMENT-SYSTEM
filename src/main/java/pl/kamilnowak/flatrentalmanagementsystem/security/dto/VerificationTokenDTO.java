package pl.kamilnowak.flatrentalmanagementsystem.security.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kamilnowak.flatrentalmanagementsystem.util.view.Views;

import java.time.LocalDateTime;

@Getter
@Setter
public class VerificationTokenDTO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String token;
    @JsonView(Views.Private.class)
    private Long loginUserId;
    @JsonView(Views.Public.class)
    private LocalDateTime createTime;

    public VerificationTokenDTO() {
    }

    @Builder
    public VerificationTokenDTO(Long id, String token, Long loginUserId, LocalDateTime createTime) {
        this.id = id;
        this.token = token;
        this.loginUserId = loginUserId;
        this.createTime = createTime;
    }
}
