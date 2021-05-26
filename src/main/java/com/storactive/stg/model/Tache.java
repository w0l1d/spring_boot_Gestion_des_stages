package com.storactive.stg.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tab_tache")
public class Tache {
    @Id
    @Column(name = "id_tache")
    private int id;

    @Column(nullable = false)
    private String tache;

    @Column(name = "date_tache",nullable = false)
    private String dateTache;


}
