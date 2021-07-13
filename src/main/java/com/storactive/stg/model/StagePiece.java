package com.storactive.stg.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tab_stage_piece")
public class StagePiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stage_piece", updatable = false)
    private Integer id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private Attachment attachment;

    @Column(name = "date_saisie")
    @CreatedDate
    private Date dateSaisie;


    @ManyToOne(optional = false)
    @ToString.Exclude
    private Piece piece;

    @ManyToOne(optional = false)
    @ToString.Exclude
    private Internship internship;

}
