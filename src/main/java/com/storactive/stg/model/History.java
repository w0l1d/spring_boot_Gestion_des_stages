package com.storactive.stg.model;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
@Table(name = "tab_history")
public class History {

    @Id
    @Column(name = "id_history")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank
    private String action;

    @Column(nullable = false, name = "created_at")
    @CreatedDate
    @CreationTimestamp
    private Date createdAt;


}
