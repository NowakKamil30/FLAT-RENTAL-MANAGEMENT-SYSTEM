package pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity;

import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = Currency.TABLE_NAME)
public class Currency {
    public static final String TABLE_NAME =  "currencies";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Size(min = 1, max = 15)
    private String name;

    public Currency() {
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
}
