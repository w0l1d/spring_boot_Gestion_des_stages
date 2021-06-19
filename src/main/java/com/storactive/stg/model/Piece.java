package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "tab_piece")
public class Piece {

    @Id
    @Column(name = "id_piece")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String label;


    @OneToMany
    @JsonIgnore
    @ToString.Exclude
    private Collection<StagePiece> stagePieces;

}
