package com.storactive.stg.model;


import lombok.Data;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

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
    private int id;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 5)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private short status;


}
