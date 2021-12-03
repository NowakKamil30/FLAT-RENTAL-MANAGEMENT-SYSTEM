package pl.kamilnowak.flatrentalmanagementsystem.apartment.dto;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kamilnowak.flatrentalmanagementsystem.view.Views;

@Getter
@Setter
public class CurrencyDHO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String name;

    public CurrencyDHO() {
    }

    @Builder
    public CurrencyDHO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
