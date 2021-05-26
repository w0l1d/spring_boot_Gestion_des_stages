package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tab_stage_piece")
public class StagePiece {

    @Id
    @Column(name = "id_stage_piece")
    private int id;

    @Column(name = "date_saisie")
    private Date dateSaisie;


    @ManyToOne(optional = false)
    @JsonIgnore
    Piece piece;

    @ManyToOne(optional = false)
    @JsonIgnore
    Stage stage;

    @OneToOne
    PieceJoint pieceJoint;

}
