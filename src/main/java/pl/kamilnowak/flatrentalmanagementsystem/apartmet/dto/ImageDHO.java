package pl.kamilnowak.flatrentalmanagementsystem.apartmet.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import pl.kamilnowak.flatrentalmanagementsystem.view.Views;

import java.time.LocalDate;

@Data
@Builder
public class ImageDHO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Private.class)
    private String photo;
    @JsonView(Views.Public.class)
    private String title;
    @JsonView(Views.Public.class)
    private LocalDate uploadDate;
    @JsonView(Views.Private.class)
    private Long apartmentId;
}
