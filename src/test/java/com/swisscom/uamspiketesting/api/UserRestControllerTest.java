package com.swisscom.uamspiketesting.api;

import com.swisscom.uamspiketesting.model.Role;
import com.swisscom.uamspiketesting.model.User;
import com.swisscom.uamspiketesting.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// simple unit test
class UserRestControllerTest {
   @MockBean
   UserService userService;

   @Test
   // Dummy test
   void shouldSayHello() {
      UserRestController controller = new UserRestController();
      String response = controller.hello("World");
      assertEquals("Hello, World", response);
   }

   @Test
   void shouldGetUsers() {
      HashSet<User> usersDummy = new HashSet<>();
      HashSet<Role> roles = new HashSet<>();
      roles.add(new Role(1,"admin", usersDummy));
      User user = new User(1, "Hans", "Muster", "hans.muster@test.com", "1234", true, roles);
      List<User> users = new ArrayList<>();
      users.add(user);
      UserService userService = Mockito.mock(UserService.class);
      when(userService.getUsers()).thenReturn(users);
      UserRestController userRestController = new UserRestController(userService);
      assertEquals(users, userRestController.getUsers(null).getBody());
   }

   @Test
   void shouldGetUsersByEmail() {
      HashSet<User> usersDummy = new HashSet<>();
      HashSet<Role> roles = new HashSet<>();
      roles.add(new Role(1,"admin", usersDummy));
      User user1 = new User(1, "Hans", "Muster", "hans.muster@test.com", "1234", true, roles);
      User user2 = new User(2, "Frieda", "Müller", "frieda.mueller@test.com", "1234", true, roles);
      List<User> users = new ArrayList<>();
      users.add(user1);
      users.add(user2);
      UserService userService = Mockito.mock(UserService.class);
      when(userService.getUsersByEmail("frieda.mueller@test.com")).thenReturn(users.stream().filter(user -> user.getEmail().equals("frieda.mueller@test.com")).collect(Collectors.toList()));
      UserRestController userRestController = new UserRestController(userService);
      assertEquals(users.get(1), userRestController.getUsers("frieda.mueller@test.com").getBody().get(0));
   }
}