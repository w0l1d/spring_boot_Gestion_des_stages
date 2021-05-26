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
    @Size(min = 6, max = 10) @NotBlank
    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{4,8}$")
    private String cin;

    @NotBlank
    @Size(min = 2)
    private String name;

    @Column(nullable = false)
    @Size(min = 8)
    @Email
    private String email;

    @Column(unique = true, nullable = false)
    @Size( min = 4) @NotBlank
    private String username;

    @Column(name = "mot_de_passe", nullable = false)
    @Size(min = 6) @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @OneToMany
    @JsonIgnore
    private Collection<History> histories;
}
