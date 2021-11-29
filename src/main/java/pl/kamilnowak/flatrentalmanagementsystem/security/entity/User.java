package pl.kamilnowak.flatrentalmanagementsystem.security.entity;

import lombok.Builder;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity.Apartment;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = User.TABLE_NAME)
public class User {
    public static final String TABLE_NAME =  "users";

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
    @OneToMany(cascade = CascadeType.ALL)
    private List<Apartment> apartments;

    public User() {
    }

    @Builder
    public User(Long id, String firstName, String lastName, LocalDateTime activeAccountData, LocalDateTime createUserData, LoginUser loginUser, List<Apartment> apartments) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.activeAccountData = activeAccountData;
        this.createUserData = createUserData;
        this.loginUser = loginUser;
        this.apartments = apartments;
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
}
