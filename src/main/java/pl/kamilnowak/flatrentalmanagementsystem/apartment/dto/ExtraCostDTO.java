package pl.kamilnowak.flatrentalmanagementsystem.apartment.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Tenant;
import pl.kamilnowak.flatrentalmanagementsystem.view.Views;

import java.math.BigDecimal;

@Getter
@Setter
public class ExtraCostDTO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private BigDecimal extraCost;
    @JsonView(Views.Public.class)
    private String name;

    public ExtraCostDTO() {
    }

    @Builder
    public ExtraCostDTO(Long id, BigDecimal extraCost, String name) {
        this.id = id;
        this.extraCost = extraCost;
        this.name = name;
    }
}
