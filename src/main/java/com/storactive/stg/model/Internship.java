package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "tab_stage")
public class Internship {

    @Id
    @Column(name = "id_stage")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false,name = "date_du")

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startsAt;

    @Column(nullable = false,name = "date_au")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endsAt;


    @Column(name = "duree")
    @Min(1)
    private int duration;

    private String formation;

    @Column(name = "etablissement")
    private String etablissement;

    @Column(name = "intitule_projet", nullable = false)
    private String project;

    @Column(name = "description_project")
    private String desc;

    @Column(nullable = false)
    private int status;



    @OneToMany
    @JsonIgnore
    private Collection<Absence> absences;

    @OneToMany
    @JsonIgnore
    private Collection<StagePiece> stagePieces;

    @OneToMany
    @JsonIgnore
    private Collection<Alert> alerts;

    @OneToMany
    @JsonIgnore
    private Collection<Task> taches;

}
