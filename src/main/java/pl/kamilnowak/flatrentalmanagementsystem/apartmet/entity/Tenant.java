package pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity;

import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = Tenant.TABLE_NAME)
public class Tenant {
    public static final String TABLE_NAME =  "tenants";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Size(min = 2, max = 40)
    @Column(name = "first_name")
    private String firstName;
    @Size(min = 2, max = 40)
    @Column(name = "last_name")
    private String lastName;
    @Pattern(regexp = "[0-9]{9}")
    @Column(name = "phote_number")
    private String phoneNumber;
    @NotEmpty
    @Size(min = 2, max = 60)
    private String mail;
    @NotNull
    private BigDecimal fee;
    @NotNull
    @Column(name = "is_paid")
    private boolean isPaid;
    @NotNull
    @Column(name = "is_active")
    private boolean isActive;
    private String description;
    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;
    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;
    @NotNull
    @Column(name = "paid_date")
    private LocalDate paidDate;
    @ManyToOne
    private Apartment apartment;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Document> documents;
    @ManyToOne
    private Currency currency;

    public Tenant() {
    }

    @Builder
    public Tenant(Long id, String firstName, String lastName, String phoneNumber, String mail, BigDecimal fee, boolean isPaid, boolean isActive, String description, LocalDate endDate, LocalDate startDate, LocalDate paidDate, Apartment apartment, List<Document> documents, Currency currency) {
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
        this.apartment = apartment;
        this.documents = documents;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
