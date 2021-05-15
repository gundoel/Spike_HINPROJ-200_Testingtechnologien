package com.swisscom.uamspiketesting.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "uam_tst_user")
@EqualsAndHashCode(exclude = "users")
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   @NotNull
   @Size(min=2, max=8)
   private String firstName;
   private String lastName;
   private String email;
   private String password;
   private boolean active;
   @ManyToMany(cascade = {
           CascadeType.PERSIST,
           CascadeType.MERGE
   }, fetch = FetchType.LAZY)
   @JoinTable(
           name = "uam_tst_user_role",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "role_id"))
   private Set<Role> roles;
}
