package com.swisscom.uamspiketesting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
   @NotBlank
   private String firstName;
   @NotBlank
   private String lastName;
   @Email
   @NotBlank
   private String email;
   @NotBlank
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
