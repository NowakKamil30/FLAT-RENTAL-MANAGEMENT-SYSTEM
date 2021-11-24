package pl.kamilnowak.flatrentalmanagementsystem.apartmet.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import pl.kamilnowak.flatrentalmanagementsystem.view.Views;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class TenantDHO {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String firstName;
    @JsonView(Views.Public.class)
    private String lastName;
    @JsonView(Views.Private.class)
    private String phoneNumber;
    @JsonView(Views.Private.class)
    private String mail;
    @JsonView(Views.Private.class)
    private BigDecimal fee;
    @JsonView(Views.Private.class)
    private boolean isPaid;
    @JsonView(Views.Private.class)
    private boolean isActive;
    @JsonView(Views.Private.class)
    private String description;
    @JsonView(Views.Private.class)
    private LocalDate endDate;
    @JsonView(Views.Private.class)
    private LocalDate startDate;
    @JsonView(Views.Private.class)
    private LocalDate paidDate;
    @JsonView(Views.Private.class)
    private Long apartmentId;
    @JsonView(Views.Private.class)
    private List<Long> docuemntIds;
    @JsonView(Views.Private.class)
    private Long currencyId;
}
