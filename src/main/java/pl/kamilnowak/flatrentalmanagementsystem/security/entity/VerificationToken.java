package pl.kamilnowak.flatrentalmanagementsystem.security.entity;

import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = VerificationToken.TABLE_NAME)
public class VerificationToken {
    public static final String TABLE_NAME =  "verification_tokens";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne
    private LoginUser loginUser;
    @Column(name = "create_time")
    private LocalDateTime createTime = LocalDateTime.now();

    public VerificationToken() {
    }

    @Builder
    public VerificationToken(Long id, String token, LoginUser loginUser, LocalDateTime createTime) {
        this.id = id;
        this.token = token;
        this.loginUser = loginUser;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
