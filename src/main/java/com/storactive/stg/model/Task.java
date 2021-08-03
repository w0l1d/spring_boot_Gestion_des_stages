package com.storactive.stg.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "tab_task")
public class Task {
    @Id
    @Column(name = "id_task", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "task", nullable = false)
    @NotBlank(message = "{validation.required}")
    @NotEmpty(message = "{validation.required}")
    private String txtTask;

    @Column(name = "date_task",
            nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validation.required}")
    private Date dateTask;

    @ManyToOne(optional = false)
    @ToString.Exclude
    private Internship internship;

}
