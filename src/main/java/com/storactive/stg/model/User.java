package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.storactive.stg.Utils;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

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
@JsonIgnoreProperties({"authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired"})
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    @Column(name = "cin", unique = true, nullable = false)
    @Size(min = 5, max = 10, message = "1-2 Upper case characters + 4-8 numbers")
    @NotBlank
    @Pattern(regexp = "^[A-Za-z]{1,2}[0-9]{4,8}$",
            message = "1-2 Upper case characters + 4-8 numbers : AB95869696 A4585 A1582589")
    private String cin;

    @NotBlank
    @Size(min = 4,
            message = "must be at least 2 characters length")
    private String name;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(unique = true,
            nullable = false)
    @Size(min = 4, message = "must be at least 4 characters length")
    @NotBlank
    private String username;

    @Column(name = "mot_de_passe", nullable = false)
    @Size(min = 8, message = "must be at least 8 characters length")
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "createdBy")
    @JsonIgnore
    @ToString.Exclude
    private Collection<History> histories;

    public short getUserNature() {
        return Utils.getUserNature(this);
    }


}
