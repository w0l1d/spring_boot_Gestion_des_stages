package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "tab_stage")
public class Internship {

    @Id
    @Column(name = "id_stage", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank
    @NotEmpty
    private String type;

    @Column(nullable = false,name = "date_du")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private Date startsAt;

    @Column(nullable = false,name = "date_au")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private Date endsAt;


    @Column(name = "duree")
    @NotBlank
    @NotEmpty
    private String duration;

    private String formation;

    @Column(name = "etablissement")
    private String etablissement;

    @Column(name = "intitule_projet", nullable = false)
    @NotBlank
    @NotEmpty
    private String project;

    @Column(name = "description_project")
    private String desc;

    @Column(nullable = false)
    @Min(1)
    @Max(4)
    private int status;
    @ManyToOne(optional = false)
    @JsonIgnoreProperties(value = {
            Interner_.INTERNSHIPS,
            Interner_.ADDRESS,
            Interner_.CIN,
            Interner_.EMAIL,
            Interner_.ENABLED,
            Interner_.GENDER,
            Interner_.HISTORIES,
            Interner_.PHONE,
            Interner_.PASSWORD,
            Interner_.USERNAME
    })
    @ToString.Exclude
    private Interner interner;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "internship")
    @JsonIgnore
    @ToString.Exclude
    private Collection<Absence> absences;

    @AssertTrue
    public boolean isValidRange() {
        return endsAt.compareTo(startsAt) > 0;
    }

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "internship")
    @JsonIgnore
    @ToString.Exclude
    private Collection<StagePiece> stagePieces;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    @ToString.Exclude
    private Collection<Alert> alerts;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "internship")
    @JsonIgnore
    @ToString.Exclude
    private Collection<Task> tasks;


}
