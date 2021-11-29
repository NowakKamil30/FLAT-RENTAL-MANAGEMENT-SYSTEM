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
    private User user;

    public AuthenticationToken() {
    }

    @Builder
    public AuthenticationToken(Long id, String token, String device, LocalDateTime createTime, User user) {
        this.id = id;
        this.token = token;
        this.device = device;
        this.createTime = createTime;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
