package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Entity
@Table(name = "tab_piece")
public class Piece {

    @Id
    @Column(name = "id_piece", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotEmpty(message = "{validation.required}")
    @NotBlank(message = "{validation.required}")
    @NotNull(message = "{validation.required}")
    private String label;


    @Transient
    public int getSpcCount() {
        return stagePieces.size();
    }


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "piece")
    @JsonIgnore
    @ToString.Exclude
    private Collection<StagePiece> stagePieces;


}
