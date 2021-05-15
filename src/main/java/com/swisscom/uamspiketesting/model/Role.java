package com.swisscom.uamspiketesting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "uam_tst_role")
@EqualsAndHashCode(exclude = "roles")
public class Role {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   private String name;
   @ManyToMany(mappedBy = "roles")
   private Set<User> users;
}
