package pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity;

import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = Document.TABLE_NAME)
public class Document {
    public static final String TABLE_NAME =  "documents";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Size(min = 1, max = 100)
    private String name;
    @Lob
    private String document;
    @ManyToOne
    private Tenant tenant;

    public Document() {

    }

    @Builder
    public Document(Long id, String name, String document, Tenant tenant) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.tenant = tenant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
