package com.storactive.stg.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tab_employee")
public class Employee extends User {


}
