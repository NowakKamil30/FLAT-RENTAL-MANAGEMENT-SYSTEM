package pl.kamilnowak.flatrentalmanagementsystem.apartment.dto;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kamilnowak.flatrentalmanagementsystem.util.view.Views;

@Getter
@Setter
public class CurrencyDTO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String name;

    public CurrencyDTO() {
    }

    @Builder
    public CurrencyDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
