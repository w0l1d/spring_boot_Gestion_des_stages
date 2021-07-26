package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "tab_alert")
public class Alert {

    @Id
    @Column(name = "id_alert")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 5)
    private String content;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    @ColumnDefault("0")
    @Min(0)
    @Max(1)
    private short status;
    @ManyToOne(optional = false)
    @JsonIgnoreProperties({
            Internship_.FORMATION,
            Internship_.ETABLISSEMENT,
            Internship_.DESC,
            Internship_.TYPE,
            Internship_.DURATION,
            Internship_.INTERNER
    })
    private Internship internship;

    @Transient
    public boolean isSolved() {
        return status != 0;
    }

}
