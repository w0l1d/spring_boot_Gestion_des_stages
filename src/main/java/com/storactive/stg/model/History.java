package com.storactive.stg.model;


import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
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

    @ManyToOne(optional = false)
    @CreatedBy
    protected User createdBy;

    @Column(nullable = false)
    @NotBlank
    private String action;
    @CreatedDate
    @Temporal(TIMESTAMP)
    @ColumnDefault("")
    @Column(name = "created_at", updatable = false, nullable = false)
    protected Date createdDate;
    @Id
    @Column(name = "id_history", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


}
