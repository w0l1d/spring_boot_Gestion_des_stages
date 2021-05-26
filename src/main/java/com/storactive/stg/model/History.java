package com.storactive.stg.model;


import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
@Table(name = "tab_history")
public class History {


    @Id
    @Column(name = "id_history")
    private int id;

    @Column(nullable = false)
    @NotBlank
    private String action;

    @Column(nullable = false, name = "created_at")
    @CreatedDate
    private Date createdAt;


}
