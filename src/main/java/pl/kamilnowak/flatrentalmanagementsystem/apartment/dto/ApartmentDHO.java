package pl.kamilnowak.flatrentalmanagementsystem.apartment.dto;

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
    private String country;
    @JsonView(Views.Public.class)
    private String postcode;
    @JsonView(Views.Public.class)
    private String city;
    @JsonView(Views.Public.class)
    private String street;
    @JsonView(Views.Public.class)
    private String houseNumber;
    @JsonView(Views.Public.class)
    private Long userId;

    public ApartmentDHO() {
    }

    @Builder
    public ApartmentDHO(Long id, String name, String description, String country, String postcode, String city, String street, String houseNumber, Long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.country = country;
        this.postcode = postcode;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.userId = userId;
    }
}
