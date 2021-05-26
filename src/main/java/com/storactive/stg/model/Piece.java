package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "tab_piece")
public class Piece {

    @Id
    @Column(name = "id_piece")
    private int id;

    @Column(nullable = false)
    private String label;


    @OneToMany
    @JsonIgnore
    private Collection<StagePiece> stagePieces;

}
