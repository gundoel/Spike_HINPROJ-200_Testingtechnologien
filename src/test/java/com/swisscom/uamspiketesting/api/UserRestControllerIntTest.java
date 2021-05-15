package com.swisscom.uamspiketesting.api;

import com.swisscom.uamspiketesting.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
// integrates the Spring TestContext Framework into JUnit 5's Jupiter programming model
@WebMvcTest(UserRestController.class)
// Integration tests from https://www.youtube.com/watch?v=pNiRNRgi5Ws
class UserRestControllerIntTest {

   @Autowired
   private MockMvc mvc;

   @MockBean
   UserService userService;

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
   }

   @Test
   void shouldGetUser() {
   }

   @Test
   void willThrowWhenUserNotFound() {

   }
}