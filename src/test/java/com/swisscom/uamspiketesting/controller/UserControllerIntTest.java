package com.swisscom.uamspiketesting.controller;

import com.swisscom.uamspiketesting.model.Role;
import com.swisscom.uamspiketesting.model.User;
import com.swisscom.uamspiketesting.service.UserService;
import org.apache.tomcat.util.file.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

import static org.mockito.AdditionalMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class) // specify class under test
class UserControllerIntTest {

   @Autowired
   private MockMvc mvc;

   @MockBean
   // Mock UserService
   UserService userService;

   @Test
   void getUsersTest() throws Exception {
      List<User> users = new ArrayList<>();
      Set<Role> roles = new HashSet<>();
      Set<User> usersDummy = new HashSet<>();
      roles.add(new Role(1, "admin", usersDummy));
      users.add(new User(1, "Hans", "Muster", "hans.muster@test.com", "1234", true, roles));
      users.add(new User(2, "Frieda", "Müller", "frieda.mueller@test.com", "1234", true, roles));
      users.sort(Comparator.comparing(user -> user.getEmail()));
      File usersTemplate = new ClassPathResource("templates/users.html").getFile();
      String html = new String(Files.readAllBytes(usersTemplate.toPath()));
      Mockito.when(userService.getUsers()).thenReturn(users);
      String url = "/users";
      // perform request
      ResultActions resultAction = mvc.perform(get(url));
      // check returned status
      resultAction
              .andExpect(status().isOk());
      // check returned mediatype for html
      resultAction.andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")));
      // compare returned html
      // this test fails, since html template is compared with result html after thymeleaf
      //resultAction.andExpect(content().string(html));

      // check if users are in html
      resultAction.andExpect(content().string(Matchers.containsString("hans.muster@test.com")));
      resultAction.andExpect(content().string(Matchers.containsString("frieda.mueller@test.com")));
      // check (e. g. alphabetical) sorting
      resultAction.andExpect(content().string(Matchers.stringContainsInOrder( "frieda.mueller@test.com", "hans.muster@test.com")));
      // check users in model
      resultAction.andExpect(model().attribute("users", users));
   }
}