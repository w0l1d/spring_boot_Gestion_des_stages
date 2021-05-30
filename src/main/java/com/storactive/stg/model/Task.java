package com.storactive.stg.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tab_tache")
public class Task {
    @Id
    @Column(name = "id_tache")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String tache;

    @Column(name = "date_tache",nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateTache;

    @ManyToOne(optional = false)
    private Internship internship;

}
