package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.storactive.stg.model.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tab_stagiaire")
public class Interner extends User {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{validation.required}")
    private Gender gender;

    private String address;


    @Size(min = 10, max = 13)
    @NotEmpty(message = "{validation.required}")
    @NotBlank(message = "{validation.required}")
    @NotNull(message = "{validation.required}")
    @Pattern(regexp = "^(\\+[0-9]{1,3}|0)([ \\-_/]*)(\\d[ \\-_/]*){9}$",
            message = "{validation.format.phone}")
    private String phone;

    @ColumnDefault("false")
    @Column(name = "is_enabled")
    private boolean enabled;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "interner")
    @JsonIgnore
    @ToString.Exclude
    Collection<Internship> internships;


    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList();
        authorities.add(new SimpleGrantedAuthority("ROLE_INTERNER"));
        return authorities;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

}

