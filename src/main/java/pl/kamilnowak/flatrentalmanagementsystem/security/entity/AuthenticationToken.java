package pl.kamilnowak.flatrentalmanagementsystem.security.entity;

import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class AuthenticationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String token;
    @NotNull
    private String device;
    @Column(name = "create_time")
    private LocalDateTime createTime = LocalDateTime.now();
    @ManyToOne
    private UserData userData;

    public AuthenticationToken() {
    }

    @Builder
    public AuthenticationToken(Long id, String token, String device, LocalDateTime createTime, UserData userData) {
        this.id = id;
        this.token = token;
        this.device = device;
        this.createTime = createTime;
        this.userData = userData;
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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
