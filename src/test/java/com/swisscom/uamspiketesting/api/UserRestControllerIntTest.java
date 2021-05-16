package com.swisscom.uamspiketesting.api;

import com.swisscom.uamspiketesting.model.Role;
import com.swisscom.uamspiketesting.model.User;
import com.swisscom.uamspiketesting.service.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
// integrates the Spring TestContext Framework into JUnit 5's Jupiter programming model
@WebMvcTest(UserRestController.class) // if no class is specified all controllers are loaded and all beans would have to be loaded
// Integration tests from https://www.youtube.com/watch?v=pNiRNRgi5Ws
class UserRestControllerIntTest {

   @Autowired
   private MockMvc mvc;

   @MockBean
   UserService userService;

   @BeforeEach
   void setUp() {
      RestAssuredMockMvc.mockMvc(mvc);
   }

   @Test
      // With default
   void hello() throws Exception {
      RequestBuilder request = MockMvcRequestBuilders.get("/api/users/hello");
      MvcResult result = mvc.perform(request).andReturn();
      // World is default request parameter
      assertEquals("Hello, World", result.getResponse().getContentAsString());
   }

   @Test
      // With name
   void helloWithName() throws Exception {
      RequestBuilder request = MockMvcRequestBuilders.get("/api/users//hello?name=Simon");
      MvcResult result = mvc.perform(request).andReturn();
      // World is default request parameter
      assertEquals("Hello, Simon", result.getResponse().getContentAsString());
   }

   @Test
      // With name, alternative syntax
   void helloWithNameStatic() throws Exception {
      // not needed, just to see result type
      ResultActions resultAction = mvc.perform(get("/api/users//hello?name=Simon"));
      MvcResult mvcResult = resultAction.andReturn();
      ModelAndView model = mvcResult.getModelAndView();
      String resultString = mvcResult.getResponse().getContentAsString();
      System.out.println("Result string: " + resultString);
      // actual test starts here
      mvc.perform(get("/api/users/hello?name=Simon"))
              .andExpect(content()
                      .string("Hello, Simon"));
   }

   @Test
   void shouldGetUsers() {
      HashSet<User> usersDummy = new HashSet<>();
      Set<Role> roles = Set.of(new Role(1, "admin", usersDummy));
      Mockito.when(userService.getUsers()).thenReturn(
              List.of(new User(1, "Hans", "Muster", "hans.muster@test.com", "1234", true, roles),
                      new User(2, "Frieda", "Müller", "frieda.mueller@test.com", "1234", true, roles)));
      RestAssuredMockMvc
              .given()
              .auth().none()
              .when()
              .get("/api/users")
              .then()
              .status(HttpStatus.OK)
              .body("$.size()", Matchers.equalTo(2))
              .body("[0].email", Matchers.equalTo("hans.muster@test.com"))
              .body("[1].email", Matchers.equalTo("frieda.mueller@test.com"));
   }

   @Test
   void shouldGetUsersByEmail() {
      String testEMail = "frieda.mueller@test.com";
      HashSet<User> usersDummy = new HashSet<>();
      Set<Role> roles = Set.of(new Role(1, "admin", usersDummy));
      Mockito.when(userService.getUsers()).thenReturn(
              List.of(new User(1, "Hans", "Muster", "hans.muster@test.com", "1234", true, roles),
                      new User(2, "Frieda", "Müller", "frieda.mueller@test.com", "1234", true, roles))
                      .stream()
                      .filter(user -> user.getEmail().equals(testEMail))
                      .collect(Collectors.toList()));
      RestAssuredMockMvc
              .given()
              .auth().none()
              .param(testEMail)
              .when()
              .get("/api/users")
              .then()
              .status(HttpStatus.OK)
              .body("$.size()", Matchers.equalTo(1))
              .body("[0].email", Matchers.equalTo("frieda.mueller@test.com"));
   }
}