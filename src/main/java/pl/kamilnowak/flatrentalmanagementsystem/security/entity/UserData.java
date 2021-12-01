package pl.kamilnowak.flatrentalmanagementsystem.security.entity;

import lombok.Builder;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity.Apartment;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = UserData.TABLE_NAME)
public class UserData {
    public static final String TABLE_NAME =  "user_datas";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "active_account_data")
    private LocalDateTime activeAccountData;
    @Column(name = "create_user_data")
    private LocalDateTime createUserData = LocalDateTime.now();
    @OneToOne
    private LoginUser loginUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userData")
    private List<Apartment> apartments;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userData")
    private List<AuthenticationToken> authenticationTokens;

    public UserData() {
    }

    @Builder
    public UserData(Long id, String firstName, String lastName, LocalDateTime activeAccountData, LocalDateTime createUserData, LoginUser loginUser, List<Apartment> apartments, List<AuthenticationToken> authenticationTokens) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.activeAccountData = activeAccountData;
        this.createUserData = createUserData;
        this.loginUser = loginUser;
        this.apartments = apartments;
        this.authenticationTokens = authenticationTokens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getActiveAccountData() {
        return activeAccountData;
    }

    public void setActiveAccountData(LocalDateTime activeAccountData) {
        this.activeAccountData = activeAccountData;
    }

    public LocalDateTime getCreateUserData() {
        return createUserData;
    }

    public void setCreateUserData(LocalDateTime createUserData) {
        this.createUserData = createUserData;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

    public List<AuthenticationToken> getAuthenticationTokens() {
        return authenticationTokens;
    }

    public void setAuthenticationTokens(List<AuthenticationToken> authenticationTokens) {
        this.authenticationTokens = authenticationTokens;
    }
}
