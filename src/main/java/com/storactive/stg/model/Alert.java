package com.storactive.stg.model;


import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
    @Min(5)
    private String content;

    @ColumnDefault("current_timestamp()")
    private Date createdAt;

    @Column(nullable = false)
    private short status;


}
