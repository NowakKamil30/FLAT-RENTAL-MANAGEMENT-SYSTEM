package pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity;

import com.sun.istack.NotNull;
import lombok.Builder;

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
    private double latitude;
    @NotNull
    private double longitude;
    @OneToMany(cascade  = CascadeType.ALL)
    private List<Tenant> tenants;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;

    public Apartment() {
    }

    @Builder
    public Apartment(Long id, String name, String description, double latitude, double longitude, List<Tenant> tenants, List<Image> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tenants = tenants;
        this.images = images;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
}
