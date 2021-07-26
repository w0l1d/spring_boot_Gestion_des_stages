package com.storactive.stg.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Data
@Entity
@Table(name = "tab_history")
public class History {
    @Id
    @Column(name = "id_history", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank
    private String action;

    @CreatedDate
    @Temporal(TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdDate;


    @ManyToOne(optional = false)
    @CreatedBy
    private User createdBy;


}
