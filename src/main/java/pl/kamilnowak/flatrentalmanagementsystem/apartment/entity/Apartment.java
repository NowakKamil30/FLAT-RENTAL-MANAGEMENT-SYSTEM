package pl.kamilnowak.flatrentalmanagementsystem.apartment.entity;

import com.sun.istack.NotNull;
import lombok.Builder;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.UserData;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = Apartment.TABLE_NAME)
public class Apartment {
    public static final String TABLE_NAME =  "apartments";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;
    @Size(max = 300)
    private String description;
    @NotNull
    private String country;
    @NotNull
    private String postcode;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String houseNumber;
    @OneToMany(cascade  = CascadeType.ALL, mappedBy = "apartment")
    private List<Tenant> tenants;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartment")
    private List<Image> images;
    @ManyToOne
    private UserData userData;

    public Apartment() {
    }

    @Builder
    public Apartment(Long id, String name, String description, String country, String postcode, String city, String street, String houseNumber, List<Tenant> tenants, List<Image> images, UserData userData) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.country = country;
        this.postcode = postcode;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.tenants = tenants;
        this.images = images;
        this.userData = userData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
