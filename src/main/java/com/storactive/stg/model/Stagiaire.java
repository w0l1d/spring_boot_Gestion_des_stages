package com.storactive.stg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.storactive.stg.model.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@Table(name = "tab_stagiaire")
public class Stagiaire extends Employee {

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    private String address;

    @NotBlank @Size(min = 10, max = 13)
    @Pattern(regexp = "^(\\+[0-9]{1,3}|0)([ \\-_/]*)(\\d[ \\-_/]*){9}$",
            message = "phone format: '*# ## ## ## ##' (#: number || *: 0 or '+'country code)")
    private String phone;

    @ColumnDefault("false")
    private boolean isEnabled;


    @OneToMany
    @JsonIgnore
    Collection<Stage> stages;

}

