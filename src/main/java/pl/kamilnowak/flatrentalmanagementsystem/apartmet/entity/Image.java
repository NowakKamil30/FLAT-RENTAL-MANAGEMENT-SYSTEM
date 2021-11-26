package pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity;

import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = Image.TABLE_NAME)
public class Image {
    public static final String TABLE_NAME =  "images";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Lob
    private String photo;
    @Size(min = 5, max = 30)
    private String title;
    @NotNull
    private LocalDate uploadDate;
    @ManyToOne
    private Apartment apartment;

    public Image() {
    }

    @Builder
    public Image(Long id, String photo, String title, LocalDate uploadDate, Apartment apartment) {
        this.id = id;
        this.photo = photo;
        this.title = title;
        this.uploadDate = uploadDate;
        this.apartment = apartment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
