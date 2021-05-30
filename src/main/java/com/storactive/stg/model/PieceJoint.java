package com.storactive.stg.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "tab_piece_jointe")
public class PieceJoint {

    @Id
    @Column(name = "id_piece_jointe")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(nullable = false, name = "piece_jointe")
    private String pieceJoint;

    @NotBlank
    @Column(nullable = false, name = "type_piece_jointe")
    private String type;

    @NotBlank
    @Column(nullable = false)
    private String path;

    


}
