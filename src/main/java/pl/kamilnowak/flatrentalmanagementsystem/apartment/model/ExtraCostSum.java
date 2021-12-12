package pl.kamilnowak.flatrentalmanagementsystem.apartment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PriceModel {
    private BigDecimal price;

    public PriceModel() {
    }

    @Builder
    public PriceModel(BigDecimal price) {
        this.price = price;
    }
}
