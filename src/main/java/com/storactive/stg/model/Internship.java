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
    @NotEmpty(message = "{validation.required}")
    @NotBlank(message = "{validation.required}")
    @NotNull(message = "{validation.required}")
    private String type;

    @Column(nullable = false,name = "date_du")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validation.required}")
    private Date startsAt;

    @Column(nullable = false,name = "date_au")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validation.required}")
    private Date endsAt;


    @Column(name = "duree")
    @NotEmpty(message = "{validation.required}")
    @NotBlank(message = "{validation.required}")
    @NotNull(message = "{validation.required}")
    private String duration;

    private String formation;

    @Column(name = "etablissement")
    private String etablissement;

    @Column(name = "intitule_projet", nullable = false)
    @NotEmpty(message = "{validation.required}")
    @NotBlank(message = "{validation.required}")
    @NotNull(message = "{validation.required}")
    private String project;

    @Column(name = "description_project")
    private String desc;

    @Column(nullable = false)
    @NotNull(message = "{validation.required}")
    @Min(value = 1, message = "{validation.min}")
    @Max(value = 4, message = "{validation.max}")
    private int status;

    @AssertTrue(message = "{validation.dates.valid_range}")
    @Transient
    @JsonIgnore
    private boolean validRange;


    public boolean isValidRange() {
        return this.validRange;
    }


    public void setValidRange(boolean validRange) {
        this.validRange = endsAt.compareTo(startsAt) > 0;
    }

    @PreUpdate
    @PrePersist
    public void initValues() {
        this.validRange = endsAt.compareTo(startsAt) > 0;
    }


    @ManyToOne(optional = false)
    @JsonIgnoreProperties(
            value = {
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "internship")
    @JsonIgnore
    @ToString.Exclude
    private Collection<Alert> alerts;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "internship")
    @JsonIgnore
    @ToString.Exclude
    private Collection<StagePiece> stagePieces;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "internship")
    @JsonIgnore
    @ToString.Exclude
    private Collection<Task> tasks;


}
