package pl.kamilnowak.flatrentalmanagementsystem.security.entity;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.kamilnowak.flatrentalmanagementsystem.security.type.TypeAccount;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = LoginUser.TABLE_NAME)
public class LoginUser implements UserDetails {
    public static final String TABLE_NAME =  "login_users";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String mail;
    private String password;
    @Column(name = "is_enable")
    private boolean isEnable = false;
    @Enumerated(value = EnumType.STRING)
    private TypeAccount role = TypeAccount.USER;
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private User user;

    public LoginUser() {
    }

    @Builder
    public LoginUser(Long id, String mail, String password, boolean isEnable, TypeAccount role, User user) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.isEnable = isEnable;
        this.role = role;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public TypeAccount getRole() {
        return role;
    }

    public void setRole(TypeAccount role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
