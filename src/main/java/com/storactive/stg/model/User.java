package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@Entity
@Table(name = "tab_user")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @Column(unique = true, nullable = false)
    @Size(min = 5, max = 10,
            message = "1 or 2 Upper case characters + 4 to 8 numbers") @NotBlank
    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{4,8}$",
            message = "CIN examples : WX958696 X4585 A15825")
    private String cin;

    @NotBlank
    @Size(min = 2,
            message = "must be at least 2 characters length")
    private String name;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(unique = true, nullable = false)
    @Size(min = 4, message = "must be at least 4 characters length") @NotBlank
    private String username;

    @Column(name = "mot_de_passe", nullable = false)
    @Size(min = 6, message = "must be at least 6 characters length") @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @OneToMany
    @JsonIgnore
    private Collection<History> histories;
}
