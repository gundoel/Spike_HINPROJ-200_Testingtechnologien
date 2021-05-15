package com.swisscom.uamspiketesting.repository;

import com.swisscom.uamspiketesting.model.Role;
import com.swisscom.uamspiketesting.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
// run tests against H2 database
class UserRepositoryTest {
   @Autowired
   UserRepository userRepository;

   @AfterEach
   void tearDown() {
      userRepository.deleteAll();
   }

   @Test
   void itShouldFindUserByEmail() {
      HashSet<Role> roles = new HashSet<>();
      HashSet<User> usersDummy = new HashSet<>();
      roles.add(new Role(1,"admin", usersDummy));
      User userToSave = new User(1, "Hans", "Muster", "hans.muster@test.com", "1234", true, roles);
      userRepository.save(userToSave);
      List<User> users = userRepository.findByEmail("hans.muster@test.com");
      assertEquals(1, users.get(0).getId());
   }

   @Test
   void userShouldExist() {
      HashSet<Role> roles = new HashSet<>();
      HashSet<User> usersDummy = new HashSet<>();
      roles.add(new Role(1,"admin", usersDummy));
      User userToSave = new User(1, "Frieda", "Müller", "frieda.mueller@test.com", "1234", true, roles);
      userRepository.save(userToSave);
      boolean userExists = userRepository.doesUserWithEmailExist("frieda.mueller@test.com");
      assertThat(userExists).isTrue();
   }
}