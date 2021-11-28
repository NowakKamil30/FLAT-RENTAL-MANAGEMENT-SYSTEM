package pl.kamilnowak.flatrentalmanagementsystem.apartmet.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import pl.kamilnowak.flatrentalmanagementsystem.view.Views;

@Getter
@Setter
public class ApartmentDHO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String name;
    @JsonView(Views.Public.class)
    private String description;
    @JsonView(Views.Public.class)
    private double latitude;
    @JsonView(Views.Public.class)
    private double longitude;
    @JsonView(Views.Public.class)
    private String country;
    @JsonView(Views.Public.class)
    private String postcode;
    @JsonView(Views.Public.class)
    private String city;
    @JsonView(Views.Public.class)
    private String street;
    @JsonView(Views.Public.class)
    private String houseNumber;

    public ApartmentDHO() {
    }

    @Builder
    public ApartmentDHO(Long id, String name, String description, double latitude, double longitude, String country, String postcode, String city, String street, String houseNumber) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.postcode = postcode;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }
}
