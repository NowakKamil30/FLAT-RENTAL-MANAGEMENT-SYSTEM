package pl.kamilnowak.flatrentalmanagementsystem.apartment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ExtraCostSum {
    private BigDecimal price;

    public ExtraCostSum() {
    }

    @Builder
    public ExtraCostSum(BigDecimal price) {
        this.price = price;
    }
}
