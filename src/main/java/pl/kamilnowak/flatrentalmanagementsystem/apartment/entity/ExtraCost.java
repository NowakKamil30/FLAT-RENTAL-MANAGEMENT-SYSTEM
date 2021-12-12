package pl.kamilnowak.flatrentalmanagementsystem.apartment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = ExtraCost.TABLE_NAME)
public class ExtraCost {
    public static final String TABLE_NAME =  "extra_costs";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Min(value=1)
    @Max(value=9999999)
    private BigDecimal extraCost;
    @Size(min = 1, max = 60)
    private String name;
    @ManyToOne
    @JsonIgnore
    private Tenant tenant;

    @Builder
    public ExtraCost(Long id, BigDecimal extraCost, String name, Tenant tenant) {
        this.id = id;
        this.extraCost = extraCost;
        this.name = name;
        this.tenant = tenant;
    }

    public ExtraCost() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getExtraCost() {
        return extraCost;
    }

    public void setExtraCost(BigDecimal extraCost) {
        this.extraCost = extraCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
