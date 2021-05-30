package com.storactive.stg.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;


@Data
@Entity
@Table(name = "tab_absence")
public class Absence {

    @Id
    @Column(name = "id_absence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,name = "date_du")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startsAt;

    @Column(nullable = false,name = "date_au")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endsAt;

    @Column(nullable = false,name = "nombre_jours")
    @Min(1)
    private int nbrDays;

    private String cause;


}
