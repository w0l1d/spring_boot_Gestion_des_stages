package com.storactive.stg.model;

import com.storactive.stg.Utils;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
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
    private Date startsAt;

    @Column(nullable = false, name = "date_au")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endsAt;

    @Column(nullable = false, name = "nombre_jours")
    private int nbrDays;

    private String cause;


    @PrePersist
    @PreUpdate
    public void calculateNbrDays() {
        this.nbrDays = Utils.getPeriodInDaysBetween(this.startsAt, this.endsAt);
    }

    @AssertTrue
    public boolean isValidRange() {
        return endsAt.compareTo(startsAt) > 0;
    }


    @ManyToOne(optional = false)
    @ToString.Exclude
    private Internship internship;


}
