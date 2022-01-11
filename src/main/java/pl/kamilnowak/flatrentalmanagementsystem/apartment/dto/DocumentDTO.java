package pl.kamilnowak.flatrentalmanagementsystem.apartment.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kamilnowak.flatrentalmanagementsystem.view.Views;

@Getter
@Setter
public class DocumentDTO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String name;
    @JsonView(Views.Private.class)
    private String document;
    @JsonView(Views.Private.class)
    private Long tenantId;

    public DocumentDTO() {
    }

    @Builder
    public DocumentDTO(Long id, String name, String document, Long tenantId) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.tenantId = tenantId;
    }
}
