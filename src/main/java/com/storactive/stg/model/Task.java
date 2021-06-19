package com.storactive.stg.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tab_task")
public class Task {
    @Id
    @Column(name = "id_task")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "task", nullable = false)
    private String txtTask;

    @Column(name = "date_task",
            nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateTask;

    @ManyToOne(optional = false)
    @ToString.Exclude
    private Internship internship;

}
