package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.storactive.stg.model.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "tab_stagiaire")
public class Interner extends User {

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    private String address;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "interner")
    @JsonIgnore
    @ToString.Exclude
    Collection<Internship> internships;
    @NotBlank
    @Size(min = 10, max = 13)
    @Pattern(regexp = "^(\\+[0-9]{1,3}|0)([ \\-_/]*)(\\d[ \\-_/]*){9}$",
            message = "phone format: '*# ## ## ## ##' (#: number || *: 0 or '+'country code)")
    private String phone;
    @ColumnDefault("false")
    @Column(name = "is_enabled")
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList();
        authorities.add(new SimpleGrantedAuthority("ROLE_INTERNER"));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

}

