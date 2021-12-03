package pl.kamilnowak.flatrentalmanagementsystem.apartment.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kamilnowak.flatrentalmanagementsystem.view.Views;

import java.time.LocalDate;

@Getter
@Setter
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

    public ImageDHO() {
    }

    @Builder
    public ImageDHO(Long id, String photo, String title, LocalDate uploadDate, Long apartmentId) {
        this.id = id;
        this.photo = photo;
        this.title = title;
        this.uploadDate = uploadDate;
        this.apartmentId = apartmentId;
    }
}
