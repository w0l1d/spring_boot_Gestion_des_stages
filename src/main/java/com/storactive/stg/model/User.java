package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.storactive.stg.Utils;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @Size(min = 5, max = 10, message = "{validation.format.cin}")
    @Pattern(regexp = "^[A-Za-z]{1,2}[0-9]{4,8}$",
            message = "{validation.format.cin}")
    @NotEmpty(message = "{validation.required}")
    @NotBlank(message = "{validation.required}")
    @NotNull(message = "{validation.required}")
    private String cin;


    @Size(min = 4,
            message = "{validation.size.min}")
    @NotEmpty(message = "{validation.required}")
    @NotBlank(message = "{validation.required}")
    @NotNull(message = "{validation.required}")
    private String name;

    @Column(nullable = false)
    @Email(message = "{validation.format.email}")
    private String email;

    @Column(unique = true,
            nullable = false)
    @Size(min = 4, message = "{validation.size.min}")
    @NotEmpty(message = "{validation.required}")
    @NotBlank(message = "{validation.required}")
    @NotNull(message = "{validation.required}")
    private String username;

    @Column(name = "mot_de_passe", nullable = false)
    @Size(min = 8, message = "{validation.size.min}")
    @NotEmpty(message = "{validation.required}")
    @NotBlank(message = "{validation.required}")
    @NotNull(message = "{validation.required}")
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
