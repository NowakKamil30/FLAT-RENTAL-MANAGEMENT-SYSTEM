package pl.kamilnowak.flatrentalmanagementsystem.apartmet.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import pl.kamilnowak.flatrentalmanagementsystem.view.Views;

import java.util.List;

@Data
@Builder
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
    private List<Long> tenantIds;
    @JsonView(Views.Public.class)
    private List<Long> imageIds;
}
