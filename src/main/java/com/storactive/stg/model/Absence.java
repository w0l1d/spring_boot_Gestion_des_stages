package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.storactive.stg.Utils;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@Entity
@Table(name = "tab_absence")
public class Absence {

    @Id
    @Column(name = "id_absence", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,name = "date_du")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validation.required}")
    private Date startsAt;

    @Column(nullable = false, name = "date_au")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validation.required}")
    private Date endsAt;

    @Column(nullable = false, name = "nombre_jours")
    private int nbrDays;

    private String cause;

    @AssertTrue(message = "{validation.dates.valid_range}")
    @Transient
    @JsonIgnore
    private boolean validRange;


    @PrePersist
    @PreUpdate
    public void initValues() {
        this.nbrDays = Utils.getPeriodInDaysBetween(this.startsAt, this.endsAt);
        this.validRange = endsAt.compareTo(startsAt) > 0;
    }


    public boolean isValidRange() {
        return this.validRange;
    }

    public void setValidRange(boolean validRange) {
        this.validRange = endsAt.compareTo(startsAt) > 0;
    }


    @ManyToOne(optional = false)
    @ToString.Exclude
    private Internship internship;


}
