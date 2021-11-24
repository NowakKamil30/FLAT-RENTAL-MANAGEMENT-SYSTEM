package pl.kamilnowak.flatrentalmanagementsystem.apartmet.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import pl.kamilnowak.flatrentalmanagementsystem.view.Views;

@Data
@Builder
public class DocumentDHO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String name;
    @JsonView(Views.Private.class)
    private String document;
}
