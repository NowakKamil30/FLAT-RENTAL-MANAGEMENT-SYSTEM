package pl.kamilnowak.flatrentalmanagementsystem.apartmet.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.kamilnowak.flatrentalmanagementsystem.view.Views;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
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
    private Long currencyId;

    public TenantDHO() {
    }

    @Builder
    public TenantDHO(Long id, String firstName, String lastName, String phoneNumber, String mail, BigDecimal fee, boolean isPaid, boolean isActive, String description, LocalDate endDate, LocalDate startDate, LocalDate paidDate, Long apartmentId, Long currencyId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.fee = fee;
        this.isPaid = isPaid;
        this.isActive = isActive;
        this.description = description;
        this.endDate = endDate;
        this.startDate = startDate;
        this.paidDate = paidDate;
        this.apartmentId = apartmentId;
        this.currencyId = currencyId;
    }
}
